# Domain Module Roadmap

## Fas 1: Domain-modeller
- [ ] Implementera Seed-modeller
  - [ ] Seed
  - [ ] SeedCategory
  - [ ] SeedStatus
  - [ ] SeedProperties
- [ ] Implementera Plant-modeller
  - [ ] Plant
  - [ ] PlantStatus
  - [ ] PlantProperties
  - [ ] PlantCare
- [ ] Implementera Garden-modeller
  - [ ] Garden
  - [ ] GardenZone
  - [ ] GardenProperties
  - [ ] GardenLayout

## Fas 2: Repository-interfaces
- [ ] Definiera SeedRepository
  - [ ] CRUD-operationer
  - [ ] Sökoperationer
  - [ ] Filtreringsoperationer
- [ ] Definiera PlantRepository
  - [ ] CRUD-operationer
  - [ ] Sökoperationer
  - [ ] Filtreringsoperationer
- [ ] Definiera GardenRepository
  - [ ] CRUD-operationer
  - [ ] Sökoperationer
  - [ ] Filtreringsoperationer

## Fas 3: Use Cases
### Seed Use Cases
- [ ] GetSeedsUseCase
- [ ] GetSeedUseCase
- [ ] AddSeedUseCase
- [ ] UpdateSeedUseCase
- [ ] DeleteSeedUseCase
- [ ] SearchSeedsUseCase
- [ ] FilterSeedsUseCase

### Plant Use Cases
- [ ] GetPlantsUseCase
- [ ] GetPlantUseCase
- [ ] AddPlantUseCase
- [ ] UpdatePlantUseCase
- [ ] DeletePlantUseCase
- [ ] SearchPlantsUseCase
- [ ] FilterPlantsUseCase

### Garden Use Cases
- [ ] GetGardensUseCase
- [ ] GetGardenUseCase
- [ ] AddGardenUseCase
- [ ] UpdateGardenUseCase
- [ ] DeleteGardenUseCase
- [ ] SearchGardensUseCase
- [ ] FilterGardensUseCase

## Fas 4: Validering och Felhantering
- [ ] Implementera valideringsregler
  - [ ] Seed-validering
  - [ ] Plant-validering
  - [ ] Garden-validering
- [ ] Definiera feltyper
  - [ ] Valideringsfel
  - [ ] Affärslogikfel
  - [ ] Systemfel
- [ ] Implementera felhantering
  - [ ] Error handling
  - [ ] Error reporting
  - [ ] Error recovery

## Fas 5: Testning och Dokumentation
- [ ] Skapa use case-tester
  - [ ] Unit tests
  - [ ] Integration tests
  - [ ] Edge cases
- [ ] Dokumentera affärsregler
  - [ ] Valideringsregler
  - [ ] Affärslogik
  - [ ] Felhantering
- [ ] Skapa API-dokumentation
  - [ ] Interface-dokumentation
  - [ ] Use case-dokumentation
  - [ ] Exempel 