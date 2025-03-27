# PlantSeeds3 - Projektkontext

Kör alltid gradle efter kodändringar, för att se så allr fungerar som det ska.
## Syfte
PlantSeeds3 är en Android-app för att hantera fröer och växtinformation. Appen ska vara enkel att använda samtidigt som den erbjuder omfattande funktionalitet för mer avancerade användare.

## Nuvarande Status
- Grundläggande CRUD-funktionalitet för trädgårdar och växter är implementerad
- Trädgårdsväljare och sparning av vald trädgård mellan sessioner är implementerad
- Material Design 3 är implementerat med stöd för både ljust och mörkt tema
- Grundläggande felhantering och användarfeedback är implementerad
- Navigering mellan olika skärmar är implementerad
- Databasintegration med Room är implementerad

## Prioriteringar
1. **Enkelhet först**
   - Endast namn är obligatoriskt för att lägga till ett frö
   - Övrig information kan fyllas i senare
   - Tydlig och intuitiv användargränssnitt

2. **Flexibilitet**
   - Användare kan välja att använda förslag från databasen
   - Möjlighet att ta bort eller ändra information efter behov
   - Stöd för både enkla och detaljerade växtbeskrivningar

3. **Databasintegration**
   - Automatiska förslag baserade på vad användaren skriver
   - Möjlighet att fylla i all information från ett valt frö
   - Enkel återställning om fel frö väljs

4. **Kvalitetssäkring**
   - Kör Gradle-byggning (`./gradlew clean build --stacktrace`) efter varje kodändring
   - Åtgärda eventuella kompileringsfel direkt
   - Håll koden felfri och byggbar

## Riktlinjer för utveckling
1. **Användargränssnitt**
   - Följ Material Design 3
   - Använd svenska som standardspråk
   - Håll formulär och fält konsekvent formaterade

2. **Databas**
   - Använd Room för lokal lagring
   - Implementera migrations för databasuppdateringar
   - Håll datamodeller enkla och utökbara

3. **Kodstruktur**
   - Följ MVVM-arkitektur
   - Använd Jetpack Compose för UI
   - Implementera Clean Architecture-principer

## Funktioner och funktionalitet
1. **Grundläggande funktioner**
   - Lägga till/redigera frön
   - Hantera frölista
   - Söka och filtrera frön

2. **Avancerade funktioner**
   - Detaljerad växtinformation
   - Planteringsinstruktioner
   - Skötselriktlinjer
   - Följeslagare och undvikande växter

3. **Framtida funktioner**
   - Planteringskalender
   - Växtjournal
   - Delning av fröer och information
   - Molnbackup

## Tekniska val
- Kotlin som programmeringsspråk
- Jetpack Compose för UI
- Room för databas
- Hilt för dependency injection
- Material Design 3 för design
- Clean Architecture för kodstruktur

## Uppdateringar
Detta dokument ska uppdateras när nya prioriteringar eller riktlinjer läggs till, eller när befintliga ändras.

## Appbeskrivning
PlantSeeds3 är en trädgårdsassistent-app som hjälper användare att hantera sina odlingar från frö till skörd. Appen är designad för både nybörjare och erfarna odlare, med fokus på användarvänlighet och praktisk funktionalitet.

### Huvudfunktioner
1. **Trädgårdshantering**
   - Skapa och hantera flera trädgårdar
   - Dokumentera trädgårdens egenskaper (storlek, plats, jordtyp)
   - Visualisera trädgårdsytor och planteringar
   - Spåra trädgårdens utveckling över tid

2. **Fröbibliotek**
   - Katalogisera dina frön
   - Sökbar databas med plantinformation
   - Planteringsguider och skötselråd
   - Spåra frönas ålder och grobarhet

3. **Plantspårning**
   - Följ varje plantas utveckling
   - Statusuppdateringar (planterad, groddar, växande, blomning, skörd)
   - Fotodokumentation av tillväxt
   - Anteckningar och observationer

