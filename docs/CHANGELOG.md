# Changelog

Alla viktiga ändringar i projektet kommer att dokumenteras i denna fil.

## [Unreleased]

### Added
- Implementerad modulär arkitektur med följande moduler:
  - common/
  - auth/
  - seed/
  - garden/
  - data/
  - domain/
  - presentation/
- Omfattande dokumentationsstruktur med följande mappar:
  - api/
  - architecture/
  - deployment/
  - development/
  - features/
  - project/
  - security/
- Svenska översättningar för plant status
- Ny displayName property i PlantStatus enum
- Implementerad SeedListScreen i seed-modulen
- Konfigurerad Gradle med version catalog för bättre dependency management

### Changed
- Uppdaterad projektstruktur för bättre organisation
- Förbättrad dokumentationsstruktur
- Omorganiserad dokumentation i logiska mappar
- Uppdaterad Gradle-konfiguration för stöd av modulär arkitektur
- Uppdaterad Compose Compiler till version 1.5.8 för kompatibilitet med Kotlin 1.9.22

### Removed
- VIKTIG SOM SATAN.md
- project_context.md
- Duplicerad SECURITY.md från development-mappen

### Fixed
- Korrigerad dokumentationsstruktur
- Förbättrad organisation av dokumentation
- Åtgärdat KAPT-plugin konfiguration
- Åtgärdat Compose Compiler version-konflikt

## [0.1.0] - 2024-03-29
### Added
- Initial projektstruktur
- Grundläggande dokumentation
- Firebase-integration
- Plant management funktionalitet

## [1.1.0] - 2024-04-01

### Tillagt
- Omfattande felhanteringsmekanism för databasoperationer
- Ny DatabaseException-hierarki för strukturerad felhantering
- Förbättrad databasmigrationsstrategi
- Index för bättre prestanda i databasen

### Ändrat
- Konsoliderat databaser till en enda PlantSeedsDatabase
- Uppdaterat databasversion till 14
- Förbättrat felhantering i repositories

### Fixat
- Problem med saknade index för foreign keys
- Dagger-processor varningar 