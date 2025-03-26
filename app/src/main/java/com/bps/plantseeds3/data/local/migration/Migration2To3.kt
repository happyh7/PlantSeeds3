package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration2To3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Skapa en temporär tabell för plants med den nya strukturen
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS plants_new (
                id TEXT NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                scientificName TEXT NOT NULL,
                species TEXT NOT NULL,
                variety TEXT NOT NULL,
                category TEXT NOT NULL,
                description TEXT NOT NULL,
                gardenId TEXT NOT NULL,
                status TEXT NOT NULL,
                plantingDate INTEGER NOT NULL,
                harvestDate INTEGER,
                createdAt INTEGER NOT NULL,
                updatedAt INTEGER NOT NULL,
                FOREIGN KEY(gardenId) REFERENCES gardens(id) ON DELETE CASCADE
            )
        """)

        // Kopiera data från den gamla plants-tabellen till den nya
        db.execSQL("""
            INSERT INTO plants_new (
                id, name, scientificName, species, variety, category, description,
                gardenId, status, plantingDate, harvestDate, createdAt, updatedAt
            )
            SELECT 
                id, name, scientificName, species, variety, category, description,
                gardenId, status, plantedDate, expectedHarvestDate, createdAt, updatedAt
            FROM plants
        """)

        // Ta bort den gamla plants-tabellen
        db.execSQL("DROP TABLE plants")

        // Byt namn på den nya plants-tabellen till det korrekta namnet
        db.execSQL("ALTER TABLE plants_new RENAME TO plants")

        // Skapa en temporär tabell för gardens med den nya strukturen
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS gardens_new (
                id TEXT NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                location TEXT,
                description TEXT,
                size REAL,
                soilType TEXT,
                sunExposure TEXT,
                createdAt INTEGER NOT NULL,
                updatedAt INTEGER NOT NULL
            )
        """)

        // Kopiera data från den gamla gardens-tabellen till den nya
        db.execSQL("""
            INSERT INTO gardens_new (
                id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
            )
            SELECT 
                id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
            FROM gardens
        """)

        // Ta bort den gamla gardens-tabellen
        db.execSQL("DROP TABLE gardens")

        // Byt namn på den nya gardens-tabellen till det korrekta namnet
        db.execSQL("ALTER TABLE gardens_new RENAME TO gardens")
    }
} 