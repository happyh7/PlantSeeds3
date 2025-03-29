# Bidra till PlantSeeds3

## Kom igång

### Förutsättningar
- Android Studio Hedgehog eller senare
- JDK 17 eller senare
- Git
- GitHub-konto

### Installation
1. Forka projektet på GitHub
2. Klona din fork lokalt:
   ```bash
   git clone https://github.com/ditt-användarnamn/PlantSeeds3.git
   ```
3. Öppna projektet i Android Studio
4. Låt Gradle synkronisera projektet

## Utvecklingsprocess

### 1. Skapa en ny branch
```bash
git checkout develop
git pull origin develop
git checkout -b feature/din-feature
```

### 2. Utveckla din feature
- Följ kodningsstandarder i `docs/development/CODING_STANDARDS.md`
- Skriv tester för ny funktionalitet
- Uppdatera dokumentation vid behov

### 3. Commita dina ändringar
```bash
git add .
git commit -m "feat: beskriv dina ändringar"
```

### 4. Pusha till GitHub
```bash
git push origin feature/din-feature
```

### 5. Skapa en Pull Request
- Gå till GitHub och skapa en PR mot `develop`
- Fyll i PR-beskrivningen enligt mall
- Lägg till relevanta reviewers

## Kodningsstandarder

### Kotlin
- Följ officiella Kotlin-kodningskonventioner
- Använd ktlint för formatering
- Skriv beskrivande variabel- och funktionsnamn
- Lägg till KDoc-kommentarer för publika API:er

### Arkitektur
- Följ Clean Architecture-principer
- Separera UI, affärslogik och data
- Använd dependency injection
- Implementera repository pattern

### Testning
- Skriv enhetstester för affärslogik
- Skriv UI-tester för komponenter
- Upprätthåll minst 80% kodtäckning
- Använd MockK för mocking

## Dokumentation

### KDoc
```kotlin
/**
 * Beskriver funktionens syfte
 *
 * @param param1 beskrivning av första parametern
 * @param param2 beskrivning av andra parametern
 * @return beskrivning av returvärdet
 * @throws ExceptionType beskrivning av undantag
 */
```

### Commit-meddelanden
```
<type>(<scope>): <description>

[optional body]

[optional footer]
```

Typer:
- feat: Ny funktionalitet
- fix: Bugfix
- docs: Dokumentationsändringar
- style: Formateringsändringar
- refactor: Kodrefaktorering
- test: Testning
- chore: Underhåll

## Pull Request-mall

```markdown
## Beskrivning
Kort beskrivning av ändringarna

## Ändringar
- [x] Ändring 1
- [x] Ändring 2
- [x] Ändring 3

## Testning
- [x] Enhetstester
- [x] UI-tester
- [x] Integrationstester

## Checklista
- [x] Följer kodningsstandarder
- [x] Inkluderar dokumentation
- [x] Har testning
- [x] Uppdaterar versionsnummer
```

## Support
Om du stöter på problem eller har frågor:
1. Kolla dokumentationen
2. Sök i issues
3. Skapa ett nytt issue om det behövs
4. Kontakta utvecklingsteamet

## Licens
Genom att bidra till projektet accepterar du att dina bidrag licensieras under samma villkor som projektet. 