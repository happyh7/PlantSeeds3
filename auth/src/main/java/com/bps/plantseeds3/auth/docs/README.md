# Auth-modulen

## Översikt
Auth-modulen hanterar all autentisering och auktorisering i appen, inklusive inloggning, registrering och lösenordshantering.

## Struktur
```
auth/
├── ui/           # UI-komponenter för autentisering
├── data/         # Datamodeller och repositories
├── domain/       # Användningsfall och logik
├── di/           # Dependency injection
└── docs/         # Dokumentation
```

## Modulens ansvar
- Hantera användarinloggning och registrering
- Säker lösenordshantering
- Token-hantering och sessioner
- Användarprofilhantering
- Säkerhetsrelaterade funktioner

## Roadmap för auth-modulen
1. [x] Sätta upp grundläggande struktur
2. [ ] Implementera inloggningsskärm
   - Nästa: Byt till seed-modulen efter att inloggning är klar
3. [ ] Implementera registreringsskärm
4. [ ] Implementera lösenordsåterställning
5. [ ] Implementera biometrisk autentisering
6. [ ] Implementera sessionhantering

## Nästa steg
Implementera inloggningsskärmen med Firebase Authentication. Efter att detta är klart, byt till seed-modulen för SeedDetailScreen.

## Changelog
### 2024-03-31
- Skapad modulstruktur
- Förberedd för Firebase Authentication-integration

## Beroenden
- Firebase Authentication
- Hilt för dependency injection
- Common-modulen för UI-komponenter

## Noteringar
- Säkerhet är högsta prioritet i denna modul
- All känslig data ska hanteras säkert
- Implementera proper error handling
- Följ OWASP säkerhetsriktlinjer
- Modulbyten sker efter att inloggningsfunktionalitet är klar 