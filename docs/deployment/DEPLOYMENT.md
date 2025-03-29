# Deployment

## Översikt
Detta dokument beskriver deploymentsprocessen för PlantSeeds3-projektet.

## Miljöer

### 1. Utveckling
```
- URL: https://dev.plantseeds3.com
- Firebase: plantseeds3-dev
- Branch: develop
```

### 2. Staging
```
- URL: https://staging.plantseeds3.com
- Firebase: plantseeds3-staging
- Branch: staging
```

### 3. Produktion
```
- URL: https://plantseeds3.com
- Firebase: plantseeds3-prod
- Branch: main
```

## Byggprocess

### 1. Android App
```bash
# Bygg debug-version
./gradlew assembleDebug

# Bygg release-version
./gradlew assembleRelease

# Bygg och signera release
./gradlew bundleRelease
```

### 2. Firebase
```bash
# Deploya till Firebase
firebase deploy

# Deploya specifika tjänster
firebase deploy --only hosting
firebase deploy --only functions
firebase deploy --only firestore
```

## CI/CD Pipeline

### 1. GitHub Actions
```yaml
name: CI/CD Pipeline

on:
  push:
    branches: [ develop, staging, main ]
  pull_request:
    branches: [ develop, staging, main ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up JDK
        uses: actions/setup-java@v2
        with:
          java-version: '17'
          distribution: 'adopt'
      - name: Build with Gradle
        run: ./gradlew build
      - name: Run Tests
        run: ./gradlew test
      - name: Deploy to Firebase
        if: github.ref == 'refs/heads/main'
        run: firebase deploy
```

### 2. Automatisk Deployment
```yaml
name: Auto Deploy

on:
  push:
    branches: [ main ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Deploy to Production
        run: |
          ./gradlew assembleRelease
          firebase deploy --only hosting
```

## Versionshantering

### 1. App-versionsnummer
```kotlin
// app/build.gradle.kts
android {
    defaultConfig {
        versionCode 1
        versionName "1.0.0"
    }
}
```

### 2. API-versionsnummer
```yaml
# api/package.json
{
  "version": "1.0.0"
}
```

## Säkerhet

### 1. API-nycklar
```bash
# Sätt miljövariabler
export FIREBASE_API_KEY=your_api_key
export FIREBASE_AUTH_DOMAIN=your_auth_domain
export FIREBASE_PROJECT_ID=your_project_id
```

### 2. Certifikat
```bash
# Generera nyckel
keytool -genkey -v -keystore plantseeds3.keystore -alias plantseeds3 -keyalg RSA -keysize 2048 -validity 10000
```

## Monitoring

### 1. Firebase Analytics
```kotlin
// app/src/main/java/com/bps/plantseeds3/analytics/Analytics.kt
class Analytics {
    fun logEvent(name: String, params: Bundle) {
        FirebaseAnalytics.getInstance(context).logEvent(name, params)
    }
}
```

### 2. Crashlytics
```kotlin
// app/src/main/java/com/bps/plantseeds3/crashlytics/Crashlytics.kt
class Crashlytics {
    fun logException(exception: Exception) {
        FirebaseCrashlytics.getInstance().recordException(exception)
    }
}
```

## Backup

### 1. Databas
```bash
# Backup Firestore
firebase firestore:export gs://plantseeds3-backup/firestore

# Backup Storage
gsutil -m cp -r gs://plantseeds3.appspot.com gs://plantseeds3-backup/storage
```

### 2. Konfiguration
```bash
# Backup Firebase config
firebase functions:config:get > firebase-config.json

# Backup app config
cp app/google-services.json app/google-services.json.backup
```

## Rollback

### 1. App
```bash
# Återställ till tidigare version
git checkout <previous-tag>
./gradlew assembleRelease
firebase deploy --only hosting
```

### 2. API
```bash
# Återställ API-version
git checkout <previous-tag>
firebase deploy --only functions
```

## Checklista

### 1. Pre-deployment
- [ ] Kör alla tester
- [ ] Uppdatera versionsnummer
- [ ] Uppdatera CHANGELOG.md
- [ ] Granska ändringar
- [ ] Backup databas
- [ ] Backup konfiguration

### 2. Deployment
- [ ] Bygg release-version
- [ ] Signera APK/Bundle
- [ ] Deploya till Firebase
- [ ] Verifiera deployment
- [ ] Testa i produktion

### 3. Post-deployment
- [ ] Verifiera analytics
- [ ] Kontrollera crashlytics
- [ ] Uppdatera dokumentation
- [ ] Skapa git-tag
- [ ] Meddela teamet

## Support

### 1. Incident Response
```bash
# Starta incident response
./Scripts/deployment/incident-response.sh

# Detta:
- Skapar incident-kanal
- Meddelar teamet
- Startar monitoring
```

### 2. Troubleshooting
```bash
# Kör health check
./Scripts/deployment/health-check.sh

# Detta kontrollerar:
- API-tillgänglighet
- Databasanslutning
- Firebase-status
```

## Dokumentation

### 1. Deployment Log
```markdown
# Deployment Log

## 2024-03-29
- Version: 1.0.0
- Ändringar:
  - Ny feature X
  - Bugfix Y
- Status: Lyckad
- Verifierad av: [Team Member]
```

### 2. Incident Log
```markdown
# Incident Log

## 2024-03-29
- Incident: API-nedtid
- Orsak: Firebase-problem
- Åtgärder:
  1. Återställde backup
  2. Uppdaterade konfiguration
- Status: Åtgärdad
``` 