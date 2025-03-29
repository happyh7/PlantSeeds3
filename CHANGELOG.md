# Changelog

Alla viktiga ändringar i projektet dokumenteras här.

## [Unreleased]

### Added
- Grundläggande projektstruktur
- Databasimplementation med Room
- Grundläggande UI-komponenter med Material 3
- CRUD-funktionalitet för frön
- Sökfunktionalitet
- Trädgårdsfunktionalitet
- Fröbank med grundläggande funktionalitet
- Dark/light theme
- Favoritmarkering för frön
- Kategorisering av frön
- Filtrering av frön efter kategori, livslängd och växtzon
- Sortering och filtrering i fröbanken
  - Sökfunktionalitet för att hitta frön efter namn, art eller kategori
  - Sorteringsmöjligheter för namn, art och kategori (A-Ö och Ö-A)
  - Förbättrad visning av frölistan med laddningsindikator och tomma tillstånd

### Changed
- Uppdaterat databasversion till 11
- Förbättrat formulärdesign för fröhantering
- Uppdaterat UI-komponenter för bättre användarupplevelse

### Fixed
- Databasintegritetsproblem med gardens-tabellen
- Kompileringsfel i AddEditSeedViewModel
- Problem med null-checks i datamodeller

### Technical
- Implementerat MVVM-arkitektur
- Sätta upp Clean Architecture
- Konfigurerat Hilt för dependency injection
- Implementerat Room-databas med migrations
- Strukturerat projekt enligt Clean Architecture-principer

## [0.1.0] - 2024-03-28
### Added
- Inledande projektstruktur
- Grundläggande databasimplementation
- Basis UI-komponenter 