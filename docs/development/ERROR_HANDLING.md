# Felhantering

## Översikt
PlantSeeds3 implementerar en robust felhanteringsstrategi som täcker alla lager i applikationen, från nätverksanrop till användargränssnittet.

## Feltyper

### 1. Domänfel
```kotlin
sealed class DomainException : Exception() {
    class NotFound(val id: String) : DomainException()
    class InvalidInput(val message: String) : DomainException()
    class ValidationError(val errors: List<String>) : DomainException()
    class BusinessRuleViolation(val message: String) : DomainException()
}
```

### 2. Datakällfel
```kotlin
sealed class DataSourceException : Exception() {
    class NetworkError(override val cause: Throwable) : DataSourceException()
    class DatabaseError(override val cause: Throwable) : DataSourceException()
    class SyncError(override val cause: Throwable) : DataSourceException()
    class CacheError(override val cause: Throwable) : DataSourceException()
}
```

### 3. UI-fel
```kotlin
sealed class UiException : Exception() {
    class NavigationError(val route: String) : UiException()
    class StateError(val message: String) : UiException()
    class ResourceError(val resourceId: Int) : UiException()
}
```

## Felhantering i olika lager

### 1. Datakällagret
```kotlin
class PlantRepositoryImpl @Inject constructor(
    private val localDataSource: PlantLocalDataSource,
    private val remoteDataSource: PlantRemoteDataSource
) : PlantRepository {
    override suspend fun getPlants(): Result<List<Plant>> {
        return try {
            // Försök hämta från cache först
            val cachedPlants = localDataSource.getPlants()
            if (cachedPlants.isNotEmpty()) {
                return Result.success(cachedPlants)
            }
            
            // Om ingen cache, hämta från nätverket
            val remotePlants = remoteDataSource.getPlants()
            localDataSource.savePlants(remotePlants)
            Result.success(remotePlants)
        } catch (e: Exception) {
            when (e) {
                is IOException -> Result.failure(DataSourceException.NetworkError(e))
                is SQLiteException -> Result.failure(DataSourceException.DatabaseError(e))
                else -> Result.failure(e)
            }
        }
    }
}
```

### 2. Domänlagret
```kotlin
class AddPlantUseCase @Inject constructor(
    private val repository: PlantRepository
) {
    suspend operator fun invoke(plant: Plant): Result<Unit> {
        return try {
            // Validera input
            validatePlant(plant)
            
            // Spara planta
            repository.addPlant(plant)
            Result.success(Unit)
        } catch (e: Exception) {
            when (e) {
                is DomainException -> Result.failure(e)
                is DataSourceException -> Result.failure(e)
                else -> Result.failure(DomainException.InvalidInput("Unexpected error: ${e.message}"))
            }
        }
    }
    
    private fun validatePlant(plant: Plant) {
        val errors = mutableListOf<String>()
        
        if (plant.name.isBlank()) {
            errors.add("Plantnamn kan inte vara tomt")
        }
        
        if (plant.seedId.isBlank()) {
            errors.add("Frö-ID kan inte vara tomt")
        }
        
        if (errors.isNotEmpty()) {
            throw DomainException.ValidationError(errors)
        }
    }
}
```

### 3. Presentationslagret
```kotlin
class PlantsViewModel @Inject constructor(
    private val getPlantsUseCase: GetPlantsUseCase,
    private val addPlantUseCase: AddPlantUseCase
) : ViewModel() {
    
    private val _state = MutableStateFlow<PlantsUiState>(PlantsUiState.Loading)
    val state: StateFlow<PlantsUiState> = _state.asStateFlow()
    
    fun onEvent(event: PlantsEvent) {
        when (event) {
            is PlantsEvent.LoadPlants -> loadPlants()
            is PlantsEvent.AddPlant -> addPlant(event.plant)
            is PlantsEvent.DismissError -> dismissError()
        }
    }
    
    private fun loadPlants() {
        viewModelScope.launch {
            _state.value = PlantsUiState.Loading
            getPlantsUseCase()
                .onSuccess { plants ->
                    _state.value = PlantsUiState.Success(plants)
                }
                .onFailure { error ->
                    _state.value = PlantsUiState.Error(
                        message = getErrorMessage(error),
                        retryAction = { loadPlants() }
                    )
                }
        }
    }
    
    private fun getErrorMessage(error: Throwable): String {
        return when (error) {
            is DomainException.NotFound -> "Plantan kunde inte hittas"
            is DomainException.ValidationError -> error.errors.joinToString("\n")
            is DataSourceException.NetworkError -> "Kunde inte ansluta till servern"
            is DataSourceException.DatabaseError -> "Ett databasfel uppstod"
            else -> "Ett oväntat fel uppstod"
        }
    }
}
```

## Felhantering i UI

### 1. Felmeddelanden
```kotlin
@Composable
fun ErrorMessage(
    message: String,
    onDismiss: () -> Unit,
    onRetry: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Fel") },
        text = { Text(message) },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Stäng")
            }
        },
        dismissButton = onRetry?.let {
            {
                TextButton(onClick = it) {
                    Text("Försök igen")
                }
            }
        }
    )
}
```

### 2. Felhantering i formulär
```kotlin
@Composable
fun PlantForm(
    state: AddEditPlantState,
    onEvent: (AddEditPlantEvent) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }
    
    OutlinedTextField(
        value = name,
        onValueChange = { 
            name = it
            nameError = null
        },
        label = { Text("Namn") },
        isError = nameError != null,
        supportingText = nameError?.let { { Text(it) } },
        modifier = Modifier.fillMaxWidth()
    )
    
    if (state.error != null) {
        Text(
            text = state.error,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}
```

## Loggning

### 1. Loggning av fel
```kotlin
class ErrorLogger @Inject constructor(
    private val crashlytics: FirebaseCrashlytics
) {
    fun logError(error: Throwable, context: String) {
        crashlytics.setCustomKey("context", context)
        crashlytics.recordException(error)
    }
    
    fun logWarning(message: String, context: String) {
        crashlytics.log("$context: $message")
    }
}
```

### 2. Felanalys
```kotlin
class ErrorAnalytics @Inject constructor(
    private val analytics: FirebaseAnalytics
) {
    fun trackError(error: Throwable, context: String) {
        analytics.logEvent("error_occurred") {
            param("error_type", error.javaClass.simpleName)
            param("error_message", error.message ?: "")
            param("context", context)
        }
    }
}
```

## Best Practices

### 1. Felhantering
- Använd specifika feltyper för olika scenarion
- Hantera fel på rätt nivå i arkitekturen
- Ge användaren meningsfulla felmeddelanden
- Implementera återställningsmekanismer

### 2. Loggning
- Logga alla fel med kontext
- Använd olika loggningsnivåer
- Inkludera relevant metadata
- Följ sekretessregler

### 3. Felrapportering
- Samla in felinformation
- Analysera felmönster
- Förbättra felhantering kontinuerligt
- Dokumentera kända fel

### 4. Prestanda
- Hantera fel effektivt
- Undvik onödiga nätverksanrop
- Cacha felmeddelanden
- Optimera felhanteringsflöden 