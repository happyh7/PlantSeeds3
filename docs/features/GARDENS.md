# Trädgårdshantering

## Översikt
Trädgårdshanteringsmodulen i PlantSeeds3 hanterar all funktionalitet relaterad till trädgårdar, inklusive skapande, redigering, planering och övervakning av trädgårdar.

## Datamodeller

### Garden (Trädgård)
```kotlin
data class Garden(
    val id: String,
    val name: String,
    val description: String?,
    val location: String?,
    val size: GardenSize,
    val type: GardenType,
    val plants: List<Plant>,
    val imageUrls: List<String>?,
    val createdAt: Date,
    val updatedAt: Date
)

enum class GardenSize(val displayName: String) {
    SMALL("Liten"),
    MEDIUM("Medium"),
    LARGE("Stor")
}

enum class GardenType(val displayName: String) {
    INDOOR("Inomhus"),
    OUTDOOR("Utomhus"),
    GREENHOUSE("Växthus"),
    BALCONY("Balkong")
}
```

## Funktioner

### 1. Trädgårdslista
- Visa alla trädgårdar
- Filtrera efter typ eller storlek
- Sortera efter namn eller datum
- Sökfunktion

### 2. Trädgårdsinformation
- Detaljerad information om varje trädgård
- Plantöversikt
- Skötselplan
- Bilddokumentation

### 3. Trädgårdsredigering
- Skapa nya trädgårdar
- Redigera befintliga trädgårdar
- Ta bort trädgårdar
- Hantera plantor i trädgården

### 4. Planering
- Trädgårdslayout
- Växtföljdsplanering
- Säsongsplanering
- Skötselschema

### 5. Övervakning
- Väderdata
- Jorddata
- Skötselhistorik
- Skördeplanering

### 6. Dokumentation
- Fotodokumentation
- Anteckningar
- Skötselhistorik
- Skördehistorik

## Användargränssnitt

### 1. Trädgårdslista (GardensScreen)
```kotlin
@Composable
fun GardensScreen(
    viewModel: GardensViewModel,
    onNavigateToAddGarden: () -> Unit,
    onNavigateToGardenDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Trädgårdar") },
                actions = {
                    IconButton(onClick = onNavigateToAddGarden) {
                        Icon(Icons.Default.Add, "Lägg till trädgård")
                    }
                }
            )
        }
    ) { padding ->
        GardensList(
            gardens = state.gardens,
            onGardenClick = onNavigateToGardenDetails,
            modifier = Modifier.padding(padding)
        )
    }
}
```

### 2. Trädgårdsredigering (AddEditGardenScreen)
```kotlin
@Composable
fun AddEditGardenScreen(
    viewModel: AddEditGardenViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEdit) "Redigera trädgård" else "Ny trädgård") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Tillbaka")
                    }
                }
            )
        }
    ) { padding ->
        GardenForm(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(padding)
        )
    }
}
```

## Use Cases

### 1. GetGardensUseCase
```kotlin
class GetGardensUseCase @Inject constructor(
    private val repository: GardenRepository
) {
    suspend operator fun invoke(): Result<List<Garden>> {
        return try {
            Result.success(repository.getGardens())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 2. AddPlantToGardenUseCase
```kotlin
class AddPlantToGardenUseCase @Inject constructor(
    private val repository: GardenRepository
) {
    suspend operator fun invoke(
        gardenId: String,
        plant: Plant
    ): Result<Unit> {
        return try {
            repository.addPlantToGarden(gardenId, plant)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

## Repository

### GardenRepository
```kotlin
interface GardenRepository {
    suspend fun getGardens(): List<Garden>
    suspend fun getGardenById(id: String): Garden?
    suspend fun addGarden(garden: Garden)
    suspend fun updateGarden(garden: Garden)
    suspend fun deleteGarden(garden: Garden)
    suspend fun addPlantToGarden(gardenId: String, plant: Plant)
    suspend fun removePlantFromGarden(gardenId: String, plantId: String)
}
```

## Synkronisering

### Firebase Sync
```kotlin
class GardenSyncService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val repository: GardenRepository
) {
    suspend fun syncGardens() {
        try {
            val remoteGardens = firestore.collection("gardens")
                .get()
                .await()
                .toObjects<GardenDto>()
                .map { it.toGarden() }
            
            repository.syncGardens(remoteGardens)
        } catch (e: Exception) {
            throw SyncException("Failed to sync gardens", e)
        }
    }
}
```

## Felhantering

### GardenExceptions
```kotlin
sealed class GardenException : Exception() {
    class NotFound(val id: String) : GardenException()
    class InvalidSize(val size: GardenSize) : GardenException()
    class InvalidType(val type: GardenType) : GardenException()
    class SyncFailed(override val cause: Throwable) : GardenException()
}
```

## Tester

### Unit Tests
```kotlin
class AddPlantToGardenUseCaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakeGardenRepository
    private lateinit var useCase: AddPlantToGardenUseCase

    @Before
    fun setup() {
        repository = FakeGardenRepository()
        useCase = AddPlantToGardenUseCase(repository)
    }

    @Test
    fun `when garden exists, adds plant successfully`() = runTest {
        // Given
        val garden = garden1
        val plant = plant1
        repository.setGarden(garden)

        // When
        val result = useCase(garden.id, plant)

        // Then
        assertTrue(result.isSuccess)
        assertTrue(repository.getGardenById(garden.id)?.plants?.contains(plant) == true)
    }
}
```

### UI Tests
```kotlin
@HiltAndroidTest
class GardensScreenTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun gardensScreen_showsEmptyState_whenNoGardens() {
        // Given
        val viewModel = GardensViewModel(FakeGardenRepository())

        // When
        composeTestRule.setContent {
            GardensScreen(viewModel)
        }

        // Then
        composeTestRule.onNodeWithText("Inga trädgårdar hittades").assertIsDisplayed()
    }
}
```

## Prestandaoptimering

### 1. Cachning
- Cacha trädgårdsdata lokalt
- Uppdatera cache vid ändringar
- Använd cache vid offline-läge

### 2. Lazy Loading
- Ladda trädgårdar i sidor
- Cacha laddade sidor
- Förbättra scrollprestanda

### 3. Bildhantering
- Komprimera bilder
- Cacha bilder lokalt
- Lazy load bilder 