4. **Smart Planering**
   - Säsongsbaserad planteringskalender
   - Påminnelser för vattning och skötsel
   - Skördeprognos
   - Väderintegration för optimal plantering

5. **Offline-first Approach**
   - Fungerar utan internetanslutning
   - Automatisk synkronisering när online
   - Säker datalagring lokalt
   - Backup av viktig information

### Användarupplevelse
- Modern och intuitiv design med Material 3
- Anpassningsbar för olika skärmstorlekar
- Stöd för både ljust och mörkt tema
- Tillgänglighetsanpassad
- Snabb och responsiv

### Målgrupp
- Hobbyodlare
- Balkongodlare
- Kolonilottsägare
- Trädgårdsintresserade
- Självhushållare

### Tekniska Fördelar
- Minimal batterianvändning
- Effektiv datahantering
- Snabb uppstart
- Låg dataförbrukning
- Säker användardata

## Projektöversikt
PlantSeeds3 är en Android-applikation för att hantera och planera trädgårdsodling. Appen hjälper användare att hålla reda på sina frön, plantor och trädgårdar.

## Teknisk Stack
- Kotlin
- Jetpack Compose för UI
- Room för databashantering
- Dagger Hilt för dependency injection
- MVVM-arkitektur med Clean Architecture
- Kotlin Coroutines och Flow för asynkron programmering

## Huvudfunktioner
1. Hantera frön (CRUD-operationer)
2. Hantera trädgårdar
3. Plantera och spåra växter
4. Sökfunktionalitet
5. Filtreringsfunktioner
6. Detaljerad information om varje växt

## Utvecklingsfaser

### Fas 1: Android-grundfunktioner
1. ✓ Grundläggande projektstruktur
2. ✓ Databasimplementation
3. ✓ Grundläggande UI-komponenter
4. ✓ CRUD för frön
5. ✓ Sökfunktionalitet
6. ⚪ Implementera trädgårdsfunktionalitet
7. ⚪ Implementera planteringsfunktionalitet
8. ⚪ Implementera tidslinje och påminnelser
9. ⚪ Implementera väderintegration
10. ⚪ Implementera bildhantering för frön och plantor

### Fas 2: Android-förbättringar
1. ⚪ Förbättra användargränssnittet
2. ⚪ Lägga till animationer
3. ⚪ Implementera dark/light theme
4. ⚪ Optimera prestanda
5. ⚪ Implementera backup/restore-funktionalitet
6. ⚪ Lägga till statistik och rapporter
7. ⚪ Implementera widgets
8. ⚪ Förbättra felhantering och användarfeedback

### Fas 3: iOS-portering med Kotlin Multiplatform Mobile (KMM)

#### Förberedelser (Prio 1)
1. ⚪ Analysera nuvarande kodbas för delning
2. ⚪ Skapa en migrationsplan
3. ⚪ Sätta upp utvecklingsmiljö för iOS (Xcode, CocoaPods)
4. ⚪ Konfigurera KMM-projekt

#### Kodmigrering (Prio 2)
1. ⚪ Migrera datamodeller till shared module
   - Seed
   - Garden
   - Plant
   - Alla relaterade dataklasser
2. ⚪ Migrera repositories till shared module
   - SeedRepository
   - GardenRepository
   - PlantRepository
3. ⚪ Migrera use cases till shared module
4. ⚪ Ersätta Android-specifika beroenden med multiplattformslösningar:
   - Room → SQLDelight
   - Dagger Hilt → Koin
   - AndroidX → Ktor för nätverk

#### iOS-implementation (Prio 3)
1. ⚪ Skapa grundläggande iOS-projekt med SwiftUI
2. ⚪ Implementera UI-komponenter:
   - SeedList
   - AddEditSeed
   - GardenList
   - PlantList
3. ⚪ Implementera navigering
4. ⚪ Implementera iOS-specifika features:
   - WidgetKit
   - Push Notifications
   - iCloud backup
5. ⚪ Anpassa UI för iOS-designriktlinjer

