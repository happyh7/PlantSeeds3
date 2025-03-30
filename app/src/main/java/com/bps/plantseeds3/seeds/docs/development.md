# Utvecklingsguide - Fröbank-modulen

## Utvecklingsmiljö
- Android Studio Arctic Fox eller senare
- Kotlin 1.5.0 eller senare
- Gradle 7.0 eller senare
- JDK 11 eller senare

## Projektstruktur
```
seeds/
├── data/
│   ├── local/
│   │   ├── dao/
│   │   ├── entity/
│   │   └── repository/
│   └── remote/
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
├── presentation/
│   ├── components/
│   ├── screens/
│   └── viewmodel/
└── docs/
    ├── README.md
    ├── architecture.md
    ├── api.md
    └── development.md
```

## Konfiguration
1. Lägg till beroenden i `build.gradle.kts`:
```kotlin
dependencies {
    implementation(project(":common"))
    implementation(project(":plants"))
    
    // Room
    implementation("androidx.room:room-runtime:2.5.0")
    implementation("androidx.room:room-ktx:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    
    // Compose
    implementation("androidx.compose.ui:ui:1.3.0")
    implementation("androidx.compose.material:material:1.3.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.0")
}
```

## Utvecklingsriktlinjer

### Kodstil
- Följ Kotlin Coding Conventions
- Använd ktlint för kodformatering
- Dokumentera alla publika API:er
- Skriv beskrivande commit-meddelanden
- Använd meningsfulla variabelnamn
- Håll funktioner korta och fokuserade

### Arkitektur
- Följ Clean Architecture-principer
- Separera affärslogik från UI
- Använd dependency injection
- Implementera repository-mönstret
- Hantera relationer med plants-modulen
- Följ SOLID-principerna

### Testning
- Skriv unit-tester för alla use cases
- Skriv integrationstester för repository
- Skriv UI-tester för alla skärmar
- Använd test-driven development
- Testa integration med plants-modulen
- Testa fröegenskaper och krav

### Prestanda
- Använd Flow för reaktiva dataflöden
- Implementera effektiv cachning
- Optimera databasfrågor
- Hantera minnesläckor
- Profilera kod regelbundet
- Mät prestanda

## Vanliga uppgifter

### Lägga till ett nytt frö
1. Skapa SeedEntity
2. Implementera SeedDao-metoder
3. Uppdatera SeedRepository
4. Skapa SeedViewModel
5. Implementera UI-komponenter
6. Skriva tester

### Uppdatera fröegenskaper
1. Uppdatera SeedState
2. Implementera SeedEvent
3. Uppdatera ViewModel
4. Uppdatera UI
5. Skriva tester

### Filtrera frön
1. Uppdatera SeedState
2. Implementera filterlogik
3. Uppdatera UI
4. Skriva tester

## Felsökning
- Använd Logcat för debugging
- Implementera proper error handling
- Använd breakpoints
- Skriv loggmeddelanden
- Använd debuggingverktyg
- Dokumentera kända problem

## Prestandaoptimering
- Profilera kod
- Optimera minnesanvändning
- Implementera cachning
- Minimera allokeringar
- Använd effektiva algoritmer
- Mät prestanda

## Säkerhet
- Validera input
- Hantera fel säkert
- Skydda känslig data
- Följ säkerhetsriktlinjer
- Uppdatera beroenden
- Genomför säkerhetsgranskningar

## Underhåll
- Uppdatera beroenden
- Dokumentera ändringar
- Skriv changelog
- Uppdatera versioner
- Genomför kodgranskningar
- Uppdatera dokumentation 