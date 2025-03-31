# PlantSeeds3 Modulstruktur

## Översikt
Detta dokument beskriver hur modulerna ska implementeras i PlantSeeds3-projektet. Varje modul kan delas separat i Cursor för fokuserad utveckling, men hela projektet måste finnas tillgängligt för korrekt byggning och beroendehantering.

## Dokumentationsstruktur
Varje modul ska ha en `/docs` mapp med följande struktur:
```
/docs
├── README.md           # Översikt över modulen och dess syfte
├── CHANGELOG.md        # Versionshistorik och ändringar
├── ROADMAP.md         # Planerad utveckling och mål
├── STATUS.md          # Nuvarande status och framsteg
└── ARCHITECTURE.md    # Teknisk arkitektur och designbeslut
```

### Dokumentationsinnehåll

#### README.md
- Modulens syfte och ansvarsområde
- Beroenden och krav
- Installation och setup
- Användningsexempel
- Länkar till relaterad dokumentation

#### CHANGELOG.md
- Versionshistorik i formatet:
  ```
  ## [X.Y.Z] - YYYY-MM-DD
  ### Added
  - Ny funktionalitet
  ### Changed
  - Ändringar i befintlig funktionalitet
  ### Deprecated
  - Föråldrad funktionalitet
  ### Removed
  - Borttagen funktionalitet
  ### Fixed
  - Bugfixar
  ```

#### ROADMAP.md
- Kortsiktiga mål (1-3 månader)
- Långsiktiga mål (3-12 månader)
- Planerade förbättringar
- Kända begränsningar
- Prioriterade uppgifter

#### STATUS.md
- Nuvarande utvecklingsfas
- Färdigställd funktionalitet
- Pågående arbete
- Kända problem
- Teststatus
- Prestandametrics

#### ARCHITECTURE.md
- Systemarkitektur
- Designmönster
- Datastrukturer
- API-design
- Säkerhetsöverväganden
- Prestandaoptimeringar

## Modulista

### 1. common
- **Syfte**: Delad funktionalitet mellan alla moduler
- **Innehåll**:
  - UI-komponenter (buttons, cards, dialogs)
  - Utilities (NetworkUtils, DateUtils, etc.)
  - Konstanter
  - Delade modeller
- **Beroenden**: Minimala, endast Android SDK och grundläggande bibliotek
- **Implementation**:
  - Skapa mapp: `mkdir common`
  - Skapa build.gradle.kts med minimala beroenden
  - Implementera UI-komponenter i `common/src/main/java/com/bps/plantseeds3/common/ui`
  - Implementera utilities i `common/src/main/java/com/bps/plantseeds3/common/util`
  - Implementera konstanter i `common/src/main/java/com/bps/plantseeds3/common/constants`
  - Implementera delade modeller i `common/src/main/java/com/bps/plantseeds3/common/model`
  - Skapa dokumentationsstruktur i `common/docs`

### 2. domain
- **Syfte**: Affärslogik och domänmodeller
- **Innehåll**:
  - Use cases
  - Repository interfaces
  - Domain models
  - Domain events
- **Beroenden**: 
  - common
  - Kotlin Coroutines
  - Hilt för dependency injection
- **Implementation**:
  - Skapa mapp: `mkdir domain`
  - Skapa build.gradle.kts med beroenden
  - Implementera repository interfaces i `domain/src/main/java/com/bps/plantseeds3/domain/repository`
  - Implementera use cases i `domain/src/main/java/com/bps/plantseeds3/domain/use_case`
  - Implementera domain models i `domain/src/main/java/com/bps/plantseeds3/domain/model`
  - Implementera domain events i `domain/src/main/java/com/bps/plantseeds3/domain/event`
  - Skapa dokumentationsstruktur i `domain/docs`

### 3. data
- **Syfte**: Datahantering och externa tjänster
- **Innehåll**:
  - Repository implementations
  - Data sources (Remote, Local)
  - DTOs
  - Mappers
- **Beroenden**:
  - domain
  - Room för lokal lagring
  - Retrofit för nätverksanrop
  - Firebase
- **Implementation**:
  - Skapa mapp: `mkdir data`
  - Skapa build.gradle.kts med beroenden
  - Implementera repository implementations i `data/src/main/java/com/bps/plantseeds3/data/repository`
  - Implementera data sources i `data/src/main/java/com/bps/plantseeds3/data/source`
  - Implementera DTOs i `data/src/main/java/com/bps/plantseeds3/data/remote/dto`
  - Implementera mappers i `data/src/main/java/com/bps/plantseeds3/data/mapper`
  - Skapa dokumentationsstruktur i `data/docs`

### 4. presentation
- **Syfte**: UI och användarinteraktion
- **Innehåll**:
  - Screens
  - ViewModels
  - UI states
  - Navigation
- **Beroenden**:
  - domain
  - common
  - Compose
  - Hilt
- **Implementation**:
  - Skapa mapp: `mkdir presentation`
  - Skapa build.gradle.kts med beroenden
  - Implementera screens i `presentation/src/main/java/com/bps/plantseeds3/presentation/screen`
  - Implementera ViewModels i `presentation/src/main/java/com/bps/plantseeds3/presentation/viewmodel`
  - Implementera UI states i `presentation/src/main/java/com/bps/plantseeds3/presentation/state`
  - Implementera navigation i `presentation/src/main/java/com/bps/plantseeds3/presentation/navigation`
  - Skapa dokumentationsstruktur i `presentation/docs`

### 5. app
- **Syfte**: Applikationsstart och konfiguration
- **Innehåll**:
  - Application class
  - DI modules
  - Navigation setup
  - Theme setup
- **Beroenden**:
  - Alla andra moduler
  - Firebase
  - Hilt
- **Implementation**:
  - Konfigurera build.gradle.kts med alla modulberoenden
  - Implementera Application class i `app/src/main/java/com/bps/plantseeds3/PlantSeeds3App`
  - Implementera DI modules i `app/src/main/java/com/bps/plantseeds3/di`
  - Konfigurera navigation i `app/src/main/java/com/bps/plantseeds3/navigation`
  - Konfigurera tema i `app/src/main/java/com/bps/plantseeds3/ui/theme`
  - Skapa dokumentationsstruktur i `app/docs`

## Byggprocess
1. Uppdatera settings.gradle.kts med nya moduler
2. Skapa modulmapp och build.gradle.kts
3. Konfigurera beroenden
4. Implementera funktionalitet
5. Testa modulen
6. Integrera med andra moduler
7. Skapa och uppdatera dokumentation

## Cursor Integration
- Varje modul kan delas separat i Cursor
- Hela projektet måste finnas för korrekt byggning
- Använd `git checkout feature/modular-restructure` för att byta till rätt branch
- Uppdatera settings.gradle.kts innan byggning
- Kör `./gradlew build` för att verifiera byggningen

## Testning
- Varje modul ska ha sina egna tester
- Använd JUnit för enhetstester
- Använd Compose UI-tester för presentation
- Implementera repository-tester i data-modulen
- Implementera use case-tester i domain-modulen

## Dependencies
- Använd versioner från root build.gradle.kts
- Uppdatera versionsvariabler i root build.gradle.kts vid behov
- Följ beroendehierarkin: app -> presentation -> data -> domain -> common

## Git Workflow
1. Skapa ny branch för varje modul
2. Implementera modulen
3. Testa och verifiera
4. Merge till feature/modular-restructure
5. Uppdatera dokumentation 