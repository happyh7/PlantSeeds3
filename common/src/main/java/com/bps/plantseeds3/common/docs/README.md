# Common Module

Detta är en gemensam modul som innehåller återanvändbara komponenter och verktyg för PlantSeeds3-appen.

## Struktur

```
common/
├── ui/           # Återanvändbara UI-komponenter
├── util/         # Utility-klasser
├── config/       # Konfigurationsklasser
└── docs/         # Dokumentation
```

## Komponenter

### UI
- `CommonButton`: En återanvändbar knapp-komponent
  - Stödjer anpassad bakgrundsfärg och textfärg
  - Inbyggd hantering av inaktiverat tillstånd
  - Standardhöjd på 50dp
  - Används för primära åtgärder i appen

- `CommonInputField`: En återanvändbar textfält-komponent
  - Stödjer både vanlig text och lösenord
  - Inbyggd felhantering och felmeddelanden
  - Anpassad padding och bredd
  - Används för all textinmatning i appen

- `CommonTopAppBar`: En återanvändbar top app bar-komponent
  - Stödjer tillbakaknapp
  - Anpassad titel med ellipsis för långa texter
  - Följer Material Design 3-riktlinjer
  - Används för alla skärmers header

### Utility
- `DateUtils`: Verktyg för datumhantering
  - Konvertering mellan olika datumformat
  - Validering av datum
  - Formatering för visning
  - Används för all datumhantering i appen

- `ValidationUtils`: Verktyg för validering
  - Validering av namn, beskrivningar, nummer
  - E-postvalidering
  - Lösenordsvalidering
  - Listvalidering
  - Sanitering av användarinmatning

- `Result`: En wrapper-klass för att hantera operationer
  - Stödjer Success, Error och Loading tillstånd
  - Säker hantering av API-anrop
  - Används för all asynkron datahantering

- `NetworkUtils`: Verktyg för nätverkshantering
  - Kontroll av nätverksanslutning
  - Stödjer olika Android-versioner
  - Används för nätverksrelaterade operationer

### Config
- `PreferencesManager`: Hanterar app-inställningar
  - Använder DataStore för persistent lagring
  - Hanterar tema, språk och notifieringar
  - Thread-safe implementation
  - Dependency injection med Hilt

- `AppConfig`: Innehåller app-konfigurationskonstanter
  - API-konfiguration
  - Valideringsregler
  - UI-konstanter
  - Databasinställningar

## Användning

### Installation
För att använda denna modul i andra moduler, lägg till följande beroende i modulens `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":common"))
}
```

### Exempel

#### CommonButton
```kotlin
CommonButton(
    text = "Spara",
    onClick = { /* Hantera klick */ },
    enabled = true,
    backgroundColor = Color(0xFF4CAF50)
)
```

#### CommonInputField
```kotlin
CommonInputField(
    value = text,
    onValueChange = { text = it },
    label = "Namn",
    isError = hasError,
    errorMessage = errorMessage
)
```

#### CommonTopAppBar
```kotlin
CommonTopAppBar(
    title = "Min Skärm",
    onNavigateBack = { /* Hantera navigation */ }
)
```

## Regler och Best Practices

1. **UI-komponenter**
   - Följ alltid Material Design 3-riktlinjer
   - Använd standardiserade färger från tema
   - Implementera stöd för mörkt läge
   - Hantera tillgänglighet (accessibility)

2. **Utility-klasser**
   - Håll funktioner pure och stateless
   - Dokumentera alla publika funktioner
   - Hantera fel på ett konsekvent sätt
   - Använd suspend-funktioner för asynkrona operationer

3. **Konfiguration**
   - Använd konstanter för alla konfigurationsvärden
   - Följ DRY-principen (Don't Repeat Yourself)
   - Implementera dependency injection
   - Hantera säkerhet och känslig data

4. **Kodkvalitet**
   - Skriv enhetstester för all logik
   - Följ Kotlin coding conventions
   - Använd null-safety konsekvent
   - Dokumentera komplexa algoritmer

## Versionshantering

Se [CHANGELOG.md](CHANGELOG.md) för detaljerad information om ändringar.

## Roadmap

Se [ROADMAP.md](ROADMAP.md) för planerade förbättringar och nya funktioner.

## Dokumentation

Se `docs/`-mappen för mer detaljerad dokumentation om varje komponent. 