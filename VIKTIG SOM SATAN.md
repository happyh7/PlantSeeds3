# PlantSeeds3 Projekt Kontext

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
PlantSeeds3 är en Android-applikation skriven i Kotlin som använder Jetpack Compose för UI. Appen hjälper användare att hantera sina trädgårdar, frön och plantor med en offline-first approach och Firebase som backend.

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