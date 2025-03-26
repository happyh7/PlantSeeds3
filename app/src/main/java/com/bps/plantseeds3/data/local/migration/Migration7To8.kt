package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val MIGRATION_7_8 = object : Migration(7, 8) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Skapa plantings-tabellen
        database.execSQL("""
            CREATE TABLE IF NOT EXISTS plantings (
                id TEXT PRIMARY KEY NOT NULL,
                plantId TEXT NOT NULL,
                gardenId TEXT NOT NULL,
                plantingDate INTEGER NOT NULL,
                expectedHarvestDate INTEGER,
                actualHarvestDate INTEGER,
                plantingDepth REAL,
                spacing REAL,
                quantity INTEGER NOT NULL DEFAULT 1,
                soilType TEXT,
                soilPh TEXT,
                sunExposure TEXT,
                waterFrequency TEXT,
                lastWatered INTEGER,
                lastFertilized INTEGER,
                lastPruned INTEGER,
                maintenanceNotes TEXT,
                status TEXT NOT NULL DEFAULT 'PLANNED',
                germinationDate INTEGER,
                floweringDate INTEGER,
                fruitingDate INTEGER,
                notes TEXT,
                createdAt INTEGER NOT NULL,
                updatedAt INTEGER NOT NULL,
                FOREIGN KEY(plantId) REFERENCES plants(id) ON DELETE CASCADE,
                FOREIGN KEY(gardenId) REFERENCES gardens(id) ON DELETE CASCADE
            )
        """)
    }
} 