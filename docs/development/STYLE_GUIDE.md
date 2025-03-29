# Stilguide för PlantSeeds3

## Översikt
Detta dokument beskriver stilguide och formateringsregler för PlantSeeds3-projektet.

## Kotlin-kodning

### 1. Filstruktur
```kotlin
// 1. Package-deklaration
package com.bps.plantseeds3.feature

// 2. Imports
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

// 3. Klass/Interface-deklaration
class FeatureScreen(
    private val viewModel: FeatureViewModel,
    private val onNavigate: (String) -> Unit
) {
    // 4. Properties
    private val state by viewModel.state.collectAsState()
    
    // 5. Init-block
    init {
        viewModel.loadData()
    }
    
    // 6. Companion object
    companion object {
        private const val TAG = "FeatureScreen"
    }
    
    // 7. Public functions
    fun handleEvent(event: FeatureEvent) {
        viewModel.handleEvent(event)
    }
    
    // 8. Private functions
    private fun setupUI() {
        // Implementation
    }
}
```

### 2. Namngivning

#### Klasser och Interfaces
```kotlin
// Klasser bör vara substantiv
class PlantRepository
class GardenViewModel

// Interfaces bör beskriva vad något kan göra
interface PlantRepository
interface DataSource
```

#### Funktioner
```kotlin
// Funktioner bör vara verb
fun getPlants()
fun updatePlant()
fun deleteGarden()

// Boolean-funktioner bör börja med is/has/should
fun isPlantHealthy()
fun hasWater()
fun shouldWater()
```

#### Variabler
```kotlin
// Variabler bör vara substantiv
val plantName: String
var isSelected: Boolean

// Konstant bör vara i UPPERCASE
companion object {
    private const val MAX_PLANTS = 100
    private const val TAG = "PlantScreen"
}
```

### 3. Formatering

#### Indentering
```kotlin
// Använd 4 mellanslag för indentering
class PlantScreen {
    private val viewModel: PlantViewModel
    
    fun handleEvent(event: PlantEvent) {
        when (event) {
            is PlantEvent.LoadPlants -> {
                viewModel.loadPlants()
            }
            is PlantEvent.AddPlant -> {
                viewModel.addPlant(event.plant)
            }
        }
    }
}
```

#### Radlängd
```kotlin
// Max 100 tecken per rad
// Bryt upp långa rader
val longString = "Detta är en mycket lång sträng som behöver brytas upp " +
    "i flera rader för att följa stilguiden"

// Använd named parameters för långa funktionsanrop
PlantScreen(
    viewModel = viewModel,
    onNavigate = { route -> navigate(route) },
    modifier = Modifier.fillMaxSize()
)
```

### 4. Dokumentation

#### KDoc
```kotlin
/**
 * Beskriver funktionens syfte
 *
 * @param param1 beskrivning av första parametern
 * @param param2 beskrivning av andra parametern
 * @return beskrivning av returvärdet
 * @throws ExceptionType beskrivning av undantag
 */
fun exampleFunction(param1: String, param2: Int): Result<Unit> {
    // Implementation
}
```

#### Inline-kommentarer
```kotlin
// Använd inline-kommentarer för att förklara komplex logik
// eller markera TODO/FIXME
// TODO: Implementera felhantering
// FIXME: Korrigera prestandaproblem
```

### 5. Compose

#### Composable-funktioner
```kotlin
@Composable
fun PlantCard(
    plant: Plant,
    onPlantClick: (Plant) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onPlantClick(plant) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        // Implementation
    }
}
```

#### State-hantering
```kotlin
// Använd remember för lokal state
var expanded by remember { mutableStateOf(false) }

// Använd collectAsState för ViewModel-state
val state by viewModel.state.collectAsState()
```

### 6. Arkitektur

#### Clean Architecture
```kotlin
// Data Layer
data class PlantDto(
    val id: String,
    val name: String,
    val status: String
)

// Domain Layer
data class Plant(
    val id: String,
    val name: String,
    val status: PlantStatus
)

// Presentation Layer
data class PlantUiState(
    val plants: List<Plant>,
    val isLoading: Boolean,
    val error: String?
)
```

#### Dependency Injection
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun providePlantRepository(
        plantDao: PlantDao,
        plantApi: PlantApi
    ): PlantRepository {
        return PlantRepositoryImpl(plantDao, plantApi)
    }
}
```

### 7. Testning

#### Enhetstester
```kotlin
@Test
fun `when plant is added then it should be saved to database`() {
    // Given
    val plant = Plant("1", "Test Plant", PlantStatus.ACTIVE)
    
    // When
    repository.addPlant(plant)
    
    // Then
    verify { plantDao.insert(any()) }
}
```

#### UI-tester
```kotlin
@Test
fun plantScreen_shouldDisplayPlantList() {
    // Given
    val plants = listOf(
        Plant("1", "Plant 1", PlantStatus.ACTIVE),
        Plant("2", "Plant 2", PlantStatus.ACTIVE)
    )
    
    // When
    composeTestRule.setContent {
        PlantScreen(plants = plants)
    }
    
    // Then
    composeTestRule.onNodeWithText("Plant 1").assertIsDisplayed()
    composeTestRule.onNodeWithText("Plant 2").assertIsDisplayed()
}
```

## Verktyg

### 1. Ktlint
```bash
# Kör ktlint
./gradlew ktlintCheck

# Fixa formateringsproblem
./gradlew ktlintFormat
```

### 2. Detekt
```bash
# Kör detekt
./gradlew detekt
```

### 3. Android Studio
- Använd "Reformat Code" (Ctrl+Alt+L)
- Använd "Optimize Imports" (Ctrl+Alt+O)
- Aktivera "Format on Save" 