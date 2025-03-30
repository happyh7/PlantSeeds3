# API-dokumentation - Växt-modulen

## Översikt
Detta dokument beskriver API:et för växt-modulen, inklusive dess komponenter, metoder och användning.

## Komponenter

### PlantRepository
```kotlin
interface PlantRepository {
    suspend fun getPlants(): Flow<List<Plant>>
    suspend fun getPlantById(id: Int): Plant?
    suspend fun insertPlant(plant: Plant)
    suspend fun deletePlant(plant: Plant)
    suspend fun updatePlant(plant: Plant)
    suspend fun getPlantsByGardenId(gardenId: Int): Flow<List<Plant>>
    suspend fun getPlantsBySeedId(seedId: Int): Flow<List<Plant>>
}
```

### PlantDao
```kotlin
@Dao
interface PlantDao {
    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlantById(id: Int): PlantEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: PlantEntity)

    @Delete
    suspend fun deletePlant(plant: PlantEntity)

    @Query("SELECT * FROM plants WHERE gardenId = :gardenId")
    fun getPlantsByGardenId(gardenId: Int): Flow<List<PlantEntity>>

    @Query("SELECT * FROM plants WHERE seedId = :seedId")
    fun getPlantsBySeedId(seedId: Int): Flow<List<PlantEntity>>
}
```

### Plant
```kotlin
data class Plant(
    val id: Int = 0,
    val name: String,
    val scientificName: String,
    val species: String,
    val variety: String,
    val category: PlantCategory,
    val seedId: Int?,
    val gardenId: Int?,
    val status: PlantStatus,
    val plantingDate: LocalDate?,
    val germinationDate: LocalDate?,
    val floweringDate: LocalDate?,
    val harvestDate: LocalDate?,
    val notes: String?,
    val images: List<String>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
```

### PlantViewModel
```kotlin
class PlantViewModel @Inject constructor(
    private val repository: PlantRepository
) : ViewModel() {
    private val _state = MutableStateFlow(PlantState())
    val state: StateFlow<PlantState> = _state.asStateFlow()

    fun onEvent(event: PlantEvent)
    private fun loadPlants()
    private fun filterAndSortPlants()
}
```

## Användning

### Lägga till en växt
```kotlin
viewModel.onEvent(PlantEvent.AddPlant(plant))
```

### Uppdatera en växt
```kotlin
viewModel.onEvent(PlantEvent.UpdatePlant(plant))
```

### Ta bort en växt
```kotlin
viewModel.onEvent(PlantEvent.DeletePlant(plant))
```

### Filtrera växter
```kotlin
viewModel.onEvent(PlantEvent.FilterByStatus(status))
viewModel.onEvent(PlantEvent.FilterByGarden(gardenId))
```

### Sortera växter
```kotlin
viewModel.onEvent(PlantEvent.SortPlants(order))
```

## Events
```kotlin
sealed class PlantEvent {
    data class AddPlant(val plant: Plant) : PlantEvent()
    data class UpdatePlant(val plant: Plant) : PlantEvent()
    data class DeletePlant(val plant: Plant) : PlantEvent()
    data class FilterByStatus(val status: PlantStatus?) : PlantEvent()
    data class FilterByGarden(val gardenId: Int?) : PlantEvent()
    data class SortPlants(val order: SortOrder) : PlantEvent()
    object RefreshPlants : PlantEvent()
}
```

## State
```kotlin
data class PlantState(
    val plants: List<Plant> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.NAME,
    val selectedStatus: PlantStatus? = null,
    val selectedGardenId: Int? = null
)
```

## Beroenden
- Room för databasoperationer
- Hilt för dependency injection
- Kotlin Coroutines för asynkron programmering
- Jetpack Compose för UI
- seeds-modulen för fröhantering
- gardens-modulen för trädgårdshantering 