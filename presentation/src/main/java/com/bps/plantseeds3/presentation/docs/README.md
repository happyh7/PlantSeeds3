# Presentation Module

## Översikt
Presentation-modulen är ansvarig för all UI-relaterad kod i applikationen. Den implementerar MVVM-arkitekturmönstret och använder Jetpack Compose för UI-komponenter.

## Struktur
```
presentation/
├── ui/           # UI-komponenter och tema
├── navigation/   # Navigation och routing
├── state/        # UI-state och events
├── screen/       # Skärmar och layouts
├── viewmodel/    # ViewModels
└── di/           # Dependency injection
```

## Komponenter

### UI
- Grundläggande UI-komponenter (knappar, textfält, kort)
- Tema och styling
- Layout-komponenter

### Navigation
- Skärmnavigation
- Deep linking
- Bottom navigation

### State
- UI-states för varje skärm
- Events för användarinteraktioner
- State management

### Screen
- Lista över alla skärmar
- Skärmkomponenter
- Layouts

### ViewModel
- Business logic
- State management
- Event handling

## Nästa steg
1. Implementera grundläggande UI-komponenter
2. Skapa tema och styling
3. Implementera navigation
4. Utveckla skärmar för:
   - Seed-modulen
   - Plant-modulen
   - Garden-modulen

## Beroenden
- Jetpack Compose
- Hilt för DI
- Navigation Compose
- ViewModel
- Coroutines
- Flow 