#### Testning och Optimering (Prio 4)
1. ⚪ Implementera tester för shared code
2. ⚪ Implementera UI-tester för iOS
3. ⚪ Prestandaoptimering
4. ⚪ Minnesoptimering
5. ⚪ Batterianvändningsoptimering

#### Distribution (Prio 5)
1. ⚪ Förbereda App Store-material
2. ⚪ Skapa screenshots och beskrivningar
3. ⚪ Implementera analytics och crash reporting
4. ⚪ Genomföra App Store Review Guidelines
5. ⚪ Publicera på App Store

## Kodriktlinjer
- Följ Clean Architecture-principer
- Använd beskrivande namngivning
- Dokumentera komplexa funktioner
- Skriv tester för kritisk funktionalitet
- Följ plattformsspecifika designriktlinjer

## Databasstruktur
[Beskrivning av databasschema och relationer]

## API-integration
[Beskrivning av externa API:er och integrationer]

## Utvecklingsprocess
- All kod skrivs av AI
- Utvecklaren utför endast fysiska åtgärder som AI inte kan göra
- Utvecklaren behöver tydliga och detaljerade instruktioner för alla manuella åtgärder
- AI ska alltid förklara varför en ändring behövs och hur den ska utföras
- Varje ny funktion implementeras och testas separat innan nästa påbörjas
- Alla ändringar dokumenteras noggrant

## Teknisk Stack
### Frontend
- Språk: Kotlin 1.9.x
- UI: Jetpack Compose 1.5.x
- Navigation: Navigation Compose
- Bildhantering: Coil
- UI-komponenter: Material3

### Backend & Databas
- Cloud: Firebase/Firestore
- Lokal databas: Room
- Synkronisering: Custom SyncService
- Offline-stöd: Room + WorkManager

### Arkitektur & Verktyg
- Arkitekturmönster: MVVM + Clean Architecture
- Dependency Injection: Hilt
- Asynkron programmering: Coroutines och Flow
- Build system: Gradle 8.x
- Versionshantering: Git
- Testning: JUnit, Espresso, Compose Testing

## Datamodeller
### Garden
```kotlin
data class Garden(
    val id: String = "",
    val name: String,
    val location: String,
    val description: String? = null,
    val seedIds: List<String> = emptyList(),
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
```

### Seed
```kotlin
data class Seed(
    val id: String = "",
    val name: String,
    val species: String? = null,
    val description: String? = null,
    val plantingInstructions: String? = null,
    val daysToGermination: Int? = null,
    val daysToHarvest: Int? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
```

### Plant
```kotlin
data class Plant(
    val id: String = "",
    val seedId: String,
    val gardenId: String,
    val status: PlantStatus = PlantStatus.PLANTED,
    val plantedDate: Date = Date(),
    val expectedHarvestDate: Date? = null,
    val notes: String? = null,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)
```

## Implementeringsordning
1. **Projektstruktur och Setup** (Steg 1)
   - Skapa nytt Android Studio-projekt
   - Konfigurera build.gradle
   - Sätta upp grundläggande projektstruktur
   - Implementera tema och färger
   - Konfigurera Firebase

2. **Datamodeller och Room** (Steg 2)
   - Implementera datamodeller
   - Sätta upp Room-databas
   - Skapa DAO:er
   - Testa databasoperationer

3. **Autentisering** (Steg 3)
   - Google Sign-In
   - Inloggningsskärm
   - Användarhantering
   - Användarspecifik data

4. **Navigation** (Steg 4)
   - NavGraph
   - Bottom navigation
   - TopBar
   - Grundläggande UI-komponenter

5. **Gardens** (Steg 5)
   - GardensScreen
   - GardenDetailsScreen
   - AddGardenScreen
   - Garden CRUD-operationer

6. **Seeds** (Steg 6)
   - SeedsScreen
   - SeedDetailsScreen
   - AddSeedScreen
   - Seed CRUD-operationer
   - Sökfunktionalitet

7. **Plants** (Steg 7)
   - PlantDetailsScreen
   - AddPlantScreen
   - Plant CRUD-operationer
   - Statushantering

