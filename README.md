# PlantSeeds3 🌱

PlantSeeds3 är en modern trädgårdsassistent-app som hjälper användare att hantera sina odlingar från frö till skörd. Appen är designad för både nybörjare och erfarna odlare, med fokus på användarvänlighet och praktisk funktionalitet.

## Huvudfunktioner 🌟

### 1. Trädgårdshantering 🏡
- Skapa och hantera flera trädgårdar
- Dokumentera trädgårdens egenskaper (storlek, plats, jordtyp)
- Visualisera trädgårdsytor och planteringar
- Spåra trädgårdens utveckling över tid

### 2. Fröbibliotek 🌰
- Katalogisera dina frön
- Sökbar databas med plantinformation
- Planteringsguider och skötselråd
- Spåra frönas ålder och grobarhet

### 3. Plantspårning 🌿
- Följ varje plantas utveckling
- Statusuppdateringar (planterad, groddar, växande, blomning, skörd)
- Fotodokumentation av tillväxt
- Anteckningar och observationer

### 4. Smart Planering 📅
- Säsongsbaserad planteringskalender
- Påminnelser för vattning och skötsel
- Skördeprognos
- Väderintegration för optimal plantering

## Teknisk Information 🔧

### Teknisk Stack
- **Frontend**: Kotlin + Jetpack Compose
- **Backend**: Firebase/Firestore
- **Lokal Databas**: Room
- **Arkitektur**: MVVM + Clean Architecture
- **DI**: Hilt
- **Asynkron**: Coroutines och Flow

### Offline-first Approach
- Fungerar utan internetanslutning
- Automatisk synkronisering när online
- Säker datalagring lokalt
- Backup av viktig information

## Installation 📲

1. Klona repositoryt:
```bash
git clone https://github.com/happyh7/PlantSeeds3.git
```

2. Konfigurera Firebase:
- Kopiera `Scripts/firebase-credentials.example.json` till `Scripts/firebase-credentials.json`
- Uppdatera med dina Firebase-uppgifter

3. Bygg och kör projektet i Android Studio

## Utveckling 👩‍💻

### Förutsättningar
- Android Studio Hedgehog eller senare
- JDK 17
- Android SDK 34
- Firebase-konto

### Kodstruktur
```
app/
├── data/
│   ├── model/      # Datamodeller
│   ├── repository/ # Repositories
│   └── source/     # Datakällor
├── di/             # Dependency Injection
├── ui/
│   ├── screens/    # Skärmar
│   ├── components/ # UI-komponenter
│   └── theme/      # Teman och styling
└── utils/          # Hjälpklasser
```

## Bidra 🤝

Vi välkomnar bidrag! Följ dessa steg:
1. Forka repositoryt
2. Skapa en feature branch
3. Commita dina ändringar
4. Pusha till branchen
5. Öppna en Pull Request

## Licens 📄

Detta projekt är licensierat under MIT-licensen. Se [LICENSE](LICENSE) för detaljer.

## Kontakt 📧

För frågor eller support, öppna ett issue eller kontakta projektägaren via GitHub. 