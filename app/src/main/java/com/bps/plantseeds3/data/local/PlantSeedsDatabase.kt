package com.bps.plantseeds3.data.local

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bps.plantseeds3.data.local.converter.LocalDateConverter
import com.bps.plantseeds3.data.local.converter.PlantStatusConverter
import com.bps.plantseeds3.data.local.converter.Converters
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
    version = 12,
    exportSchema = false
)
@TypeConverters(
    LocalDateConverter::class,
    PlantStatusConverter::class,
    Converters::class
)
abstract class PlantSeedsDatabase : RoomDatabase() {
    private val TAG = "PlantSeedsDatabase"

    abstract fun gardenDao(): GardenDao
    abstract fun seedDao(): SeedDao
    abstract fun plantDao(): PlantDao
    abstract fun plantingDao(): PlantingDao

    companion object {
        const val DATABASE_NAME = "plantseeds_v12.db"

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("PlantSeedsDatabase", "Utför migrering från version 6 till 7")
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `seeds` (
                        `id` TEXT NOT NULL,
                        `name` TEXT NOT NULL,
                        `scientificName` TEXT,
                        `species` TEXT,
                        `variety` TEXT,
                        `description` TEXT,
                        `category` TEXT NOT NULL,
                        `plantingDepth` REAL,
                        `plantingDistance` REAL,
                        `plantSpacing` REAL,
                        `rowSpacing` REAL,
                        `plantingDates` TEXT,
                        `daysToGermination` INTEGER,
                        `daysToMaturity` INTEGER,
                        `lifespan` TEXT,
                        `hardinessZone` TEXT,
                        `sunRequirement` TEXT,
                        `waterRequirement` TEXT,
                        `soilType` TEXT,
                        `soilPh` REAL,
                        `sowingInstructions` TEXT,
                        `growingInstructions` TEXT,
                        `harvestingInstructions` TEXT,
                        `storageInstructions` TEXT,
                        `harvestPeriod` TEXT,
                        `maintenanceDates` TEXT,
                        `fertilizingSchedule` TEXT,
                        `pruningSchedule` TEXT,
                        `companionPlants` TEXT,
                        `avoidPlants` TEXT,
                        `height` REAL,
                        `spread` REAL,
                        `yield` TEXT,
                        `culinaryUses` TEXT,
                        `medicinalUses` TEXT,
                        `imageUrl` TEXT,
                        `source` TEXT,
                        `isFavorite` INTEGER NOT NULL DEFAULT 0,
                        `createdAt` INTEGER NOT NULL,
                        `updatedAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                """.trimIndent())
            }
        }

        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("PlantSeedsDatabase", "Utför migrering från version 7 till 8")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `tags` TEXT")
            }
        }

        val MIGRATION_8_9 = object : Migration(8, 9) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("PlantSeedsDatabase", "Utför migrering från version 8 till 9")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `notes` TEXT")
            }
        }

        val MIGRATION_9_10 = object : Migration(9, 10) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("PlantSeedsDatabase", "Utför migrering från version 9 till 10")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `lastPlanted` INTEGER")
            }
        }

        val MIGRATION_10_11 = object : Migration(10, 11) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("PlantSeedsDatabase", "Utför migrering från version 10 till 11")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `lastHarvested` INTEGER")
            }
        }

        val MIGRATION_11_12 = object : Migration(11, 12) {
            override fun migrate(database: SupportSQLiteDatabase) {
                Log.d("PlantSeedsDatabase", "Utför migrering från version 11 till 12")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `plantSpacing` REAL")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `rowSpacing` REAL")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `plantingDates` TEXT")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `maintenanceDates` TEXT")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `fertilizingSchedule` TEXT")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `pruningSchedule` TEXT")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `height` REAL")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `spread` REAL")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `yield` TEXT")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `culinaryUses` TEXT")
                database.execSQL("ALTER TABLE seeds ADD COLUMN `medicinalUses` TEXT")
            }
        }

        val MIGRATIONS = arrayOf<Migration>(
            MIGRATION_6_7,
            MIGRATION_7_8,
            MIGRATION_8_9,
            MIGRATION_9_10,
            MIGRATION_10_11,
            MIGRATION_11_12
        )
    }
} 