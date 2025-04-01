# PlantSeeds3 Roadmap

## Översikt
Detta dokument beskriver utvecklingsplanen för PlantSeeds3-appen, med fokus på modulär utveckling och prioriterade uppgifter.

## Prioriterade Uppgifter

### Fas 1: Grundläggande Infrastruktur
1. [x] Sätta upp projektstruktur
2. [x] Implementera modulär arkitektur
3. [x] Skapa dokumentation för varje modul
4. [x] Implementera grundläggande UI-komponenter i common-modulen
   - Nästa: Byt till auth-modulen efter att UI-komponenter är klara
5. [ ] Sätta upp CI/CD-pipeline

### Fas 2: Autentisering och Användarhantering (Auth-modulen)
1. [ ] Implementera inloggningsskärm
   - Nästa: Byt till seed-modulen efter att inloggning är klar
2. [ ] Implementera registreringsskärm
3. [ ] Implementera lösenordsåterställning
4. [ ] Implementera användarprofilhantering
5. [ ] Implementera biometrisk autentisering

### Fas 3: Fröhantering (Seed-modulen)
1. [x] Implementera SeedListScreen
2. [ ] Implementera SeedDetailScreen
   - Nästa: Byt till garden-modulen efter att SeedDetailScreen är klar
3. [ ] Implementera fröhanteringsoperationer
4. [ ] Implementera fröstatistik
5. [ ] Implementera frönotifieringar

### Fas 4: Trädgårdshantering (Garden-modulen)
1. [ ] Implementera GardenOverviewScreen
   - Nästa: Byt tillbaka till common-modulen för optimering
2. [ ] Implementera GardenPlanningScreen
3. [ ] Implementera PlantCareScreen
4. [ ] Implementera växtövervakning
5. [ ] Implementera växtnotifieringar

## Modulöversikt

### Common-modulen
- [x] Grundläggande UI-komponenter
- [x] Nätverkshantering
- [x] Lokalisering
- [x] Verktyg och utilities

### Auth-modulen
- [ ] Inloggning och registrering
- [ ] Lösenordshantering
- [ ] Token-hantering
- [ ] Användarprofilhantering

### Seed-modulen
- [x] Frölistor och detaljer
- [ ] Fröhanteringsoperationer
- [ ] Fröstatistik
- [ ] Frönotifieringar

### Garden-modulen
- [ ] Trädgårdsplanering
- [ ] Odlingszoner
- [ ] Växtövervakning
- [ ] Växtnotifieringar

## Tekniska Mål
- [x] Implementera Clean Architecture
- [x] Följa Material Design 3-riktlinjer
- [ ] Säkerställa offline-first funktionalitet
- [x] Implementera proper error handling
- [ ] Säkerställa hög prestanda
- [x] Implementera omfattande testning

## Nästa Steg
1. [x] Slutföra grundläggande UI-komponenter i common-modulen
2. Byt till auth-modulen för inloggningsimplementation
3. Efter inloggning, fortsätt med SeedDetailScreen i seed-modulen
4. Byt till garden-modulen för GardenOverviewScreen
5. Återvänd till common-modulen för optimering

## Noteringar
- Varje modul ska ha egen dokumentation och roadmap
- Uppdatera denna roadmap regelbundet
- Prioritera användarupplevelse och prestanda
- Följ säkerhetsriktlinjer för all känslig data
- Modulbyten sker efter att en kritisk funktionalitet är klar 