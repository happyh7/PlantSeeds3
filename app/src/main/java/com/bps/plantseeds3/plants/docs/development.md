# Utvecklingsguide - Växt-modulen

## Utvecklingsmiljö
- Android Studio Arctic Fox eller senare
- Kotlin 1.5.0 eller senare
- Gradle 7.0 eller senare
- JDK 11 eller senare

## Projektstruktur
```
plants/
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
    implementation(project(":seeds"))
    implementation(project(":gardens"))
    
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

### Arkitektur
- Följ Clean Architecture-principer
- Separera affärslogik från UI
- Använd dependency injection
- Implementera repository-mönstret

### Testning
- Skriv unit-tester för alla use cases
- Skriv integrationstester för repository
- Skriv UI-tester för alla skärmar
- Använd test-driven development när möjligt

### Prestanda
- Använd Flow för reaktiva dataflöden
- Implementera effektiv cachning
- Optimera databasfrågor
- Hantera minnesläckor

## Vanliga uppgifter

### Lägga till en ny växt
1. Skapa PlantEntity
2. Implementera PlantDao-metoder
3. Uppdatera PlantRepository
4. Skapa PlantViewModel
5. Implementera UI-komponenter
6. Skriva tester

### Uppdatera växtstatus
1. Uppdatera PlantState
2. Implementera PlantEvent
3. Uppdatera ViewModel
4. Uppdatera UI
5. Skriva tester

### Filtrera växter
1. Uppdatera PlantState
2. Implementera filterlogik
3. Uppdatera UI
4. Skriva tester

## Felsökning
- Använd Logcat för debugging
- Implementera proper error handling
- Använd breakpoints för debugging
- Skriv loggmeddelanden för viktiga händelser

## Prestandaoptimering
- Använd lazy loading
- Implementera pagination
- Cacha data när möjligt
- Optimera bildhantering

## Säkerhet
- Validera all användarinput
- Hantera känslig data säkert
- Implementera proper error handling
- Följ Android Security Best Practices

## Underhåll
- Uppdatera beroenden regelbundet
- Dokumentera ändringar
- Skriv changelog
- Uppdatera versioner 