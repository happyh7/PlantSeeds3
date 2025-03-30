# Testning

## Översikt
PlantSeeds3 implementerar en omfattande teststrategi som täcker alla nivåer av applikationen, från enhetstester till UI-tester och integrationstester.

## Testnivåer

### 1. Enhetstester
```kotlin
class PlantRepositoryTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    
    private lateinit var repository: FakePlantRepository
    private lateinit var useCase: GetPlantsUseCase
    
    @Before
    fun setup() {
        repository = FakePlantRepository()
        useCase = GetPlantsUseCase(repository)
    }
    
    @Test
    fun `when plants exist, returns success with plants`() = runTest {
        // Given
        val plants = listOf(plant1, plant2)
        repository.setPlants(plants)
        
        // When
        val result = useCase()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(plants, result.getOrNull())
    }
    
    @Test
    fun `when no plants exist, returns empty list`() = runTest {
        // When
        val result = useCase()
        
        // Then
        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() == true)
    }
}
```

### 2. UI-tester
```kotlin
@HiltAndroidTest
class PlantsScreenTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun plantsScreen_showsPlants_whenPlantsExist() {
        // Given
        val plants = listOf(plant1, plant2)
        val viewModel = PlantsViewModel(FakePlantRepository().apply {
            setPlants(plants)
        })
        
        // When
        composeTestRule.setContent {
            PlantsScreen(viewModel)
        }
        
        // Then
        plants.forEach { plant ->
            composeTestRule.onNodeWithText(plant.name).assertIsDisplayed()
        }
    }
    
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

### 3. Integrationstester
```kotlin
@HiltAndroidTest
class PlantDatabaseIntegrationTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)
    
    @Inject
    lateinit var database: AppDatabase
    
    @Inject
    lateinit var repository: PlantRepository
    
    @Before
    fun setup() {
        database.clearAllTables()
    }
    
    @Test
    fun whenPlantIsAdded_thenItCanBeRetrieved() = runTest {
        // Given
        val plant = plant1
        
        // When
        repository.addPlant(plant)
        val result = repository.getPlantById(plant.id)
        
        // Then
        assertNotNull(result)
        assertEquals(plant, result)
    }
}
```

## Testverktyg

### 1. Mocking
```kotlin
class PlantRepositoryTest {
    @MockK
    private lateinit var localDataSource: PlantLocalDataSource
    
    @MockK
    private lateinit var remoteDataSource: PlantRemoteDataSource
    
    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }
    
    @Test
    fun `when local data exists, returns local data`() = runTest {
        // Given
        val plants = listOf(plant1, plant2)
        every { localDataSource.getPlants() } returns plants
        
        // When
        val result = repository.getPlants()
        
        // Then
        assertTrue(result.isSuccess)
        assertEquals(plants, result.getOrNull())
        verify { localDataSource.getPlants() }
        verify(exactly = 0) { remoteDataSource.getPlants() }
    }
}
```

### 2. Testregler
```kotlin
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestRule {
    override fun statement(base: Statement, description: TestDescription): Statement {
        return object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testDispatcher)
                base.evaluate()
                Dispatchers.resetMain()
            }
        }
    }
}

class CoroutineTestRule : TestRule {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    
    override fun statement(base: Statement, description: TestDescription): Statement {
        return object : Statement() {
            override fun evaluate() {
                Dispatchers.setMain(testDispatcher)
                base.evaluate()
                Dispatchers.resetMain()
            }
        }
    }
}
```

## Testkategorier

### 1. Happy Path
```kotlin
class PlantUseCaseTest {
    @Test
    fun `when all inputs are valid, plant is created successfully`() = runTest {
        // Given
        val plant = plant1
        
        // When
        val result = useCase(plant)
        
        // Then
        assertTrue(result.isSuccess)
        verify { repository.addPlant(plant) }
    }
}
```

### 2. Edge Cases
```kotlin
class PlantValidationTest {
    @Test
    fun `when plant name is empty, returns validation error`() {
        // Given
        val plant = plant1.copy(name = "")
        
        // When
        val result = validator.validate(plant)
        
        // Then
        assertFalse(result.isValid)
        assertTrue(result.errors.contains("Namn kan inte vara tomt"))
    }
}
```

### 3. Felhantering
```kotlin
class PlantRepositoryTest {
    @Test
    fun `when network error occurs, returns failure`() = runTest {
        // Given
        every { remoteDataSource.getPlants() } throws IOException()
        
        // When
        val result = repository.getPlants()
        
        // Then
        assertTrue(result.isFailure)
        assertTrue(result.exceptionOrNull() is NetworkException)
    }
}
```

## CI/CD-integration

### 1. GitHub Actions
```yaml
name: Android CI

on:
  push:
    branches: [ main ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    runs-on: ubuntu-latest
    
    steps:
    - uses: actions/checkout@v2
    
    - name: Set up JDK 11
      uses: actions/setup-java@v2
      with:
        java-version: '11'
        distribution: 'adopt'
        
    - name: Run Tests
      run: ./gradlew test
      
    - name: Run UI Tests
      run: ./gradlew connectedAndroidTest
      
    - name: Upload Test Results
      uses: actions/upload-artifact@v2
      with:
        name: test-results
        path: app/build/reports/tests/
```

### 2. Testrapportering
```kotlin
class TestReporter @Inject constructor(
    private val analytics: FirebaseAnalytics
) {
    fun reportTestResults(results: TestResults) {
        analytics.logEvent("test_results") {
            param("total_tests", results.totalTests)
            param("passed_tests", results.passedTests)
            param("failed_tests", results.failedTests)
            param("coverage", results.coverage)
        }
    }
}
```

## Best Practices

### 1. Teststruktur
- Följ AAA-mönstret (Arrange, Act, Assert)
- Använd beskrivande testnamn
- Gruppera relaterade tester
- Håll tester enkla och fokuserade

### 2. Testdata
- Använd fiktiva testdata
- Skapa testdatafabriker
- Hantera testdata effektivt
- Återanvänd testdata

### 3. Testunderhåll
- Uppdatera tester med kodändringar
- Ta bort oanvända tester
- Dokumentera tester
- Följ testkonventioner

### 4. Testprestanda
- Optimera testkörning
- Parallellisera tester
- Minimera testberoenden
- Hantera testresurser

### 5. Testkvalitet
- Sträva efter hög täckning
- Testa edge cases
- Validera testresultat
- Granska testkod 