package com.bps.plantseeds3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.dao.PlantingDao
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.local.entity.GardenEntity
import com.bps.plantseeds3.data.local.entity.PlantEntity
import com.bps.plantseeds3.data.local.entity.PlantingEntity
import com.bps.plantseeds3.data.local.entity.SeedEntity
import com.bps.plantseeds3.data.local.converter.Converters
import com.bps.plantseeds3.data.local.migration.DatabaseMigrations

@Database(
    entities = [
        GardenEntity::class,
        PlantEntity::class,
        PlantingEntity::class,
        SeedEntity::class
    ],
    version = 14,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class PlantSeedsDatabase : RoomDatabase() {
    abstract fun gardenDao(): GardenDao
    abstract fun plantDao(): PlantDao
    abstract fun plantingDao(): PlantingDao
    abstract fun seedDao(): SeedDao

    companion object {
        const val DATABASE_NAME = "plantseeds.db"

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE gardens ADD COLUMN plants TEXT NOT NULL DEFAULT ''")
            }
        }

        fun getMigrations(): Array<Migration> {
            return arrayOf(MIGRATION_1_2)
        }
    }
} 