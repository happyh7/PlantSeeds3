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
- `CommonInputField`: En återanvändbar textfält-komponent
- `CommonTopAppBar`: En återanvändbar top app bar-komponent

### Utility
- `DateUtils`: Verktyg för datumhantering
- `ValidationUtils`: Verktyg för validering
- `Result`: En wrapper-klass för att hantera operationer
- `NetworkUtils`: Verktyg för nätverkshantering

### Config
- `PreferencesManager`: Hanterar app-inställningar
- `AppConfig`: Innehåller app-konfigurationskonstanter

## Användning

För att använda denna modul i andra moduler, lägg till följande beroende i modulens `build.gradle.kts`:

```kotlin
dependencies {
    implementation(project(":common"))
}
```

## Dokumentation

Se `docs/`-mappen för mer detaljerad dokumentation om varje komponent. 