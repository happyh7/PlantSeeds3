# Lokalisering

## Översikt
PlantSeeds3 stödjer flera språk för att göra appen tillgänglig för användare över hela världen. Lokaliseringen hanteras genom strängresurser och dynamisk språkväxling.

## Struktur

### 1. Resursfiler
```
app/src/main/res/
├── values/           # Engelska (standard)
│   └── strings.xml
├── values-sv/       # Svenska
│   └── strings.xml
├── values-de/       # Tyska
│   └── strings.xml
└── values-fr/       # Franska
    └── strings.xml
```

### 2. Strängresurser
```xml
<!-- values/strings.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">PlantSeeds3</string>
    <string name="seeds">Seeds</string>
    <string name="plants">Plants</string>
    <string name="gardens">Gardens</string>
    <!-- ... -->
</resources>

<!-- values-sv/strings.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="app_name">PlantSeeds3</string>
    <string name="seeds">Frön</string>
    <string name="plants">Plantor</string>
    <string name="gardens">Trädgårdar</string>
    <!-- ... -->
</resources>
```

## Implementation

### 1. Språkhantering
```kotlin
class LanguageManager @Inject constructor(
    private val context: Context,
    private val dataStore: DataStore<Preferences>
) {
    private val LANGUAGE_KEY = stringPreferencesKey("language")
    
    suspend fun setLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
        updateLocale(language)
    }
    
    private fun updateLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
    }
}
```

### 2. Språkväxling
```kotlin
@Composable
fun LanguageSelector(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    
    Box {
        OutlinedButton(
            onClick = { expanded = true }
        ) {
            Text(currentLanguage)
            Icon(Icons.Default.ArrowDropDown, "Välj språk")
        }
        
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("English") },
                onClick = {
                    onLanguageSelected("en")
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Svenska") },
                onClick = {
                    onLanguageSelected("sv")
                    expanded = false
                }
            )
            // ... fler språk
        }
    }
}
```

### 3. Användning i UI
```kotlin
@Composable
fun SeedsScreen() {
    val context = LocalContext.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(context.getString(R.string.seeds)) }
            )
        }
    ) { padding ->
        // ... innehåll
    }
}
```

## Formatering

### 1. Datum
```kotlin
fun formatDate(date: Date, locale: Locale): String {
    return SimpleDateFormat("yyyy-MM-dd", locale).format(date)
}
```

### 2. Tal
```kotlin
fun formatNumber(number: Double, locale: Locale): String {
    return NumberFormat.getNumberInstance(locale).format(number)
}
```

### 3. Valuta
```kotlin
fun formatCurrency(amount: Double, locale: Locale): String {
    return NumberFormat.getCurrencyInstance(locale).format(amount)
}
```

## Testning

### 1. Språktester
```kotlin
class LanguageTest {
    @Test
    fun `when language is changed, strings are updated`() {
        // Given
        val context = ApplicationProvider.getApplicationContext<Context>()
        val languageManager = LanguageManager(context, dataStore)
        
        // When
        runBlocking {
            languageManager.setLanguage("sv")
        }
        
        // Then
        assertEquals("Frön", context.getString(R.string.seeds))
    }
}
```

### 2. UI-tester
```kotlin
@HiltAndroidTest
class LanguageSelectorTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Test
    fun languageSelector_showsAllLanguages() {
        // When
        composeTestRule.setContent {
            LanguageSelector(
                currentLanguage = "English",
                onLanguageSelected = {}
            )
        }
        
        // Then
        composeTestRule.onNodeWithText("English").assertIsDisplayed()
        composeTestRule.onNodeWithText("Svenska").assertIsDisplayed()
    }
}
```

## Best Practices

### 1. Stränghantering
- Använd alltid strängresurser istället för hårdkodade strängar
- Använd beskrivande nycklar för strängresurser
- Gruppera relaterade strängar i separata filer

### 2. Språkväxling
- Spara användarens språkpreferens
- Uppdatera UI direkt vid språkväxling
- Hantera fallback till standardspråk

### 3. Formatering
- Använd lokala formateringsregler
- Hantera olika datum- och tidsformat
- Anpassa valutaformatering

### 4. Prestanda
- Cacha översatta strängar
- Lazy load språkresurser
- Optimera strängresursfiler 