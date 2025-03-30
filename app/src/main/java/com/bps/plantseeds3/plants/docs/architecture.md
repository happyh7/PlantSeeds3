# Arkitektur - Växt-modulen

## Översikt
Växt-modulen följer Clean Architecture-principer med tre huvudlager:
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
- Integrerar med seeds- och gardens-modulerna
- Hanterar relationer mellan växter och trädgårdar

### 2. Domänlagret
- Innehåller affärslogik och domänmodeller
- Definierar repository-interfacet
- Innehåller use cases för växthantering
- Är oberoende av data- och presentationslagren
- Hanterar växtens livscykel och status
- Definierar relationer mellan växter och andra entiteter

### 3. Presentationslagret
- Hanterar UI och användarinteraktion
- Använder Jetpack Compose för UI
- Implementerar MVVM-mönstret
- Hanterar state och events
- Visar växtens utveckling och status
- Hanterar interaktion med trädgårdar

## Dataflöde
1. Användaren interagerar med UI
2. ViewModel tar emot events
3. Use cases utför affärslogik
4. Repository hanterar datalagring
5. UI uppdateras med nytt state

## Beroenden
- seeds-modulen för fröhantering
- gardens-modulen för trädgårdshantering
- common-modulen för delad funktionalitet
- Injicerar beroenden via Hilt

## Testning
- Unit-tester för domänlagret
- Integrationstester för data-lagret
- UI-tester för presentationslagret
- End-to-end-tester för hela flödet
- Tester för integration med seeds- och gardens-modulerna
- Tester för växtens livscykel och statusändringar 