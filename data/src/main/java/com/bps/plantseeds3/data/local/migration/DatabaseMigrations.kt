package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bps.plantseeds3.common.exceptions.DatabaseException

object DatabaseMigrations {
    val MIGRATION_13_14 = object : Migration(13, 14) {
        override fun migrate(database: SupportSQLiteDatabase) {
            try {
                // Skapa temporär tabell för seeds
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS seeds_temp (
                        id TEXT NOT NULL PRIMARY KEY,
                        plantId TEXT NOT NULL,
                        name TEXT NOT NULL,
                        species TEXT,
                        description TEXT,
                        plantingInstructions TEXT,
                        daysToGermination INTEGER,
                        daysToHarvest INTEGER,
                        lightNeeds TEXT,
                        waterNeeds TEXT,
                        soilType TEXT,
                        temperature TEXT,
                        spacing TEXT,
                        companionPlants TEXT,
                        avoidPlants TEXT,
                        imageUrl TEXT,
                        createdAt INTEGER NOT NULL,
                        updatedAt INTEGER NOT NULL,
                        isSynced INTEGER NOT NULL DEFAULT 0,
                        FOREIGN KEY (plantId) REFERENCES plants(id) ON DELETE CASCADE
                    )
                """)

                // Kopiera data från gamla tabellen till den temporära
                database.execSQL("""
                    INSERT INTO seeds_temp (
                        id, plantId, name, species, description, plantingInstructions,
                        daysToGermination, daysToHarvest, lightNeeds, waterNeeds,
                        soilType, temperature, spacing, companionPlants, avoidPlants,
                        imageUrl, createdAt, updatedAt, isSynced
                    )
                    SELECT 
                        id, plantId, name, species, description, plantingInstructions,
                        daysToGermination, daysToHarvest, lightNeeds, waterNeeds,
                        soilType, temperature, spacing, companionPlants, avoidPlants,
                        imageUrl, createdAt, updatedAt, isSynced
                    FROM seeds
                """)

                // Ta bort gamla tabellen
                database.execSQL("DROP TABLE seeds")

                // Byt namn på temporära tabellen
                database.execSQL("ALTER TABLE seeds_temp RENAME TO seeds")

                // Skapa index
                database.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_plantId ON seeds(plantId)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_name ON seeds(name)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_createdAt ON seeds(createdAt)")
                database.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_updatedAt ON seeds(updatedAt)")
            } catch (e: Exception) {
                throw DatabaseException.MigrationFailedException(13, 14, e)
            }
        }
    }

    // Funktion för att få alla migrationer
    fun getMigrations(): Array<Migration> {
        return arrayOf(MIGRATION_13_14)
    }
} 