package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_8_9 = object : Migration(8, 9) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Skapa temporär tabell med nya fält
        db.execSQL("""
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
        """.trimIndent())

        // Kopiera data från gamla tabellen till nya
        db.execSQL("""
            INSERT INTO gardens_new (
                id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
            )
            SELECT id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
            FROM gardens
        """)

        // Ta bort gamla tabellen
        db.execSQL("DROP TABLE gardens")

        // Byt namn på nya tabellen
        db.execSQL("ALTER TABLE gardens_new RENAME TO gardens")
    }
} 