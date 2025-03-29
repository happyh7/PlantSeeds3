# PlantSeeds3 - Projektkontext

## Syfte
PlantSeeds3 är en Android-app för att hantera fröer och växtinformation. Appen ska vara enkel att använda samtidigt som den erbjuder omfattande funktionalitet för mer avancerade användare.

## Nuvarande Status
- Grundläggande CRUD-funktionalitet för trädgårdar och växter är implementerad
- Trädgårdsväljare och sparning av vald trädgård mellan sessioner är implementerad
- Material Design 3 är implementerat med stöd för både ljust och mörkt tema
- Grundläggande felhantering och användarfeedback är implementerad
- Navigering mellan olika skärmar är implementerad
- Databasintegration med Room är implementerad

## Prioriteringar
1. **Enkelhet först**
   - Endast namn är obligatoriskt för att lägga till ett frö
   - Övrig information kan fyllas i senare
   - Tydlig och intuitiv användargränssnitt

2. **Flexibilitet**
   - Användare kan välja att använda förslag från databasen
   - Möjlighet att ta bort eller ändra information efter behov
   - Stöd för både enkla och detaljerade växtbeskrivningar

3. **Databasintegration**
   - Automatiska förslag baserade på vad användaren skriver
   - Möjlighet att fylla i all information från ett valt frö
   - Enkel återställning om fel frö väljs

4. **Kvalitetssäkring**
   - Kör Gradle-byggning (`./gradlew clean build --stacktrace`) efter varje kodändring
   - Åtgärda eventuella kompileringsfel direkt
   - Håll koden felfri och byggbar

## Tekniska val
- Kotlin som programmeringsspråk
- Jetpack Compose för UI
- Room för databas
- Hilt för dependency injection
- Material Design 3 för design
- Clean Architecture för kodstruktur

## Appbeskrivning
PlantSeeds3 är en trädgårdsassistent-app som hjälper användare att hantera sina odlingar från frö till skörd. Appen är designad för både nybörjare och erfarna odlare, med fokus på användarvänlighet och praktisk funktionalitet.

### Huvudfunktioner
1. **Trädgårdshantering**
   - Skapa och hantera flera trädgårdar
   - Dokumentera trädgårdens egenskaper
   - Visualisera trädgårdsytor och planteringar
   - Spåra trädgårdens utveckling över tid

2. **Fröbibliotek**
   - Katalogisera dina frön
   - Sökbar databas med plantinformation
   - Planteringsguider och skötselråd
   - Spåra frönas ålder och grobarhet

3. **Plantspårning**
   - Följ varje plantas utveckling
   - Statusuppdateringar
   - Fotodokumentation av tillväxt
   - Anteckningar och observationer

4. **Smart Planering**
   - Säsongsbaserad planteringskalender
   - Påminnelser för vattning och skötsel
   - Skördeprognos
   - Väderintegration för optimal plantering

### Målgrupp
- Hobbyodlare
- Balkongodlare
- Kolonilottsägare
- Trädgårdsintresserade
- Självhushållare

### Tekniska Fördelar
- Minimal batterianvändning
- Effektiv datahantering
- Snabb uppstart
- Låg dataförbrukning
- Säker användardata 