# Changelog

Alla viktiga ändringar i common-modulen kommer att dokumenteras i denna fil.

## [1.0.0] - 2024-03-19

### Lagt till
- Grundläggande UI-komponenter
  - `CommonButton` för standardiserade knappar
  - `CommonInputField` för textinmatning
  - `CommonTopAppBar` för skärmrubriker

- Utility-klasser
  - `DateUtils` för datumhantering
  - `ValidationUtils` för validering
  - `Result` för operationer
  - `NetworkUtils` för nätverkshantering

- Konfigurationsklasser
  - `PreferencesManager` för inställningar
  - `AppConfig` för app-konfiguration

### Ändrat
- Uppdaterat minSdk till 26 för stöd av java.time API
- Konfigurerat Compose-kompilator till version 1.5.8
- Lagt till lint-baseline för hantering av varningar

### Fixat
- Korrigerat typfel i CommonTopAppBar
- Lagt till nödvändiga nätverksbehörigheter
- Förbättrat felhantering i utility-klasserna

### Säkerhet
- Implementerat säker hantering av användarinmatning
- Lagt till validering för all användardata
- Säkerställt thread-safe implementation av PreferencesManager

## [0.1.0] - 2024-03-18
- Initial setup av common-modulen
- Grundläggande projektstruktur
- Baskonfiguration för Gradle 