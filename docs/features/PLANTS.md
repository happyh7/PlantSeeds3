# Planthantering

## Översikt
Planthanteringsmodulen i PlantSeeds3 hanterar all funktionalitet relaterad till plantor, inklusive plantering, statusuppdateringar, skötsel och skördeplanering.

## Datamodeller

### Plant (Planta)
```kotlin
data class Plant(
    val id: String,
    val seedId: String,
    val gardenId: String,
    val name: String,
    val status: PlantStatus,
    val plantedDate: Date,
    val expectedHarvestDate: Date?,
    val actualHarvestDate: Date?,
    val notes: String?,
    val imageUrls: List<String>?,
    val createdAt: Date,
    val updatedAt: Date
)

enum class PlantStatus(val displayName: String) {
    PLANTED("Planterad"),
    GERMINATING("Grodde"),
    GROWING("Växer"),
    FLOWERING("Blommar"),
    HARVESTED("Skördad"),
    FAILED("Misslyckad")
}
```

## Funktioner

### 1. Plantlista
- Visa alla plantor i en scrollbar lista
- Filtrera efter status eller trädgård
- Sortera efter datum eller status
- Sökfunktion

### 2. Plantinformation
- Detaljerad information om varje planta
- Statusuppdateringar
- Skötselhistorik
- Bilddokumentation

### 3. Plantredigering
- Lägga till nya plantor
- Redigera befintliga plantor
- Ta bort plantor
- Uppdatera status

### 4. Statusuppdateringar
- Ändra plantstatus
- Lägga till kommentarer
- Dokumentera med bilder
- Spåra utveckling

### 5. Skötsel
- Vattningspåminnelser
- Skötselinstruktioner
- Problemhantering
- Skördeplanering

### 6. Dokumentation
- Fotodokumentation
- Anteckningar
- Skötselhistorik
- Skördehistorik

## Användargränssnitt

### 1. Plantlista (PlantsScreen)
```kotlin
@Composable
fun PlantsScreen(
    viewModel: PlantsViewModel,
    onNavigateToAddPlant: () -> Unit,
    onNavigateToPlantDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plantor") },
                actions = {
                    IconButton(onClick = onNavigateToAddPlant) {
                        Icon(Icons.Default.Add, "Lägg till planta")
                    }
                }
            )
        }
    ) { padding ->
        PlantsList(
            plants = state.plants,
            onPlantClick = onNavigateToPlantDetails,
            modifier = Modifier.padding(padding)
        )
    }
}
```

### 2. Plantredigering (AddEditPlantScreen)
```kotlin
@Composable
fun AddEditPlantScreen(
    viewModel: AddEditPlantViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEdit) "Redigera planta" else "Ny planta") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Tillbaka")
                    }
                }
            )
        }
    ) { padding ->
        PlantForm(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(padding)
        )
    }
}
```

## Use Cases

### 1. GetPlantsUseCase
```kotlin
class GetPlantsUseCase @Inject constructor(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(): Result<List<Plant>> {
        return try {
            Result.success(repository.getPlants())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 2. UpdatePlantStatusUseCase
```kotlin
class UpdatePlantStatusUseCase @Inject constructor(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(
        plantId: String,
        newStatus: PlantStatus
    ): Result<Unit> {
        return try {
            repository.updatePlantStatus(plantId, newStatus)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

## Repository

### PlantRepository
```kotlin
interface PlantRepository {
    suspend fun getPlants(): List<Plant>
    suspend fun getPlantById(id: String): Plant?
    suspend fun addPlant(plant: Plant)
    suspend fun updatePlant(plant: Plant)
    suspend fun deletePlant(plant: Plant)
    suspend fun updatePlantStatus(plantId: String, status: PlantStatus)
    suspend fun getPlantsByGarden(gardenId: String): List<Plant>
}
```

## Synkronisering

### Firebase Sync
```kotlin
class PlantSyncService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val repository: PlantRepository
) {
    suspend fun syncPlants() {
        try {
            val remotePlants = firestore.collection("plants")
                .get()
                .await()
                .toObjects<PlantDto>()
                .map { it.toPlant() }
            
            repository.syncPlants(remotePlants)
        } catch (e: Exception) {
            throw SyncException("Failed to sync plants", e)
        }
    }
}
```

## Felhantering

### PlantExceptions
```kotlin
sealed class PlantException : Exception() {
    class NotFound(val id: String) : PlantException()
    class InvalidStatus(val status: PlantStatus) : PlantException()
    class InvalidDate(val date: Date) : PlantException()
    class SyncFailed(override val cause: Throwable) : PlantException()
}
```

## Tester

### Unit Tests
```kotlin
class UpdatePlantStatusUseCaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakePlantRepository
    private lateinit var useCase: UpdatePlantStatusUseCase

    @Before
    fun setup() {
        repository = FakePlantRepository()
        useCase = UpdatePlantStatusUseCase(repository)
    }

    @Test
    fun `when plant exists, updates status successfully`() = runTest {
        // Given
        val plant = plant1.copy(status = PlantStatus.PLANTED)
        repository.setPlant(plant)

        // When
        val result = useCase(plant.id, PlantStatus.GROWING)

        // Then
        assertTrue(result.isSuccess)
        assertEquals(PlantStatus.GROWING, repository.getPlantById(plant.id)?.status)
    }
}
```

### UI Tests
```kotlin
@HiltAndroidTest
class PlantsScreenTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun plantsScreen_showsEmptyState_whenNoPlants() {
        // Given
        val viewModel = PlantsViewModel(FakePlantRepository())

        // When
        composeTestRule.setContent {
            PlantsScreen(viewModel)
        }

        // Then
        composeTestRule.onNodeWithText("Inga plantor hittades").assertIsDisplayed()
    }
}
```

## Prestandaoptimering

### 1. Cachning
- Cacha plantdata lokalt
- Uppdatera cache vid ändringar
- Använd cache vid offline-läge

### 2. Lazy Loading
- Ladda plantor i sidor
- Cacha laddade sidor
- Förbättra scrollprestanda

### 3. Bildhantering
- Komprimera bilder
- Cacha bilder lokalt
- Lazy load bilder 