package com.bps.plantseeds3.data.local.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Skapa temporära tabeller med korrekt datumformat
        database.execSQL("""
            CREATE TABLE gardens_new (
                id TEXT PRIMARY KEY NOT NULL,
                name TEXT NOT NULL,
                location TEXT,
                description TEXT,
                size REAL,
                soilType TEXT,
                sunExposure TEXT,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL
            )
        """)

        database.execSQL("""
            CREATE TABLE plants_new (
                id TEXT PRIMARY KEY NOT NULL,
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
                sowingDepth TEXT,
                spacing TEXT,
                daysToGermination TEXT,
                daysToMaturity TEXT,
                sunRequirement TEXT,
                waterRequirement TEXT,
                soilRequirement TEXT,
                soilPh TEXT,
                hardiness TEXT,
                sowingInstructions TEXT,
                growingInstructions TEXT,
                harvestInstructions TEXT,
                storageInstructions TEXT,
                companionPlants TEXT,
                avoidPlants TEXT,
                height TEXT,
                spread TEXT,
                yield TEXT,
                culinaryUses TEXT,
                medicinalUses TEXT,
                tags TEXT,
                notes TEXT,
                createdAt TEXT NOT NULL,
                updatedAt TEXT NOT NULL,
                FOREIGN KEY(gardenId) REFERENCES gardens_new(id) ON DELETE CASCADE
            )
        """)

        // Konvertera datum till ISO-format
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        
        // Migrera gardens
        database.query("SELECT * FROM gardens").use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val location = cursor.getString(cursor.getColumnIndexOrThrow("location"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val size = cursor.getDouble(cursor.getColumnIndexOrThrow("size"))
                val soilType = cursor.getString(cursor.getColumnIndexOrThrow("soilType"))
                val sunExposure = cursor.getString(cursor.getColumnIndexOrThrow("sunExposure"))
                val createdAt = LocalDate.now().format(formatter)
                val updatedAt = LocalDate.now().format(formatter)

                database.execSQL("""
                    INSERT INTO gardens_new (
                        id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, arrayOf(id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt))
            }
        }

        // Migrera plants
        database.query("SELECT * FROM plants").use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val scientificName = cursor.getString(cursor.getColumnIndexOrThrow("scientificName"))
                val species = cursor.getString(cursor.getColumnIndexOrThrow("species"))
                val variety = cursor.getString(cursor.getColumnIndexOrThrow("variety"))
                val category = cursor.getString(cursor.getColumnIndexOrThrow("category"))
                val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                val gardenId = cursor.getString(cursor.getColumnIndexOrThrow("gardenId"))
                val status = cursor.getString(cursor.getColumnIndexOrThrow("status"))
                val plantingDate = LocalDate.now().format(formatter)
                val harvestDate = null
                val sowingDepth = cursor.getString(cursor.getColumnIndexOrThrow("sowingDepth"))
                val spacing = cursor.getString(cursor.getColumnIndexOrThrow("spacing"))
                val daysToGermination = cursor.getString(cursor.getColumnIndexOrThrow("daysToGermination"))
                val daysToMaturity = cursor.getString(cursor.getColumnIndexOrThrow("daysToMaturity"))
                val sunRequirement = cursor.getString(cursor.getColumnIndexOrThrow("sunRequirement"))
                val waterRequirement = cursor.getString(cursor.getColumnIndexOrThrow("waterRequirement"))
                val soilRequirement = cursor.getString(cursor.getColumnIndexOrThrow("soilRequirement"))
                val soilPh = cursor.getString(cursor.getColumnIndexOrThrow("soilPh"))
                val hardiness = cursor.getString(cursor.getColumnIndexOrThrow("hardiness"))
                val sowingInstructions = cursor.getString(cursor.getColumnIndexOrThrow("sowingInstructions"))
                val growingInstructions = cursor.getString(cursor.getColumnIndexOrThrow("growingInstructions"))
                val harvestInstructions = cursor.getString(cursor.getColumnIndexOrThrow("harvestInstructions"))
                val storageInstructions = cursor.getString(cursor.getColumnIndexOrThrow("storageInstructions"))
                val companionPlants = cursor.getString(cursor.getColumnIndexOrThrow("companionPlants"))
                val avoidPlants = cursor.getString(cursor.getColumnIndexOrThrow("avoidPlants"))
                val height = cursor.getString(cursor.getColumnIndexOrThrow("height"))
                val spread = cursor.getString(cursor.getColumnIndexOrThrow("spread"))
                val yield = cursor.getString(cursor.getColumnIndexOrThrow("yield"))
                val culinaryUses = cursor.getString(cursor.getColumnIndexOrThrow("culinaryUses"))
                val medicinalUses = cursor.getString(cursor.getColumnIndexOrThrow("medicinalUses"))
                val tags = cursor.getString(cursor.getColumnIndexOrThrow("tags"))
                val notes = cursor.getString(cursor.getColumnIndexOrThrow("notes"))
                val createdAt = LocalDate.now().format(formatter)
                val updatedAt = LocalDate.now().format(formatter)

                database.execSQL("""
                    INSERT INTO plants_new (
                        id, name, scientificName, species, variety, category, description,
                        gardenId, status, plantingDate, harvestDate, sowingDepth, spacing,
                        daysToGermination, daysToMaturity, sunRequirement, waterRequirement,
                        soilRequirement, soilPh, hardiness, sowingInstructions, growingInstructions,
                        harvestInstructions, storageInstructions, companionPlants, avoidPlants,
                        height, spread, yield, culinaryUses, medicinalUses, tags, notes,
                        createdAt, updatedAt
                    ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """, arrayOf(
                    id, name, scientificName, species, variety, category, description,
                    gardenId, status, plantingDate, harvestDate, sowingDepth, spacing,
                    daysToGermination, daysToMaturity, sunRequirement, waterRequirement,
                    soilRequirement, soilPh, hardiness, sowingInstructions, growingInstructions,
                    harvestInstructions, storageInstructions, companionPlants, avoidPlants,
                    height, spread, yield, culinaryUses, medicinalUses, tags, notes,
                    createdAt, updatedAt
                ))
            }
        }

        // Ta bort gamla tabeller
        database.execSQL("DROP TABLE plants")
        database.execSQL("DROP TABLE gardens")

        // Byt namn på nya tabeller
        database.execSQL("ALTER TABLE gardens_new RENAME TO gardens")
        database.execSQL("ALTER TABLE plants_new RENAME TO plants")
    }
} 