# PlantSeeds3

En Android-app för att hantera din växtodling och fröbank.

## Funktioner

- Hantera din fröbank
- Spåra odlingar
- Få påminnelser
- Dela med dig av dina växter
- Lär dig om olika växter

## Teknisk Stack

- Kotlin
- Jetpack Compose
- Hilt för dependency injection
- Room för databas
- Material Design 3
- Clean Architecture

## Projektstruktur

```
plantseeds3/
├── app/                # Huvudapplikation
├── common/             # Gemensamma komponenter och verktyg
│   ├── ui/            # Återanvändbara UI-komponenter
│   ├── util/          # Utility-klasser
│   ├── config/        # Konfigurationsklasser
│   └── docs/          # Dokumentation
└── docs/              # Projektövergripande dokumentation
```

## Installation

1. Klona projektet
```bash
git clone https://github.com/dittanvändarnamn/plantseeds3.git
```

2. Öppna i Android Studio
3. Låt Gradle synka
4. Kör appen

## Utveckling

Se [CONTRIBUTING.md](common/src/main/java/com/bps/plantseeds3/common/docs/CONTRIBUTING.md) för riktlinjer om hur du kan bidra till projektet.

## Dokumentation

- [Common Module](common/src/main/java/com/bps/plantseeds3/common/docs/README.md)
- [Changelog](common/src/main/java/com/bps/plantseeds3/common/docs/CHANGELOG.md)
- [Roadmap](common/src/main/java/com/bps/plantseeds3/common/docs/ROADMAP.md)

## Licens

Detta projekt är licensierat under MIT-licensen - se [LICENSE](LICENSE) för detaljer. 