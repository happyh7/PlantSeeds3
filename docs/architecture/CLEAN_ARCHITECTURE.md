# Clean Architecture

## Översikt
PlantSeeds3 följer Clean Architecture-principerna för att säkerställa en modulär, testbar och underhållbar kodbas. Arkitekturen är uppdelad i tre huvudlager: Data, Domain och Presentation.

## Lag

### 1. Data Layer
```
app/src/main/java/com/plantseeds3/data/
├── model/          # Datamodeller och DTO:er
├── repository/     # Repository-implementationer
├── source/        # Datakällor (Room, Firebase)
└── mapper/        # Mappning mellan lager
```

#### Datamodeller
- `SeedEntity`: Room-entitet för frön
- `PlantEntity`: Room-entitet för plantor
- `GardenEntity`: Room-entitet för trädgårdar
- `SeedDto`: Firebase DTO för frön
- `PlantDto`: Firebase DTO för plantor
- `GardenDto`: Firebase DTO för trädgårdar

#### Repositories
- `SeedRepository`: Hanterar frödata
- `PlantRepository`: Hanterar plantdata
- `GardenRepository`: Hanterar trädgårdsdata
- `SyncRepository`: Hanterar synkronisering

### 2. Domain Layer
```
app/src/main/java/com/plantseeds3/domain/
├── model/         # Domain-modeller
├── repository/    # Repository-interfaces
├── usecase/       # Use Cases
└── exception/     # Custom exceptions
```

#### Domain Models
- `Seed`: Frömodell
- `Plant`: Plantmodell
- `Garden`: Trädgårdsmodell
- `PlantStatus`: Enum för plantstatus

#### Use Cases
- `GetSeedsUseCase`: Hämta frön
- `AddSeedUseCase`: Lägga till frö
- `UpdateSeedUseCase`: Uppdatera frö
- `DeleteSeedUseCase`: Ta bort frö
- `GetPlantsUseCase`: Hämta plantor
- `AddPlantUseCase`: Lägga till planta
- `UpdatePlantUseCase`: Uppdatera planta
- `DeletePlantUseCase`: Ta bort planta

### 3. Presentation Layer
```
app/src/main/java/com/plantseeds3/presentation/
├── screens/       # UI-skärmar
├── components/    # Återanvändbara komponenter
├── viewmodel/     # ViewModels
└── theme/         # UI-tema
```

#### Screens
- `SeedsScreen`: Frölista
- `AddEditSeedScreen`: Lägg till/redigera frö
- `PlantsScreen`: Plantlista
- `AddEditPlantScreen`: Lägg till/redigera planta
- `GardenScreen`: Trädgårdsöversikt

#### ViewModels
- `SeedsViewModel`: Hanterar frödata
- `AddEditSeedViewModel`: Hanterar fröredigering
- `PlantsViewModel`: Hanterar plantdata
- `AddEditPlantViewModel`: Hanterar plantredigering
- `GardenViewModel`: Hanterar trädgårdsdata

## Dataflöde

1. **UI -> ViewModel**
   - Användare interagerar med UI
   - UI anropar ViewModel-funktioner
   - ViewModel validerar input

2. **ViewModel -> Use Case**
   - ViewModel anropar Use Case
   - Use Case utför affärslogik
   - Use Case returnerar resultat

3. **Use Case -> Repository**
   - Use Case anropar Repository
   - Repository hanterar data
   - Repository returnerar data

4. **Repository -> Data Source**
   - Repository anropar Data Source
   - Data Source hämtar/lagrar data
   - Data Source returnerar data

## Dependency Injection

### Hilt Modules
- `AppModule`: App-nivå dependencies
- `DatabaseModule`: Databasdependencies
- `RepositoryModule`: Repository-dependencies
- `UseCaseModule`: Use Case-dependencies

### Scopes
- `@Singleton`: Globala dependencies
- `@ActivityRetainedScoped`: Activity-specifika dependencies
- `@ViewModelScoped`: ViewModel-specifika dependencies

## Error Handling

### Domain Exceptions
- `SeedNotFoundException`
- `PlantNotFoundException`
- `GardenNotFoundException`
- `InvalidInputException`
- `SyncException`

### Error States
- `Loading`: Laddar data
- `Success`: Data laddad
- `Error`: Fel uppstod
- `Empty`: Ingen data

## Testing Strategy

### Unit Tests
- Testa Use Cases
- Testa Repositories
- Testa ViewModels
- Testa Mappers

### UI Tests
- Testa Screens
- Testa Components
- Testa Navigation
- Testa User Interactions

### Integration Tests
- Testa Data Flow
- Testa Database Operations
- Testa Firebase Sync
- Testa Error Handling 