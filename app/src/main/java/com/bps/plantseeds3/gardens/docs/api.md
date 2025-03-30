# API-dokumentation - Trädgård-modulen

## Översikt
Detta dokument beskriver API:et för trädgård-modulen, inklusive dess komponenter, metoder och användning.

## Komponenter

### GardenRepository
```kotlin
interface GardenRepository {
    suspend fun getGardens(): Flow<List<Garden>>
    suspend fun getGardenById(id: Int): Garden?
    suspend fun insertGarden(garden: Garden)
    suspend fun deleteGarden(garden: Garden)
    suspend fun updateGarden(garden: Garden)
    suspend fun getGardensByZone(zone: GardenZone): Flow<List<Garden>>
    suspend fun getPlantsByGardenId(gardenId: Int): Flow<List<Plant>>
}
```

### GardenDao
```kotlin
@Dao
interface GardenDao {
    @Query("SELECT * FROM gardens")
    fun getAllGardens(): Flow<List<GardenEntity>>

    @Query("SELECT * FROM gardens WHERE id = :id")
    suspend fun getGardenById(id: Int): GardenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGarden(garden: GardenEntity)

    @Delete
    suspend fun deleteGarden(garden: GardenEntity)

    @Query("SELECT * FROM gardens WHERE zone = :zone")
    fun getGardensByZone(zone: GardenZone): Flow<List<GardenEntity>>

    @Query("SELECT * FROM plants WHERE gardenId = :gardenId")
    fun getPlantsByGardenId(gardenId: Int): Flow<List<PlantEntity>>
}
```

### Garden
```kotlin
data class Garden(
    val id: Int = 0,
    val name: String,
    val description: String?,
    val zone: GardenZone,
    val size: Double,
    val soilType: String,
    val sunExposure: String,
    val waterSource: String?,
    val notes: String?,
    val images: List<String>,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
```

### GardenViewModel
```kotlin
class GardenViewModel @Inject constructor(
    private val repository: GardenRepository
) : ViewModel() {
    private val _state = MutableStateFlow(GardenState())
    val state: StateFlow<GardenState> = _state.asStateFlow()

    fun onEvent(event: GardenEvent)
    private fun loadGardens()
    private fun filterAndSortGardens()
}
```

## Användning

### Lägga till en trädgård
```kotlin
viewModel.onEvent(GardenEvent.AddGarden(garden))
```

### Uppdatera en trädgård
```kotlin
viewModel.onEvent(GardenEvent.UpdateGarden(garden))
```

### Ta bort en trädgård
```kotlin
viewModel.onEvent(GardenEvent.DeleteGarden(garden))
```

### Filtrera trädgårdar
```kotlin
viewModel.onEvent(GardenEvent.FilterByZone(zone))
viewModel.onEvent(GardenEvent.FilterBySize(minSize, maxSize))
```

### Sortera trädgårdar
```kotlin
viewModel.onEvent(GardenEvent.SortGardens(order))
```

## Events
```kotlin
sealed class GardenEvent {
    data class AddGarden(val garden: Garden) : GardenEvent()
    data class UpdateGarden(val garden: Garden) : GardenEvent()
    data class DeleteGarden(val garden: Garden) : GardenEvent()
    data class FilterByZone(val zone: GardenZone?) : GardenEvent()
    data class FilterBySize(val minSize: Double?, val maxSize: Double?) : GardenEvent()
    data class SortGardens(val order: SortOrder) : GardenEvent()
    object RefreshGardens : GardenEvent()
}
```

## State
```kotlin
data class GardenState(
    val gardens: List<Garden> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.NAME,
    val selectedZone: GardenZone? = null,
    val selectedSizeRange: ClosedFloatingPointRange<Double>? = null
)
```

## Beroenden
- Room för databasoperationer
- Hilt för dependency injection
- Kotlin Coroutines för asynkron programmering
- Jetpack Compose för UI
- plants-modulen för växthantering 