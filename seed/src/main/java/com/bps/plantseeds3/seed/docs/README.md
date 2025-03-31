# Seed-modulen

## Översikt
Seed-modulen hanterar all funktionalitet relaterad till fröhantering, inklusive frölistor, fröinformation och frörelaterade operationer.

## Struktur
```
seed/
├── ui/           # UI-komponenter för fröhantering
├── data/         # Datamodeller och repositories
├── domain/       # Användningsfall och logik
├── di/           # Dependency injection
└── docs/         # Dokumentation
```

## Modulens ansvar
- Hantera frölistor och enskilda frön
- Hantera fröinformation och metadata
- Hantera frörelaterade operationer (sådd, skörd, etc.)
- Hantera frörelaterade statistik och rapporter
- Hantera frörelaterade notifieringar

## Roadmap för seed-modulen
1. [x] Sätta upp grundläggande struktur
2. [x] Implementera SeedListScreen
3. [ ] Implementera SeedDetailScreen
   - Nästa: Byt till garden-modulen efter att SeedDetailScreen är klar
4. [ ] Implementera fröhanteringsoperationer
5. [ ] Implementera fröstatistik
6. [ ] Implementera frönotifieringar

## Nästa steg
Implementera SeedDetailScreen för att visa detaljerad information om enskilda frön. Efter att detta är klart, byt till garden-modulen för GardenOverviewScreen.

## Changelog
### 2024-03-31
- Skapad modulstruktur
- Implementerat SeedListScreen med grundläggande funktionalitet

## Beroenden
- Room för lokaldatabas
- Hilt för dependency injection
- Common-modulen för UI-komponenter

## Noteringar
- Implementera offline-first arkitektur
- Hantera stora datamängder effektivt
- Implementera proper caching-strategi
- Följ Material Design 3-riktlinjer för UI
- Modulbyten sker efter att SeedDetailScreen är klar 