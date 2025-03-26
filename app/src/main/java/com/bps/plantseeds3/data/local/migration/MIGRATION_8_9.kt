package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Skapa temporär tabell med nya fält
        database.execSQL("""
            CREATE TABLE gardens_new (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                location TEXT,
                description TEXT,
                size REAL,
                width REAL,
                length REAL,
                elevation REAL,
                slope REAL,
                soilType TEXT,
                sunExposure TEXT,
                irrigation TEXT,
                fence TEXT,
                notes TEXT,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL
            )
        """)

        // Kopiera data från gamla tabellen till nya
        database.execSQL("""
            INSERT INTO gardens_new (
                id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
            )
            SELECT id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
            FROM gardens
        """)

        // Ta bort gamla tabellen
        database.execSQL("DROP TABLE gardens")

        // Byt namn på nya tabellen
        database.execSQL("ALTER TABLE gardens_new RENAME TO gardens")
    }
} 