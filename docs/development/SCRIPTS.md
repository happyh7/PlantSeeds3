# Skript i PlantSeeds3

## Översikt
Detta dokument beskriver de olika skripten som finns i projektet och hur de används.

## Skriptstruktur
```
Scripts/
├── data/              # Skript för datahantering
├── build/             # Byggskript
├── test/              # Testskript
└── deployment/        # Deploymentskript
```

## Datahantering

### 1. Plant Data Enrichment
```bash
# Kör skriptet för att berika plantdata
python Scripts/data/enrich_plant_data.py

# Parametrar:
--input: Indatafil (JSON)
--output: Utdatafil (JSON)
--api-key: API-nyckel för plantdatatjänst
```

### 2. Database Migration
```bash
# Kör databasmigration
./Scripts/data/migrate_database.sh

# Parametrar:
--version: Målversion
--backup: Skapa backup (true/false)
```

## Byggprocess

### 1. Build Script
```bash
# Bygg projektet
./Scripts/build/build.sh

# Parametrar:
--clean: Rensa byggmapp (true/false)
--release: Bygg release-version (true/false)
```

### 2. Version Management
```bash
# Hantera versionsnummer
./Scripts/build/version.sh

# Kommandon:
update: Uppdatera versionsnummer
tag: Skapa git-tag
```

## Testning

### 1. Test Runner
```bash
# Kör alla tester
./Scripts/test/run_tests.sh

# Parametrar:
--unit: Kör enhetstester
--ui: Kör UI-tester
--coverage: Generera täckningsrapport
```

### 2. Test Data Generator
```bash
# Generera testdata
python Scripts/test/generate_test_data.py

# Parametrar:
--count: Antal testposter
--output: Utdatafil
```

## Deployment

### 1. Release Script
```bash
# Skapa release
./Scripts/deployment/create_release.sh

# Parametrar:
--version: Versionsnummer
--notes: Release-anteckningar
```

### 2. Deploy Script
```bash
# Deploya till Firebase
./Scripts/deployment/deploy_to_firebase.sh

# Parametrar:
--environment: Miljö (dev/prod)
--force: Tvinga deployment
```

## Användning

### 1. Installation
```bash
# Installera alla skript
./Scripts/install.sh

# Detta installerar:
- Python-beroenden
- Shell-skript
- Konfigurationsfiler
```

### 2. Konfiguration
```bash
# Konfigurera skript
./Scripts/configure.sh

# Detta konfigurerar:
- API-nycklar
- Miljövariabler
- Sökvägar
```

## Underhåll

### 1. Backup
```bash
# Skapa backup av skript
./Scripts/backup.sh

# Detta säkerhetskopierar:
- Skriptfiler
- Konfigurationsfiler
- Datafiler
```

### 2. Cleanup
```bash
# Städa upp temporära filer
./Scripts/cleanup.sh

# Detta tar bort:
- Loggfiler
- Cache-filer
- Temporära filer
```

## Felsökning

### 1. Log Viewer
```bash
# Visa loggar
./Scripts/debug/view_logs.sh

# Parametrar:
--type: Loggtyp
--lines: Antal rader
```

### 2. Health Check
```bash
# Kontrollera systemets hälsa
./Scripts/debug/health_check.sh

# Detta kontrollerar:
- Databasanslutning
- API-tillgänglighet
- Filrättigheter
```

## Best Practices

### 1. Skriptutveckling
- Använd tydlig dokumentation
- Implementera felhantering
- Lägg till loggning
- Skriv tester

### 2. Skriptkörning
- Kör i rätt miljö
- Använd rätt parametrar
- Kontrollera resultat
- Säkerhetskopiera vid behov

### 3. Underhåll
- Uppdatera dokumentation
- Testa ändringar
- Versionera skript
- Granska regelbundet 