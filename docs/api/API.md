# API-dokumentation

## Översikt
Detta dokument beskriver API:et för PlantSeeds3-projektet.

## Base URL
```
https://api.plantseeds3.com/v1
```

## Autentisering
Alla API-anrop kräver autentisering med en Bearer-token:
```
Authorization: Bearer <token>
```

## Endpoints

### Plants

#### Hämta alla plantor
```http
GET /plants
```

Response:
```json
{
  "plants": [
    {
      "id": "string",
      "name": "string",
      "species": "string",
      "status": "string",
      "plantedAt": "string",
      "harvestedAt": "string",
      "notes": "string",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ],
  "total": "integer",
  "page": "integer",
  "size": "integer"
}
```

#### Hämta en specifik planta
```http
GET /plants/{id}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "species": "string",
  "status": "string",
  "plantedAt": "string",
  "harvestedAt": "string",
  "notes": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Skapa ny planta
```http
POST /plants
```

Request body:
```json
{
  "name": "string",
  "species": "string",
  "status": "string",
  "plantedAt": "string",
  "notes": "string"
}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "species": "string",
  "status": "string",
  "plantedAt": "string",
  "harvestedAt": "string",
  "notes": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Uppdatera planta
```http
PUT /plants/{id}
```

Request body:
```json
{
  "name": "string",
  "species": "string",
  "status": "string",
  "plantedAt": "string",
  "harvestedAt": "string",
  "notes": "string"
}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "species": "string",
  "status": "string",
  "plantedAt": "string",
  "harvestedAt": "string",
  "notes": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Ta bort planta
```http
DELETE /plants/{id}
```

Response:
```json
{
  "success": "boolean"
}
```

### Seeds

#### Hämta alla frön
```http
GET /seeds
```

Response:
```json
{
  "seeds": [
    {
      "id": "string",
      "name": "string",
      "species": "string",
      "description": "string",
      "plantingInstructions": "string",
      "daysToGermination": "integer",
      "daysToHarvest": "integer",
      "lightNeeds": "string",
      "waterNeeds": "string",
      "soilType": "string",
      "temperature": "number",
      "spacing": "number",
      "companionPlants": ["string"],
      "avoidPlants": ["string"],
      "imageUrl": "string",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ],
  "total": "integer",
  "page": "integer",
  "size": "integer"
}
```

#### Hämta ett specifikt frö
```http
GET /seeds/{id}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "species": "string",
  "description": "string",
  "plantingInstructions": "string",
  "daysToGermination": "integer",
  "daysToHarvest": "integer",
  "lightNeeds": "string",
  "waterNeeds": "string",
  "soilType": "string",
  "temperature": "number",
  "spacing": "number",
  "companionPlants": ["string"],
  "avoidPlants": ["string"],
  "imageUrl": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Skapa nytt frö
```http
POST /seeds
```

Request body:
```json
{
  "name": "string",
  "species": "string",
  "description": "string",
  "plantingInstructions": "string",
  "daysToGermination": "integer",
  "daysToHarvest": "integer",
  "lightNeeds": "string",
  "waterNeeds": "string",
  "soilType": "string",
  "temperature": "number",
  "spacing": "number",
  "companionPlants": ["string"],
  "avoidPlants": ["string"],
  "imageUrl": "string"
}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "species": "string",
  "description": "string",
  "plantingInstructions": "string",
  "daysToGermination": "integer",
  "daysToHarvest": "integer",
  "lightNeeds": "string",
  "waterNeeds": "string",
  "soilType": "string",
  "temperature": "number",
  "spacing": "number",
  "companionPlants": ["string"],
  "avoidPlants": ["string"],
  "imageUrl": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Uppdatera frö
```http
PUT /seeds/{id}
```

Request body:
```json
{
  "name": "string",
  "species": "string",
  "description": "string",
  "plantingInstructions": "string",
  "daysToGermination": "integer",
  "daysToHarvest": "integer",
  "lightNeeds": "string",
  "waterNeeds": "string",
  "soilType": "string",
  "temperature": "number",
  "spacing": "number",
  "companionPlants": ["string"],
  "avoidPlants": ["string"],
  "imageUrl": "string"
}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "species": "string",
  "description": "string",
  "plantingInstructions": "string",
  "daysToGermination": "integer",
  "daysToHarvest": "integer",
  "lightNeeds": "string",
  "waterNeeds": "string",
  "soilType": "string",
  "temperature": "number",
  "spacing": "number",
  "companionPlants": ["string"],
  "avoidPlants": ["string"],
  "imageUrl": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Ta bort frö
```http
DELETE /seeds/{id}
```

Response:
```json
{
  "success": "boolean"
}
```

### Gardens

#### Hämta alla trädgårdar
```http
GET /gardens
```

Response:
```json
{
  "gardens": [
    {
      "id": "string",
      "name": "string",
      "description": "string",
      "location": "string",
      "size": "number",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ],
  "total": "integer",
  "page": "integer",
  "size": "integer"
}
```

#### Hämta en specifik trädgård
```http
GET /gardens/{id}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "location": "string",
  "size": "number",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Skapa ny trädgård
```http
POST /gardens
```

Request body:
```json
{
  "name": "string",
  "description": "string",
  "location": "string",
  "size": "number"
}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "location": "string",
  "size": "number",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Uppdatera trädgård
```http
PUT /gardens/{id}
```

Request body:
```json
{
  "name": "string",
  "description": "string",
  "location": "string",
  "size": "number"
}
```

Response:
```json
{
  "id": "string",
  "name": "string",
  "description": "string",
  "location": "string",
  "size": "number",
  "createdAt": "string",
  "updatedAt": "string"
}
```

#### Ta bort trädgård
```http
DELETE /gardens/{id}
```

Response:
```json
{
  "success": "boolean"
}
```

## Felhantering

### Felkoder
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error

### Felformat
```json
{
  "error": {
    "code": "string",
    "message": "string",
    "details": "object"
  }
}
```

## Rate Limiting
- 100 anrop per minut per användare
- 1000 anrop per timme per användare

## Versionering
API:et följer semantisk versionering (MAJOR.MINOR.PATCH).
- MAJOR: Brytande ändringar
- MINOR: Ny funktionalitet
- PATCH: Bugfixar

## Caching
- GET-anrop cachar i 5 minuter
- Cache-Control header inkluderas i svaret

## Webhooks
Webhooks stöds för följande händelser:
- plant.created
- plant.updated
- plant.deleted
- seed.created
- seed.updated
- seed.deleted
- garden.created
- garden.updated
- garden.deleted

## SDK
Ett officiellt SDK finns tillgängligt för:
- Android (Kotlin)
- iOS (Swift)
- Web (JavaScript) 