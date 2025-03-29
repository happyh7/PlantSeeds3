# Arkitekturdiagram

## Översikt
Detta dokument innehåller arkitekturdiagram för PlantSeeds3-projektet.

## Clean Architecture

```mermaid
graph TD
    subgraph Presentation Layer
        UI[UI Components]
        VM[ViewModels]
    end
    
    subgraph Domain Layer
        UC[Use Cases]
        DM[Domain Models]
    end
    
    subgraph Data Layer
        R[Repositories]
        DS[Data Sources]
        DTO[DTOs]
    end
    
    UI --> VM
    VM --> UC
    UC --> DM
    UC --> R
    R --> DS
    DS --> DTO
```

## Databasstruktur

```mermaid
erDiagram
    SEEDS ||--o{ PLANTS : contains
    GARDENS ||--o{ PLANTS : contains
    SEEDS {
        string id PK
        string name
        string species
        string description
        string plantingInstructions
        int daysToGermination
        int daysToHarvest
        string lightNeeds
        string waterNeeds
        string soilType
        float temperature
        float spacing
        string[] companionPlants
        string[] avoidPlants
        string imageUrl
        timestamp createdAt
        timestamp updatedAt
    }
    PLANTS {
        string id PK
        string seedId FK
        string gardenId FK
        string name
        string status
        timestamp plantedAt
        timestamp harvestedAt
        string notes
        timestamp createdAt
        timestamp updatedAt
    }
    GARDENS {
        string id PK
        string name
        string description
        string location
        float size
        timestamp createdAt
        timestamp updatedAt
    }
```

## Komponentdiagram

```mermaid
graph TD
    subgraph App
        MainActivity[MainActivity]
        NavGraph[Navigation Graph]
    end
    
    subgraph Features
        Seeds[Seeds Feature]
        Plants[Plants Feature]
        Gardens[Gardens Feature]
    end
    
    subgraph Core
        DI[Dependency Injection]
        DB[Database]
        Network[Network]
    end
    
    MainActivity --> NavGraph
    NavGraph --> Seeds
    NavGraph --> Plants
    NavGraph --> Gardens
    
    Seeds --> DI
    Plants --> DI
    Gardens --> DI
    
    DI --> DB
    DI --> Network
```

## Sekvensdiagram

### Lägga till en ny planta

```mermaid
sequenceDiagram
    participant UI as UI Layer
    participant VM as ViewModel
    participant UC as UseCase
    participant R as Repository
    participant DB as Database
    participant API as API
    
    UI->>VM: addPlant(plantData)
    VM->>UC: execute(plantData)
    UC->>R: addPlant(plantData)
    R->>DB: insert(plantData)
    R->>API: post(plantData)
    API-->>R: response
    R-->>UC: result
    UC-->>VM: result
    VM-->>UI: updateUI(result)
```

## Klassdiagram

```mermaid
classDiagram
    class Plant {
        +String id
        +String name
        +PlantStatus status
        +Date plantedAt
        +Date harvestedAt
        +String notes
        +create()
        +update()
        +delete()
    }
    
    class Seed {
        +String id
        +String name
        +String species
        +String description
        +create()
        +update()
        +delete()
    }
    
    class Garden {
        +String id
        +String name
        +String description
        +create()
        +update()
        +delete()
    }
    
    Plant --> Seed
    Plant --> Garden
```

## Deployment-arkitektur

```mermaid
graph TD
    subgraph Production
        App[Android App]
        Firebase[Firebase Services]
        Storage[Cloud Storage]
    end
    
    subgraph Development
        DevApp[Development App]
        DevFirebase[Firebase Dev]
        DevStorage[Dev Storage]
    end
    
    App --> Firebase
    Firebase --> Storage
    
    DevApp --> DevFirebase
    DevFirebase --> DevStorage
```

## Säkerhetsarkitektur

```mermaid
graph TD
    subgraph Client
        App[Android App]
        Security[Security Layer]
        Encryption[Encryption]
    end
    
    subgraph Server
        API[API Gateway]
        Auth[Authentication]
        RBAC[Role-Based Access]
    end
    
    App --> Security
    Security --> Encryption
    Encryption --> API
    API --> Auth
    Auth --> RBAC
```

## Testarkitektur

```mermaid
graph TD
    subgraph Unit Tests
        UT[Unit Tests]
        Mock[Mock Objects]
    end
    
    subgraph Integration Tests
        IT[Integration Tests]
        TestDB[Test Database]
    end
    
    subgraph UI Tests
        UIT[UI Tests]
        TestUI[Test UI Components]
    end
    
    UT --> Mock
    IT --> TestDB
    UIT --> TestUI
``` 