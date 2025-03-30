# Fröbank-modulen

## Översikt
Fröbank-modulen hanterar all funktionalitet relaterad till frön i applikationen. Detta inkluderar:
- Hantering av frödata
- Visning av frön
- Sökning och filtrering av frön
- Hantering av fröegenskaper och krav
- Koppling mellan frön och växter

## Struktur
```
seeds/
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
- `SeedRepository`: Hanterar CRUD-operationer för frön
- `SeedDao`: Databasåtkomst för frön

### Domänlagret
- `Seed`: Domänmodell för frön
- `SeedCategory`: Enum för frökategorier
- `SeedUseCases`: Affärslogik för fröhantering

### Presentationslagret
- `SeedScreen`: Huvudskärm för frön
- `SeedViewModel`: State-hantering för frön
- `SeedItem`: Komponent för att visa enskilda frön

## Användning
För att använda fröbank-modulen:
1. Inkludera modulen i ditt projekt
2. Injicera nödvändiga beroenden
3. Använd `SeedScreen` för att visa frön

## Dokumentation
- [Arkitektur](architecture.md)
- [API-dokumentation](api.md)
- [Utvecklingsriktlinjer](development.md) 