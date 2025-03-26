package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Skapa en temporär tabell med den nya strukturen
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
                plantingDate TEXT NOT NULL,
                harvestDate TEXT,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL
            )
        """)

        // Kopiera data från den gamla tabellen till den nya, med konvertering av status
        db.execSQL("""
            INSERT INTO plants_new (
                id, name, scientificName, species, variety, category, description,
                gardenId, status, plantingDate, harvestDate, createdAt, updatedAt
            )
            SELECT
                id, name, scientificName, species, variety, category, description,
                gardenId,
                CASE status
                    WHEN 'PLANTED' THEN 'GROWING'
                    WHEN 'GROWING' THEN 'GROWING'
                    WHEN 'FLOWERING' THEN 'FLOWERING'
                    WHEN 'FRUITING' THEN 'FRUITING'
                    WHEN 'HARVESTED' THEN 'HARVESTED'
                    WHEN 'DEAD' THEN 'DEAD'
                    ELSE 'SEED'
                END as status,
                plantingDate, harvestDate, createdAt, updatedAt
            FROM plants
        """)

        // Ta bort den gamla tabellen
        db.execSQL("DROP TABLE plants")

        // Byt namn på den nya tabellen
        db.execSQL("ALTER TABLE plants_new RENAME TO plants")
    }
} 