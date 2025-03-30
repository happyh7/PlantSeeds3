package com.bps.plantseeds3.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bps.plantseeds3.common.data.Converters
import com.bps.plantseeds3.seeds.data.SeedDao
import com.bps.plantseeds3.seeds.model.Seed

@Database(
    entities = [
        Seed::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun seedDao(): SeedDao
} 