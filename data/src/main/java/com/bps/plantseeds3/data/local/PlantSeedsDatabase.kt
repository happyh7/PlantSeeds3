package com.bps.plantseeds3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.dao.PlantingDao
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.local.entity.GardenEntity
import com.bps.plantseeds3.data.local.entity.PlantEntity
import com.bps.plantseeds3.data.local.entity.PlantingEntity
import com.bps.plantseeds3.data.local.entity.SeedEntity
import com.bps.plantseeds3.data.local.converter.DateConverter

@Database(
    entities = [
        GardenEntity::class,
        PlantEntity::class,
        PlantingEntity::class,
        SeedEntity::class
    ],
    version = 13,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
abstract class PlantSeedsDatabase : RoomDatabase() {
    abstract fun gardenDao(): GardenDao
    abstract fun plantDao(): PlantDao
    abstract fun plantingDao(): PlantingDao
    abstract fun seedDao(): SeedDao

    companion object {
        // Migrationer kommer att läggas till här senare
    }
} 