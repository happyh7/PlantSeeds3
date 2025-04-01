# Domain Module

## Översikt
Domain-modulen innehåller affärslogiken och är central för applikationen. Den definierar modeller, repository-interfaces och use cases som används av andra moduler.

## Struktur
```
domain/
├── model/           # Domain-modeller
├── repository/      # Repository-interfaces
├── use_case/        # Use cases
└── util/            # Utility-klasser
```

## Komponenter

### Model
- Domain-modeller
- Value objects
- Enums
- Constants

### Repository
- Repository-interfaces
- Data contracts
- Error definitions
- Response types

### Use Case
- Business logic
- Operation definitions
- Validation rules
- Error handling

### Util
- Helper functions
- Extensions
- Constants
- Type definitions

## Beroenden
- Kotlin Coroutines
- Flow
- Hilt
- Arrow (för funktionell programmering) 