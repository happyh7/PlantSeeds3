# Garden-modulen

## Översikt
Garden-modulen hanterar all funktionalitet relaterad till trädgårdshantering, inklusive trädgårdsplanering, odlingszoner och växtövervakning.

## Struktur
```
garden/
├── ui/           # UI-komponenter för trädgårdshantering
├── data/         # Datamodeller och repositories
├── domain/       # Användningsfall och logik
├── di/           # Dependency injection
└── docs/         # Dokumentation
```

## Modulens ansvar
- Hantera trädgårdsplanering och layout
- Hantera odlingszoner och växtplatser
- Hantera växtövervakning och vård
- Hantera växtrelaterade notifieringar
- Hantera växtrelaterad statistik och rapporter

## Roadmap för garden-modulen
1. [x] Sätta upp grundläggande struktur
2. [x] Implementera GardenOverviewScreen
   - Nästa: Byt tillbaka till common-modulen för optimering
3. [ ] Implementera GardenPlanningScreen
4. [ ] Implementera PlantCareScreen
5. [ ] Implementera växtövervakning
6. [ ] Implementera växtnotifieringar

## Nästa steg
Byt tillbaka till common-modulen för optimering. Efter att optimeringen är klar, återvänd för att implementera GardenPlanningScreen.

## Changelog
### 2024-04-03
- Implementerat GardenOverviewScreen med grundläggande funktionalitet
- Lagt till GardenList-komponent
- Lagt till AddGardenDialog och EditGardenDialog
- Implementerat CRUD-operationer för trädgårdar

### 2024-03-31
- Skapad modulstruktur
- Förberedd för implementering av trädgårdsfunktionalitet

## Beroenden
- Room för lokaldatabas
- Hilt för dependency injection
- Common-modulen för UI-komponenter

## Noteringar
- Implementera intuitiv planeringsgränssnitt
- Hantera komplexa trädgårdslayout
- Implementera växtövervakningssystem
- Följ Material Design 3-riktlinjer för UI
- Modulbyten sker efter att GardenOverviewScreen är klar 