8. **Synkronisering** (Steg 8)
   - FirestoreRepository
   - SyncService
   - Offline-cache
   - Konflikthantering

9. **Avancerade Funktioner** (Steg 9)
   - Väderintegration
   - Notifikationer
   - Statistik
   - Delning

10. **Optimering** (Steg 10)
    - Prestandaoptimering
    - UI/UX-förbättringar
    - Felhantering
    - Testning

## Viktiga Filer och Deras Syfte
### Konfiguration
- `google-services.json`: Firebase-konfiguration
- `build.gradle`: Projektberoenden
- `AndroidManifest.xml`: App-konfiguration
- `.gitignore`: Git-konfiguration

### Data
- `data/model/`: Datamodeller
- `data/repository/`: Repositories
- `data/sync/`: Synkroniseringslogik
- `data/offline/`: Offline-hantering

### UI
- `ui/screens/`: Huvudskärmar
- `ui/components/`: Återanvändbara komponenter
- `ui/viewmodels/`: ViewModels
- `ui/navigation/`: Navigeringslogik

### Utils
- `utils/`: Hjälpklasser och funktioner

## Testning
### Unit Tests
- Repository-tester
- ViewModel-tester
- Datamodell-tester

### Integration Tests
- Databas-tester
- Synkroniseringstester
- API-tester

### UI Tests
- Compose-tester
- End-to-end-tester
- Navigeringstester

## Felhantering
- Användarvänliga felmeddelanden
- Offline-fallback
- Automatisk återhämtning
- Loggning

## Säkerhet
- Krypterad datalagring
- Säkra API-anrop
- Användarautentisering
- Datavalidering

## Prestanda
- Lazy loading
- Bildcachning
- Minneshantering
- Batterioptimeringar

## Tillgänglighet
- TalkBack-stöd
- Skalbar text
- Kontrastförhållanden
- Färgblindhetsstöd

## Dataflöde
### Lokal Data
1. **Room Database**
   - Primär datakälla för appen
   - Caching av all data
   - Offline-first approach
   - Versionshantering av schema

2. **Preferences**
   - Användarinställningar
   - App-konfiguration
   - Cacheinställningar
   - Synkroniseringsstatus

### Molndata
1. **Firestore**
   - Backup av användardata
   - Delning mellan enheter
   - Säkerhetskopior
   - Användarspecifik data

2. **Firebase Storage**
   - Bildlagring
   - Dokumentlagring
   - Backup av media

### Synkronisering
- Automatisk synkronisering vid nätverksanslutning
- Konflikthantering vid samtidiga ändringar
- Versionshantering av data
- Återhämtning vid fel

## Användarflöde
1. **Onboarding**
   - Välkomstskärm
   - Introduktion till huvudfunktioner
   - Inloggning/Registrering
   - Initiala inställningar

2. **Huvudnavigering**
   - Bottom navigation för huvudfunktioner
   - Snabbåtkomst till vanliga åtgärder
   - Kontextuell navigation
   - Gesture-baserad navigation

3. **Trädgårdshantering**
   - Översikt över trädgårdar
   - Detaljvy för varje trädgård
   - Lägg till/redigera trädgård
   - Planteringsöversikt

4. **Fröhantering**
   - Fröbibliotek
   - Sökfunktion
   - Detaljerad fröinformation
   - Planteringsguider

5. **Planthantering**
   - Plantöversikt
   - Statusuppdateringar
   - Tillväxtspårning
   - Skördeplanering

## Versionshantering
### Git Branches
- `main`: Produktionskod
- `develop`: Utvecklingsbranch
- `feature/*`: Nya funktioner
- `bugfix/*`: Buggfixar
- `release/*`: Releaseförberedelser

### Versioner
- Semantic Versioning (MAJOR.MINOR.PATCH)
- Release Notes för varje version
- Changelog
- Migration guides

### Releaseprocess
1. Kodfrysning
2. Testning
3. Buggfixar
4. Release kandidat
5. Final release 