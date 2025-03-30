# Common-modulen

## Översikt
Common-modulen innehåller gemensam funktionalitet som används av alla andra moduler i applikationen. Detta inkluderar:
- Gemensamma UI-komponenter
- Delade verktyg och hjälpfunktioner
- Grundläggande datamodeller
- Konstanter och konfiguration
- Loggning och felhantering

## Struktur
```
common/
├── ui/           # Gemensamma UI-komponenter
├── util/         # Hjälpfunktioner och verktyg
├── model/        # Grundläggande datamodeller
├── config/       # Konstanter och konfiguration
└── docs/         # Dokumentation
```

## Beroenden
- AndroidX Core
- AndroidX Compose
- Kotlin Standard Library
- Hilt för dependency injection

## API
### UI-komponenter
- `CommonButton`: Standardiserad knapp
- `CommonTextField`: Standardiserat textfält
- `CommonCard`: Standardiserad kort-komponent
- `CommonDialog`: Standardiserad dialog

### Verktyg
- `DateUtils`: Datumhantering
- `StringUtils`: Stränghantering
- `ImageUtils`: Bildhantering
- `ValidationUtils`: Validering

### Datamodeller
- `Result`: Resultattyp för operationer
- `Error`: Felhantering
- `Resource`: Resurshantering

## Användning
För att använda common-modulen:
1. Inkludera modulen i ditt projekt
2. Importera nödvändiga komponenter
3. Använd komponenterna i din kod

## Dokumentation
- [Arkitektur](architecture.md)
- [API-dokumentation](api.md)
- [Utvecklingsriktlinjer](development.md) 