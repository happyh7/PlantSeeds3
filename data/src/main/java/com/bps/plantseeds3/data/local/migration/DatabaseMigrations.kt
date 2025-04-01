package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bps.plantseeds3.common.exceptions.DatabaseException

object DatabaseMigrations {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            try {
                db.execSQL("""
                    ALTER TABLE seeds 
                    ADD COLUMN plantId TEXT
                """)
            } catch (e: Exception) {
                throw DatabaseException.MigrationFailedException(1, 2, e)
            }
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            try {
                db.execSQL("""
                    CREATE INDEX IF NOT EXISTS index_seeds_plantId 
                    ON seeds(plantId)
                """)
            } catch (e: Exception) {
                throw DatabaseException.MigrationFailedException(2, 3, e)
            }
        }
    }

    val MIGRATION_13_14 = object : Migration(13, 14) {
        override fun migrate(db: SupportSQLiteDatabase) {
            try {
                // Skapa temporär tabell för seeds
                db.execSQL("""
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
                db.execSQL("""
                    INSERT INTO seeds_temp (
                        id, plantId, name, species, description, plantingInstructions,
                        daysToGermination, daysToHarvest, lightNeeds, waterNeeds,
                        soilType, temperature, spacing, companionPlants, avoidPlants,
                        imageUrl, createdAt, updatedAt, isSynced
                    )
                    SELECT 
                        id, plantId, name, species, description, plantingInstructions,
                        CAST(daysToGermination AS INTEGER),
                        CAST(daysToHarvest AS INTEGER),
                        lightNeeds, waterNeeds,
                        soilType, temperature, spacing, companionPlants, avoidPlants,
                        imageUrl,
                        CAST(createdAt AS INTEGER),
                        CAST(updatedAt AS INTEGER),
                        CAST(isSynced AS INTEGER)
                    FROM seeds
                """)

                // Ta bort gamla tabellen
                db.execSQL("DROP TABLE seeds")

                // Byt namn på temporära tabellen
                db.execSQL("ALTER TABLE seeds_temp RENAME TO seeds")

                // Skapa index
                db.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_plantId ON seeds(plantId)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_name ON seeds(name)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_createdAt ON seeds(createdAt)")
                db.execSQL("CREATE INDEX IF NOT EXISTS index_seeds_updatedAt ON seeds(updatedAt)")
            } catch (e: Exception) {
                throw DatabaseException.MigrationFailedException(13, 14, e)
            }
        }
    }

    // Funktion för att få alla migrationer
    fun getMigrations(): Array<Migration> {
        return arrayOf(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_13_14)
    }
} 