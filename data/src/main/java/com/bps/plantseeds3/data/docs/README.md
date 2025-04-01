# Data Module

## Översikt
Data-modulen är ansvarig för all datahantering i applikationen. Den implementerar repository-mönstret och hanterar både lokala och nätverksbaserade datakällor.

## Struktur
```
data/
├── local/           # Lokal datalagring (Room)
├── remote/          # Nätverksanrop (API)
├── repository/      # Repository-implementationer
└── mapper/          # Datamappning mellan lager
```

## Komponenter

### Local
- Room-databas
- DAO-interfaces
- Entity-klasser
- Type converters

### Remote
- API-interfaces
- DTO-klasser
- Nätverkskonfiguration
- API-klienter

### Repository
- Repository-implementationer
- Datakällshantering
- Felhantering
- Caching-strategier

### Mapper
- Entity till Domain-mappning
- DTO till Domain-mappning
- Domain till Entity-mappning
- Domain till DTO-mappning

## Beroenden
- Room
- Retrofit
- Hilt
- Kotlin Coroutines
- Flow
- Moshi 