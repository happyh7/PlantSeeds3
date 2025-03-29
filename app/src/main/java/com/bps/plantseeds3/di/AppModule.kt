package com.bps.plantseeds3.di

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.bps.plantseeds3.data.local.PlantSeedsDatabase
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.dao.PlantingDao
import com.bps.plantseeds3.data.local.dao.SeedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val TAG = "AppModule"

    @Provides
    @Singleton
    fun providePlantSeedsDatabase(
        @ApplicationContext context: Context
    ): PlantSeedsDatabase {
        Log.d(TAG, "Skapar PlantSeedsDatabase")
        return Room.databaseBuilder(
            context,
            PlantSeedsDatabase::class.java,
            "plantseeds_v12.db"
        )
        .addMigrations(
            PlantSeedsDatabase.MIGRATION_6_7,
            PlantSeedsDatabase.MIGRATION_7_8,
            PlantSeedsDatabase.MIGRATION_8_9,
            PlantSeedsDatabase.MIGRATION_9_10,
            PlantSeedsDatabase.MIGRATION_10_11,
            PlantSeedsDatabase.MIGRATION_11_12
        )
        .build()
    }

    @Provides
    @Singleton
    fun provideGardenDao(database: PlantSeedsDatabase): GardenDao {
        Log.d(TAG, "Skapar GardenDao")
        return database.gardenDao()
    }

    @Provides
    @Singleton
    fun provideSeedDao(database: PlantSeedsDatabase): SeedDao {
        Log.d(TAG, "Skapar SeedDao")
        return database.seedDao()
    }

    @Provides
    @Singleton
    fun providePlantDao(database: PlantSeedsDatabase): PlantDao {
        Log.d(TAG, "Skapar PlantDao")
        return database.plantDao()
    }

    @Provides
    @Singleton
    fun providePlantingDao(database: PlantSeedsDatabase): PlantingDao {
        Log.d(TAG, "Skapar PlantingDao")
        return database.plantingDao()
    }
} 