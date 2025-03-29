# Databasstruktur

## Översikt
PlantSeeds3 använder Room för lokal datalagring och Firebase för molnlagring. Databasen är designad för att stödja offline-first funktionalitet med synkronisering mot Firebase.

## Datamodeller

### Seed (Frö)
```kotlin
@Entity(tableName = "seeds")
data class SeedEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val species: String?,
    val description: String?,
    val plantingInstructions: String?,
    val daysToGermination: Int?,
    val daysToHarvest: Int?,
    val lightNeeds: String?,
    val waterNeeds: String?,
    val soilType: String?,
    val temperature: String?,
    val spacing: String?,
    val companionPlants: List<String>?,
    val avoidPlants: List<String>?,
    val imageUrl: String?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val isSynced: Boolean = false
)
```

### Plant (Planta)
```kotlin
@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val seedId: String,
    val gardenId: String,
    val name: String,
    val status: PlantStatus,
    val plantedDate: Date,
    val expectedHarvestDate: Date?,
    val actualHarvestDate: Date?,
    val notes: String?,
    val imageUrls: List<String>?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val isSynced: Boolean = false
)
```

### Garden (Trädgård)
```kotlin
@Entity(tableName = "gardens")
data class GardenEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val location: String,
    val description: String?,
    val size: String?,
    val soilType: String?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date(),
    val isSynced: Boolean = false
)
```

## Relationer

### Plant-Garden Relation
```kotlin
data class PlantWithGarden(
    @Embedded val plant: PlantEntity,
    @Relation(
        parentColumn = "gardenId",
        entityColumn = "id"
    )
    val garden: GardenEntity
)
```

### Plant-Seed Relation
```kotlin
data class PlantWithSeed(
    @Embedded val plant: PlantEntity,
    @Relation(
        parentColumn = "seedId",
        entityColumn = "id"
    )
    val seed: SeedEntity
)
```

## DAOs (Data Access Objects)

### SeedDao
```kotlin
@Dao
interface SeedDao {
    @Query("SELECT * FROM seeds ORDER BY name ASC")
    fun getAllSeeds(): Flow<List<SeedEntity>>

    @Query("SELECT * FROM seeds WHERE id = :id")
    suspend fun getSeedById(id: String): SeedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeed(seed: SeedEntity)

    @Update
    suspend fun updateSeed(seed: SeedEntity)

    @Delete
    suspend fun deleteSeed(seed: SeedEntity)

    @Query("SELECT * FROM seeds WHERE name LIKE '%' || :query || '%'")
    fun searchSeeds(query: String): Flow<List<SeedEntity>>
}
```

### PlantDao
```kotlin
@Dao
interface PlantDao {
    @Query("SELECT * FROM plants ORDER BY plantedDate DESC")
    fun getAllPlants(): Flow<List<PlantWithSeed>>

    @Query("SELECT * FROM plants WHERE gardenId = :gardenId")
    fun getPlantsByGarden(gardenId: String): Flow<List<PlantWithSeed>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: PlantEntity)

    @Update
    suspend fun updatePlant(plant: PlantEntity)

    @Delete
    suspend fun deletePlant(plant: PlantEntity)
}
```

### GardenDao
```kotlin
@Dao
interface GardenDao {
    @Query("SELECT * FROM gardens ORDER BY name ASC")
    fun getAllGardens(): Flow<List<GardenEntity>>

    @Query("SELECT * FROM gardens WHERE id = :id")
    suspend fun getGardenById(id: String): GardenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGarden(garden: GardenEntity)

    @Update
    suspend fun updateGarden(garden: GardenEntity)

    @Delete
    suspend fun deleteGarden(garden: GardenEntity)
}
```

## Type Converters

```kotlin
class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toString(list: List<String>): String {
        return list.joinToString(",")
    }
}
```

## Synkronisering

### Sync Status
```kotlin
@Entity(tableName = "sync_status")
data class SyncStatusEntity(
    @PrimaryKey
    val id: String = "sync_status",
    val lastSyncTime: Date,
    val isSyncing: Boolean = false,
    val error: String? = null
)
```

### Sync Logik
1. Lokala ändringar markeras med `isSynced = false`
2. Synkronisering körs i bakgrunden
3. Lyckade synkroniseringar uppdaterar `isSynced = true`
4. Fel loggas och kan återställas

## Migrations

### Version 1 -> 2
```kotlin
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("""
            ALTER TABLE plants 
            ADD COLUMN imageUrls TEXT
        """)
    }
}
```

## Indexering

### Performance Indexes
```kotlin
@Entity(
    tableName = "seeds",
    indices = [
        Index("name"),
        Index("species")
    ]
)
```

## Backup & Restore

### Backup Process
1. Exportera Room-databasen
2. Kryptera data
3. Spara till lokal lagring
4. Uppladda till Firebase Storage

### Restore Process
1. Ladda ner från Firebase Storage
2. Dekryptera data
3. Återställ Room-databasen
4. Synkronisera med Firebase 