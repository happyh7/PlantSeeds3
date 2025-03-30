# Växt-modulen

## Översikt
Växt-modulen hanterar all funktionalitet relaterad till växter i applikationen. Detta inkluderar:
- Hantering av växtdata
- Visning av växter
- Sökning och filtrering av växter
- Hantering av växtstatus och utveckling
- Koppling mellan växter och trädgårdar

## Struktur
```
plants/
├── data/        # Data-lagret
├── domain/      # Domänmodeller och affärslogik
├── presentation/# UI-komponenter
└── docs/        # Dokumentation
```

## Beroenden
- common-modulen för gemensam funktionalitet
- Room-databas för persistent lagring
- seeds-modulen för fröhantering
- gardens-modulen för trädgårdshantering

## API
### Data-lagret
- `PlantRepository`: Hanterar CRUD-operationer för växter
- `PlantDao`: Databasåtkomst för växter

### Domänlagret
- `Plant`: Domänmodell för växter
- `PlantStatus`: Enum för växtens utvecklingsstadium
- `PlantUseCases`: Affärslogik för växthantering

### Presentationslagret
- `PlantScreen`: Huvudskärm för växter
- `PlantViewModel`: State-hantering för växter
- `PlantItem`: Komponent för att visa enskilda växter

## Användning
För att använda växt-modulen:
1. Inkludera modulen i ditt projekt
2. Injicera nödvändiga beroenden
3. Använd `PlantScreen` för att visa växter

## Dokumentation
- [Arkitektur](architecture.md)
- [API-dokumentation](api.md)
- [Utvecklingsriktlinjer](development.md) 