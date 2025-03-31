# Common-modulen

## Översikt
Common-modulen är grundläggande för hela projektet och innehåller delade komponenter och verktyg som används av andra moduler.

## Struktur
```
common/
├── util/          # Hjälpverktyg och utilities
├── model/         # Delade datamodeller
├── constants/     # Konstanter och konfiguration
├── ui/           # Grundläggande UI-komponenter
├── docs/         # Dokumentation
└── config/       # Konfigurationsfiler
```

## Modulens ansvar
- Tillhandahålla grundläggande UI-komponenter för hela appen
- Hantera nätverksanrop och internetanslutning
- Hantera lokalisering och strängresurser
- Tillhandahålla gemensamma verktyg och utilities

## Roadmap för common-modulen
1. [x] Sätta upp grundläggande struktur
2. [x] Implementera NetworkUtils
3. [ ] Implementera grundläggande UI-komponenter
   - Nästa: Byt till auth-modulen efter att UI-komponenter är klara
4. [ ] Sätta upp strängresurser för lokalisering
5. [ ] Implementera tillgänglighetsfunktioner

## Nästa steg
Implementera grundläggande UI-komponenter som kan återanvändas i hela appen. Efter att detta är klart, byt till auth-modulen för inloggningsimplementation.

## Changelog
### 2024-03-31
- Skapad modulstruktur
- Implementerat NetworkUtils för internetanslutningshantering

## Beroenden
- AndroidX Core
- Material3
- Hilt för dependency injection

## Noteringar
- Denna modul ska inte ha några beroenden på andra projektmoduler
- Alla komponenter ska vara återanvändbara och väl dokumenterade
- När nya komponenter läggs till, uppdatera denna dokumentation
- Modulbyten sker efter att grundläggande UI-komponenter är klara 