# Arkitektur - Fröbank-modulen

## Översikt
Fröbank-modulen följer Clean Architecture-principer med tre huvudlager:
1. Data-lagret
2. Domänlagret
3. Presentationslagret

## Arkitekturdiagram
```
┌─────────────────┐
│  Presentation   │
│  (UI Layer)     │
└────────┬────────┘
         │
┌────────▼────────┐
│    Domain       │
│  (Business)     │
└────────┬────────┘
         │
┌────────▼────────┐
│     Data        │
│  (Repository)   │
└─────────────────┘
```

## Lagren

### 1. Data-lagret
- Hanterar all datalagring och databasoperationer
- Implementerar repository-interfacet från domänlagret
- Använder Room för persistent lagring
- Hanterar datamappning mellan entiteter och domänmodeller
- Integrerar med plants-modulen
- Hanterar relationer mellan frön och växter

### 2. Domänlagret
- Innehåller affärslogik och domänmodeller
- Definierar repository-interfacet
- Innehåller use cases för fröhantering
- Är oberoende av data- och presentationslagren
- Hanterar fröns egenskaper och krav
- Definierar relationer mellan frön och växter

### 3. Presentationslagret
- Hanterar UI och användarinteraktion
- Använder Jetpack Compose för UI
- Implementerar MVVM-mönstret
- Hanterar state och events
- Visar fröns information och egenskaper
- Hanterar interaktion med växter

## Dataflöde
1. Användaren interagerar med UI
2. ViewModel tar emot events
3. Use cases utför affärslogik
4. Repository hanterar datalagring
5. UI uppdateras med nytt state

## Beroenden
- plants-modulen för växthantering
- common-modulen för delad funktionalitet
- Injicerar beroenden via Hilt

## Testning
- Unit-tester för domänlagret
- Integrationstester för data-lagret
- UI-tester för presentationslagret
- End-to-end-tester för hela flödet
- Tester för integration med plants-modulen
- Tester för fröegenskaper och krav 