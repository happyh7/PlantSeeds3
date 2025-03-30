# Kodningsstandarder

## Översikt
PlantSeeds3 följer strikta kodningsstandarder för att säkerställa konsekvent, läsbar och underhållbar kod.

## Kotlin-konventioner

### 1. Namngivning
```kotlin
// Klasser och interfaces
class PlantRepository
interface PlantDataSource

// Funktioner och variabler
fun getPlants(): List<Plant>
var currentPlant: Plant? = null

// Konstanter
companion object {
    private const val MAX_PLANTS = 100
    private const val DEFAULT_PAGE_SIZE = 20
}

// Enum-klasser
enum class PlantStatus {
    PLANTED,
    GROWING,
    HARVESTED
}
```

### 2. Filstruktur
```kotlin
// 1. Paketdeklaration
package com.plantseeds3.domain.model

// 2. Importer
import com.plantseeds3.data.repository.PlantRepository
import com.plantseeds3.domain.exception.PlantException

// 3. Klassdeklaration
class Plant(
    val id: String,
    val name: String,
    val status: PlantStatus
) {
    // 4. Properties
    private var _plants = mutableListOf<Plant>()
    val plants: List<Plant> = _plants

    // 5. Init-block
    init {
        // Initialisering
    }

    // 6. Companion object
    companion object {
        // Statiska medlemmar
    }

    // 7. Public functions
    fun addPlant(plant: Plant) {
        _plants.add(plant)
    }

    // 8. Private functions
    private fun validatePlant(plant: Plant) {
        // Validering
    }
}
```

### 3. Funktioner
```kotlin
// Enkla funktioner
fun getPlantById(id: String): Plant? {
    return plants.find { it.id == id }
}

// Suspend funktioner
suspend fun loadPlants(): Result<List<Plant>> {
    return try {
        Result.success(repository.getPlants())
    } catch (e: Exception) {
        Result.failure(e)
    }
}

// Extension functions
fun List<Plant>.filterByStatus(status: PlantStatus): List<Plant> {
    return filter { it.status == status }
}
```

## Arkitekturriktlinjer

### 1. Clean Architecture
```kotlin
// Domain Layer
data class Plant(
    val id: String,
    val name: String,
    val status: PlantStatus
)

// Data Layer
data class PlantEntity(
    @PrimaryKey val id: String,
    val name: String,
    val status: String
)

// Presentation Layer
data class PlantUiState(
    val plants: List<Plant>,
    val isLoading: Boolean,
    val error: String?
)
```

### 2. Dependency Injection
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePlantRepository(
        localDataSource: PlantLocalDataSource,
        remoteDataSource: PlantRemoteDataSource
    ): PlantRepository {
        return PlantRepositoryImpl(localDataSource, remoteDataSource)
    }
}
```

### 3. Repository Pattern
```kotlin
interface PlantRepository {
    suspend fun getPlants(): Result<List<Plant>>
    suspend fun addPlant(plant: Plant): Result<Unit>
    suspend fun updatePlant(plant: Plant): Result<Unit>
    suspend fun deletePlant(plant: Plant): Result<Unit>
}
```

## UI-riktlinjer

### 1. Compose
```kotlin
@Composable
fun PlantList(
    plants: List<Plant>,
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = plants,
            key = { it.id }
        ) { plant ->
            PlantItem(
                plant = plant,
                onClick = { onPlantClick(plant) }
            )
        }
    }
}
```

### 2. ViewModel
```kotlin
class PlantsViewModel @Inject constructor(
    private val getPlantsUseCase: GetPlantsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<PlantsUiState>(PlantsUiState.Loading)
    val state: StateFlow<PlantsUiState> = _state.asStateFlow()

    fun onEvent(event: PlantsEvent) {
        when (event) {
            is PlantsEvent.LoadPlants -> loadPlants()
            is PlantsEvent.AddPlant -> addPlant(event.plant)
        }
    }
}
```

## Felhantering

### 1. Exceptions
```kotlin
sealed class PlantException : Exception() {
    class NotFound(val id: String) : PlantException()
    class InvalidInput(val message: String) : PlantException()
    class NetworkError(override val cause: Throwable) : PlantException()
}
```

### 2. Result Type
```kotlin
suspend fun getPlant(id: String): Result<Plant> {
    return try {
        val plant = repository.getPlantById(id)
        if (plant != null) {
            Result.success(plant)
        } else {
            Result.failure(PlantException.NotFound(id))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
```

## Dokumentation

### 1. KDoc
```kotlin
/**
 * Hämtar en planta med det angivna ID:t.
 *
 * @param id Plantans unika identifierare
 * @return Result som innehåller antingen Plant eller Exception
 */
suspend fun getPlant(id: String): Result<Plant>
```

### 2. Inline Comments
```kotlin
// Kontrollera om plantan finns
val plant = repository.getPlantById(id)
if (plant == null) {
    return Result.failure(PlantException.NotFound(id))
}

// Validera plantans data
if (!isValidPlant(plant)) {
    return Result.failure(PlantException.InvalidInput("Invalid plant data"))
}
```

## Best Practices

### 1. Kodkvalitet
- Följ SOLID-principerna
- Använd dependency injection
- Implementera clean architecture
- Skriv testbar kod

### 2. Prestanda
- Använd coroutines för asynkron kod
- Implementera effektiv cachning
- Optimera databasanrop
- Hantera minnesläckor

### 3. Säkerhet
- Validera all input
- Hantera känslig data säkert
- Implementera felhantering
- Följ säkerhetsriktlinjer

### 4. Underhållbarhet
- Skriv läsbar kod
- Dokumentera komplex logik
- Följ namngivningskonventioner
- Håll koden DRY

### 5. Testning
- Skriv enhetstester
- Implementera UI-tester
- Säkerställ hög täckning
- Följ testdriven utveckling 