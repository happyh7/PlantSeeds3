# PlantSeeds3 - Projektkontext

Kör alltid gradle efter kodändringar, för att se så allr fungerar som det ska.

> **Viktigt**: Uppdatera alltid [CHANGELOG.md](CHANGELOG.md) när du gör ändringar i projektet. Detta hjälper oss att hålla koll på utvecklingen och göra det enklare för nya utvecklare att förstå projektets historia.

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
6. ✓ Implementera trädgårdsfunktionalitet
7. ⚪ Implementera planteringsfunktionalitet:
   - ⚪ Skapa grundläggande planteringsvy
   - ⚪ Implementera drag-and-drop för plantor
   - ⚪ Lägga till plantinformation
   - ⚪ Hantera plantstatus
   - ⚪ Implementera plantredigering
   - ⚪ Lägga till plantkommentarer
8. ⚪ Implementera tidslinje och påminnelser:
   - ⚪ Skapa tidslinjevy
   - ⚪ Implementera datumhantering
   - ⚪ Lägga till påminnelser
   - ⚪ Hantera upprepande händelser
   - ⚪ Implementera notifikationer
   - ⚪ Lägga till kalenderintegration
9. ⚪ Implementera väderintegration:
   - ⚪ Ansluta till väder-API
   - ⚪ Visa aktuellt väder
   - ⚪ Implementera väderprognos
   - ⚪ Lägga till väderbaserade rekommendationer
   - ⚪ Hantera väderhistorik
10. ⚪ Implementera bildhantering för frön och plantor:
    - ⚪ Implementera kameraintegration
    - ⚪ Hantera bildlagring
    - ⚪ Implementera bildredigering
    - ⚪ Lägga till bildkommentarer
    - ⚪ Implementera bildgalleri
    - ⚪ Hantera bildbackup

### Fas 2: Android-förbättringar
1. ⚪ Förbättra användargränssnittet:
   - ⚪ Implementera snyggare övergångar
   - ⚪ Förbättra formulärdesign
   - ⚪ Lägga till fler Material 3-komponenter
   - ⚪ Förbättra responsiviteten
   - ⚪ Implementera bättre felmeddelanden
2. ⚪ Lägga till animationer:
   - ⚪ Implementera övergångsanimationer
   - ⚪ Lägga till laddningsanimationer
   - ⚪ Implementera interaktiva animationer
   - ⚪ Förbättra feedback-animationer
   - ⚪ Lägga till micro-interaktioner
3. ✓ Implementera dark/light theme
4. ⚪ Optimera prestanda:
   - ⚪ Förbättra databasprestanda
   - ⚪ Optimera bildhantering
   - ⚪ Implementera bättre cachning
   - ⚪ Minska minnesanvändning
   - ⚪ Förbättra appstartstid
5. ⚪ Implementera backup/restore-funktionalitet:
   - ⚪ Implementera automatisk backup
   - ⚪ Lägga till manuell backup
   - ⚪ Implementera återställning
   - ⚪ Hantera backup-historik
   - ⚪ Säkerställa dataintegritet
6. ⚪ Lägga till statistik och rapporter:
   - ⚪ Implementera grundläggande statistik
   - ⚪ Lägga till diagram och grafer
   - ⚪ Implementera anpassade rapporter
   - ⚪ Lägga till exportfunktionalitet
   - ⚪ Implementera trendanalys
7. ⚪ Implementera widgets:
   - ⚪ Skapa trädgårdswidget
   - ⚪ Implementera väderwidget
   - ⚪ Lägga till planteringswidget
   - ⚪ Skapa påminnelsewidget
   - ⚪ Implementera statistikwidget
8. ⚪ Förbättra felhantering och användarfeedback:
   - ⚪ Implementera bättre felmeddelanden
   - ⚪ Lägga till felrapportering
   - ⚪ Implementera crash reporting
   - ⚪ Förbättra användarfeedback
   - ⚪ Lägga till hjälpfunktioner

### Fas 3: Fröbanken (Prio 1)
1. ✓ Grundläggande fröbankstruktur
2. ✓ Databasintegration för fröbank
3. ✓ UI för fröbank
4. ⚪ Förbättra fröbanken:
   - ⚪ Lägga till kategorisering av frön
   - ⚪ Implementera sökfunktion med filter
   - ⚪ Lägga till sorteringsmöjligheter
   - ⚪ Implementera favoritmarkering
   - ⚪ Lägga till bilder för frön
   - ⚪ Implementera delning av frön
   - ⚪ Lägga till QR-kod för frön
   - ⚪ Implementera import/export av frödata
   - ⚪ Lägga till statistik över fröbanken
   - ⚪ Implementera backup av fröbanken

### Fas 4: Planteringsfunktionalitet (Prio 2)
1. ⚪ Grundläggande planteringsfunktioner:
   - ⚪ Skapa planteringsplan
   - ⚪ Lägga till plantor
   - ⚪ Hantera plantstatus
   - ⚪ Spåra tillväxt
2. ⚪ Avancerade planteringsfunktioner:
   - ⚪ Automatisk planteringsplanering
   - ⚪ Växtföljdsrekommendationer
   - ⚪ Skördeprognos
   - ⚪ Vattningspåminnelser
3. ⚪ Visualisering:
   - ⚪ Trädgårdsöversikt
   - ⚪ Plantkarta
   - ⚪ Tillväxtdiagram
   - ⚪ Skördeprognosdiagram

### Fas 5: Tidslinje och Påminnelser (Prio 3)
1. ⚪ Grundläggande tidslinje:
   - ⚪ Visa planteringsdatum
   - ⚪ Visa förväntad skörd
   - ⚪ Hantera faktiska datum
2. ⚪ Påminnelser:
   - ⚪ Vattningspåminnelser
   - ⚪ Skördepåminnelser
   - ⚪ Skötselpåminnelser
   - ⚪ Anpassade påminnelser
3. ⚪ Kalenderintegration:
   - ⚪ Synkronisera med systemkalender
   - ⚪ Visa planteringskalender
   - ⚪ Hantera upprepande händelser

### Fas 6: Väderintegration (Prio 4)
1. ⚪ Grundläggande väderdata:
   - ⚪ Visa aktuellt väder
   - ⚪ Väderprognos
   - ⚪ Temperaturdata
2. ⚪ Väderbaserade rekommendationer:
   - ⚪ Planteringsrekommendationer
   - ⚪ Vattningsrekommendationer
   - ⚪ Skyddsrekommendationer
3. ⚪ Väderhistorik:
   - ⚪ Spåra väderdata
   - ⚪ Analysera väderpåverkan
   - ⚪ Jämföra med tidigare år

### Fas 7: Bildhantering (Prio 5)
1. ⚪ Grundläggande bildhantering:
   - ⚪ Ta bilder av frön
   - ⚪ Ta bilder av plantor
   - ⚪ Visa bilder i appen
2. ⚪ Avancerad bildhantering:
   - ⚪ Bildredigering
   - ⚪ Bildkategorisering
   - ⚪ Bildkommentarer
3. ⚪ Bildbackup:
   - ⚪ Molnlagring
   - ⚪ Automatisk backup
   - ⚪ Bildåterställning

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
   - ✓ SeedsScreen
   - ✓ SeedDetailsScreen
   - ✓ AddSeedScreen
   - ✓ Seed CRUD-operationer
   - ⚪ Sökfunktionalitet (grundläggande UI implementerad)
   - ✓ Optimerad databashantering med Room och Coroutines
   - ✓ Effektiv cachning och dataflödeshantering

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