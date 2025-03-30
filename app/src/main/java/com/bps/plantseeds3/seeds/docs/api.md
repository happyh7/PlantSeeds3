# API-dokumentation - Fröbank-modulen

## Översikt
Detta dokument beskriver API:et för fröbank-modulen, inklusive dess komponenter, metoder och användning.

## Komponenter

### SeedRepository
```kotlin
interface SeedRepository {
    suspend fun getSeeds(): Flow<List<Seed>>
    suspend fun getSeedById(id: Int): Seed?
    suspend fun insertSeed(seed: Seed)
    suspend fun deleteSeed(seed: Seed)
    suspend fun updateSeed(seed: Seed)
    suspend fun getSeedsByCategory(category: SeedCategory): Flow<List<Seed>>
    suspend fun getPlantsBySeedId(seedId: Int): Flow<List<Plant>>
}
```

### SeedDao
```kotlin
@Dao
interface SeedDao {
    @Query("SELECT * FROM seeds")
    fun getAllSeeds(): Flow<List<SeedEntity>>

    @Query("SELECT * FROM seeds WHERE id = :id")
    suspend fun getSeedById(id: Int): SeedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeed(seed: SeedEntity)

    @Delete
    suspend fun deleteSeed(seed: SeedEntity)

    @Query("SELECT * FROM seeds WHERE category = :category")
    fun getSeedsByCategory(category: SeedCategory): Flow<List<SeedEntity>>

    @Query("SELECT * FROM plants WHERE seedId = :seedId")
    fun getPlantsBySeedId(seedId: Int): Flow<List<PlantEntity>>
}
```

### Seed
```kotlin
data class Seed(
    val id: Int = 0,
    val name: String,
    val scientificName: String,
    val species: String,
    val variety: String,
    val category: SeedCategory,
    val hardinessZone: String,
    val lifespan: String,
    val daysToGermination: Int?,
    val daysToMaturity: Int?,
    val plantingDepth: Double?,
    val spacing: Double?,
    val sunRequirements: String,
    val waterRequirements: String,
    val soilRequirements: String,
    val notes: String?,
    val images: List<String>,
    val isFavorite: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
```

### SeedViewModel
```kotlin
class SeedViewModel @Inject constructor(
    private val repository: SeedRepository
) : ViewModel() {
    private val _state = MutableStateFlow(SeedState())
    val state: StateFlow<SeedState> = _state.asStateFlow()

    fun onEvent(event: SeedEvent)
    private fun loadSeeds()
    private fun filterAndSortSeeds()
}
```

## Användning

### Lägga till ett frö
```kotlin
viewModel.onEvent(SeedEvent.AddSeed(seed))
```

### Uppdatera ett frö
```kotlin
viewModel.onEvent(SeedEvent.UpdateSeed(seed))
```

### Ta bort ett frö
```kotlin
viewModel.onEvent(SeedEvent.DeleteSeed(seed))
```

### Filtrera frön
```kotlin
viewModel.onEvent(SeedEvent.FilterByCategory(category))
viewModel.onEvent(SeedEvent.FilterByHardinessZone(zone))
```

### Sortera frön
```kotlin
viewModel.onEvent(SeedEvent.SortSeeds(order))
```

## Events
```kotlin
sealed class SeedEvent {
    data class AddSeed(val seed: Seed) : SeedEvent()
    data class UpdateSeed(val seed: Seed) : SeedEvent()
    data class DeleteSeed(val seed: Seed) : SeedEvent()
    data class FilterByCategory(val category: SeedCategory?) : SeedEvent()
    data class FilterByHardinessZone(val zone: String?) : SeedEvent()
    data class SortSeeds(val order: SortOrder) : SeedEvent()
    data class ToggleFavorite(val seed: Seed) : SeedEvent()
    object RefreshSeeds : SeedEvent()
}
```

## State
```kotlin
data class SeedState(
    val seeds: List<Seed> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val searchQuery: String = "",
    val sortOrder: SortOrder = SortOrder.NAME,
    val selectedCategory: SeedCategory? = null,
    val selectedHardinessZone: String? = null,
    val showFavoritesOnly: Boolean = false
)
```

## Beroenden
- Room för databasoperationer
- Hilt för dependency injection
- Kotlin Coroutines för asynkron programmering
- Jetpack Compose för UI
- plants-modulen för växthantering 