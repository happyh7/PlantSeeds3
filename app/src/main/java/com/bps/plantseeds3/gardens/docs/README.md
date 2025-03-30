# Trädgård-modulen

## Översikt
Trädgård-modulen hanterar all funktionalitet relaterad till trädgårdar i applikationen. Detta inkluderar:
- Hantering av trädgårdsdata
- Visning av trädgårdar
- Sökning och filtrering av trädgårdar
- Hantering av trädgårdszoner och klimat
- Koppling mellan trädgårdar och växter

## Struktur
```
gardens/
├── data/        # Data-lagret
├── domain/      # Domänmodeller och affärslogik
├── presentation/# UI-komponenter
└── docs/        # Dokumentation
```

## Beroenden
- common-modulen för gemensam funktionalitet
- Room-databas för persistent lagring
- plants-modulen för växthantering

## API
### Data-lagret
- `GardenRepository`: Hanterar CRUD-operationer för trädgårdar
- `GardenDao`: Databasåtkomst för trädgårdar

### Domänlagret
- `Garden`: Domänmodell för trädgårdar
- `GardenZone`: Enum för trädgårdszoner
- `GardenUseCases`: Affärslogik för trädgårdshantering

### Presentationslagret
- `GardenScreen`: Huvudskärm för trädgårdar
- `GardenViewModel`: State-hantering för trädgårdar
- `GardenItem`: Komponent för att visa enskilda trädgårdar

## Användning
För att använda trädgård-modulen:
1. Inkludera modulen i ditt projekt
2. Injicera nödvändiga beroenden
3. Använd `GardenScreen` för att visa trädgårdar

## Dokumentation
- [Arkitektur](architecture.md)
- [API-dokumentation](api.md)
- [Utvecklingsriktlinjer](development.md) 