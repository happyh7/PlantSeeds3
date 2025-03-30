# Bidra till Common Module

Tack för ditt intresse att bidra till PlantSeeds3's common-modul! Detta dokument beskriver hur du kan bidra på bästa sätt.

## För att komma igång

1. Forka projektet
2. Skapa en feature branch (`git checkout -b feature/AmazingFeature`)
3. Commita dina ändringar (`git commit -m 'Lägger till någon fantastisk feature'`)
4. Pusha till branchen (`git push origin feature/AmazingFeature`)
5. Öppna en Pull Request

## Utvecklingsmiljö

### Krav
- Android Studio Hedgehog eller senare
- Kotlin 1.9.22
- Gradle 8.2
- Android SDK 34
- JDK 17

### Installation
1. Klona projektet
2. Öppna i Android Studio
3. Låt Gradle synka
4. Kör `./gradlew build`

## Kodstandard

### Kotlin
- Följ [Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Använd `ktlint` för kodformatering
- Maximal linjelängd är 120 tecken
- Använd 4 mellanslag för indentering

### Dokumentation
- Dokumentera alla publika API:er
- Använd KDoc-format för dokumentation
- Inkludera exempel där det är relevant
- Uppdatera README.md vid större ändringar

### Commits
- Använd beskrivande commit-meddelanden
- Följ [Conventional Commits](https://www.conventionalcommits.org/)
- Commita ofta och i logiska delar
- Inkludera issue-referenser när relevant

## Pull Requests

### Process
1. Skapa en feature branch från `main`
2. Implementera dina ändringar
3. Uppdatera dokumentationen
4. Lägg till/uppdatera tester
5. Skapa en Pull Request

### Checklista
- [ ] Koden följer kodstandarden
- [ ] Alla tester passerar
- [ ] Dokumentationen är uppdaterad
- [ ] CHANGELOG.md är uppdaterad
- [ ] Inga lint-varningar
- [ ] Inga kompileringsfel

## Tester

### Enhetstester
- Skriv tester för all ny funktionalitet
- Använd JUnit 4
- Följ AAA-mönstret (Arrange-Act-Assert)
- Använd beskrivande testnamn

### UI-tester
- Skriv UI-tester för komponenter
- Använd Compose Testing Library
- Testa olika tillstånd och scenarion
- Inkludera accessibility-tester

## Felrapportering

### Bug Reports
- Använd issue-mall för bug reports
- Inkludera steg för att reproducera
- Lägg till loggar och stack traces
- Beskriv förväntat beteende

### Feature Requests
- Använd issue-mall för feature requests
- Beskriv användningsfallet
- Inkludera exempel
- Motivera varför det behövs

## Kommunikation

### Issue Tracker
- Använd lämpliga labels
- Tilldela issues till dig själv
- Uppdatera status regelbundet
- Stäng issues när de är lösta

### Code Review
- Var konstruktiv i feedback
- Fokusera på kodkvalitet
- Respektera olika stilar
- Lär av varandra

## Licens

Genom att bidra till projektet accepterar du att dina bidrag licensieras under samma villkor som projektet.

## Kontakt

- Projektledare: [Namn]
- Email: [Email]
- Issue Tracker: [Länk]
- Chat: [Länk] 