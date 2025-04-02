# Fröhantering

## Översikt
Fröhanteringsmodulen i PlantSeeds3 hanterar all funktionalitet relaterad till frön, inklusive lagring, sökning, kategorisering och planteringsinformation.

## Datamodell

### Seed (Frö)
```kotlin
data class Seed(
    val id: String,
    val name: String,
    val species: String?,
    val description: String?,
    val plantingInstructions: String?,
    val daysToGermination: Int?,
    val daysToHarvest: Int?,
    val lightNeeds: String?,
    val waterNeeds: String?,
    val soilType: String?,
    val temperature: String?,
    val spacing: String?,
    val companionPlants: List<String>?,
    val avoidPlants: List<String>?,
    val imageUrl: String?,
    val createdAt: Date,
    val updatedAt: Date
)
```

## Funktioner

### 1. Frölista
- Visa alla frön i en scrollbar lista
- Sortera efter namn, art eller datum
- Filtrera efter kategori eller status
- Sökfunktion med autokomplettering

### 2. Fröinformation
- Detaljerad information om varje frö
- Planteringsinstruktioner
- Skötselråd
- Bilder och dokumentation
- Scrollbar detaljvy med all information
- Automatisk uppdatering efter redigering

### 3. Fröredigering
- Lägga till nya frön
- Redigera befintliga frön
- Ta bort frön
- Kopiera frön
- Hantera följeslagare och växter att undvika
- Förbättrad felhantering och validering

### 4. Kategorisering
- Organisera frön i kategorier
- Lägga till egna kategorier
- Tagga frön
- Favoritmarkering

### 5. Sökfunktionalitet
- Sök på namn eller art
- Filtrera efter egenskaper
- Spara sökningar
- Sökhistorik

### 6. Planteringsinformation
- Planteringsdatum
- Grobarhet
- Skördedatum
- Växtföljdsrekommendationer

## Användargränssnitt

### 1. Frölista (SeedsScreen)
```kotlin
@Composable
fun SeedsScreen(
    viewModel: SeedsViewModel,
    onNavigateToAddSeed: () -> Unit,
    onNavigateToSeedDetails: (String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Frön") },
                actions = {
                    IconButton(onClick = onNavigateToAddSeed) {
                        Icon(Icons.Default.Add, "Lägg till frö")
                    }
                }
            )
        }
    ) { padding ->
        SeedsList(
            seeds = state.seeds,
            onSeedClick = onNavigateToSeedDetails,
            modifier = Modifier.padding(padding)
        )
    }
}
```

### 2. Fröredigering (AddEditSeedScreen)
```kotlin
@Composable
fun AddEditSeedScreen(
    viewModel: AddEditSeedViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (state.isEdit) "Redigera frö" else "Nytt frö") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Tillbaka")
                    }
                }
            )
        }
    ) { padding ->
        SeedForm(
            state = state,
            onEvent = viewModel::onEvent,
            modifier = Modifier.padding(padding)
        )
    }
}
```

## Use Cases

### 1. GetSeedsUseCase
```kotlin
class GetSeedsUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(): Result<List<Seed>> {
        return try {
            Result.success(repository.getSeeds())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

### 2. AddSeedUseCase
```kotlin
class AddSeedUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(seed: Seed): Result<Unit> {
        return try {
            repository.addSeed(seed)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
```

## Repository

### SeedRepository
```kotlin
interface SeedRepository {
    suspend fun getSeeds(): List<Seed>
    suspend fun getSeedById(id: String): Seed?
    suspend fun addSeed(seed: Seed)
    suspend fun updateSeed(seed: Seed)
    suspend fun deleteSeed(seed: Seed)
    suspend fun searchSeeds(query: String): List<Seed>
}
```

## Synkronisering

### Firebase Sync
```kotlin
class SeedSyncService @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val repository: SeedRepository
) {
    suspend fun syncSeeds() {
        try {
            val remoteSeeds = firestore.collection("seeds")
                .get()
                .await()
                .toObjects<SeedDto>()
                .map { it.toSeed() }
            
            repository.syncSeeds(remoteSeeds)
        } catch (e: Exception) {
            throw SyncException("Failed to sync seeds", e)
        }
    }
}
```

## Felhantering

### SeedExceptions
```kotlin
sealed class SeedException : Exception() {
    class NotFound(val id: String) : SeedException()
    class InvalidInput(val message: String) : SeedException()
    class SyncFailed(override val cause: Throwable) : SeedException()
    class DatabaseError(override val cause: Throwable) : SeedException()
    class ValidationError(val message: String) : SeedException()
}
```

### Förbättrad Felhantering
- Centraliserad felhantering för databasoperationer
- Detaljerad loggning för felsökning
- Användarvänliga felmeddelanden
- Automatisk återhämtning från fel
- Validering av indata

## Tester

### Unit Tests
```kotlin
class GetSeedsUseCaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: FakeSeedRepository
    private lateinit var useCase: GetSeedsUseCase

    @Before
    fun setup() {
        repository = FakeSeedRepository()
        useCase = GetSeedsUseCase(repository)
    }

    @Test
    fun `when repository returns seeds, use case returns success`() = runTest {
        // Given
        val seeds = listOf(seed1, seed2)
        repository.setSeeds(seeds)

        // When
        val result = useCase()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(seeds, result.getOrNull())
    }
}
```

### UI Tests
```kotlin
@HiltAndroidTest
class SeedsScreenTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun seedsScreen_showsEmptyState_whenNoSeeds() {
        // Given
        val viewModel = SeedsViewModel(FakeSeedRepository())

        // When
        composeTestRule.setContent {
            SeedsScreen(viewModel)
        }

        // Then
        composeTestRule.onNodeWithText("Inga frön hittades").assertIsDisplayed()
    }
}
```

## Prestandaoptimering

### 1. Cachning
- Cacha frödata lokalt
- Uppdatera cache vid ändringar
- Använd cache vid offline-läge

### 2. Lazy Loading
- Ladda frön i sidor
- Cacha laddade sidor
- Förbättra scrollprestanda

### 3. Bildhantering
- Komprimera bilder
- Cacha bilder lokalt
- Lazy load bilder 