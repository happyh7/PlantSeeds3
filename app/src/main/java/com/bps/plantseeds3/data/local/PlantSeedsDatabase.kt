package com.bps.plantseeds3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bps.plantseeds3.data.local.converter.LocalDateConverter
import com.bps.plantseeds3.data.local.converter.PlantStatusConverter
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.dao.PlantingDao
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.local.entity.Garden
import com.bps.plantseeds3.data.local.entity.Plant
import com.bps.plantseeds3.data.local.entity.Planting
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.data.local.migration.MIGRATION_6_7
import com.bps.plantseeds3.data.local.migration.MIGRATION_7_8
import com.bps.plantseeds3.data.local.migration.MIGRATION_8_9
import java.text.SimpleDateFormat
import java.util.*

@Database(
    entities = [Garden::class, Seed::class, Plant::class, Planting::class],
    version = 10,
    exportSchema = false
)
@TypeConverters(
    LocalDateConverter::class,
    PlantStatusConverter::class
)
abstract class PlantSeedsDatabase : RoomDatabase() {
    abstract fun gardenDao(): GardenDao
    abstract fun seedDao(): SeedDao
    abstract fun plantDao(): PlantDao
    abstract fun plantingDao(): PlantingDao

    companion object {
        const val DATABASE_NAME = "plantseeds_v10.db"

        private val MIGRATION_9_10 = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Uppdatera gardens-tabellen
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

                // Migrera data från gamla tabellen
                database.execSQL("""
                    INSERT INTO gardens_new (id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt)
                    SELECT id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
                    FROM gardens
                """)

                // Ta bort gamla tabellen
                database.execSQL("DROP TABLE gardens")

                // Byt namn på nya tabellen
                database.execSQL("ALTER TABLE gardens_new RENAME TO gardens")
            }
        }

        val MIGRATIONS = arrayOf<Migration>(
            MIGRATION_6_7,
            MIGRATION_7_8,
            MIGRATION_8_9,
            MIGRATION_9_10
        )
    }
} 