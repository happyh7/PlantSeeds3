package com.bps.plantseeds3.data.di

import android.content.Context
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
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): PlantSeedsDatabase {
        return Room.databaseBuilder(
            context,
            PlantSeedsDatabase::class.java,
            PlantSeedsDatabase.DATABASE_NAME
        )
        .addMigrations(*PlantSeedsDatabase.getMigrations())
        .build()
    }

    @Provides
    fun provideGardenDao(database: PlantSeedsDatabase): GardenDao {
        return database.gardenDao()
    }

    @Provides
    fun providePlantDao(database: PlantSeedsDatabase): PlantDao {
        return database.plantDao()
    }

    @Provides
    fun providePlantingDao(database: PlantSeedsDatabase): PlantingDao {
        return database.plantingDao()
    }

    @Provides
    fun provideSeedDao(database: PlantSeedsDatabase): SeedDao {
        return database.seedDao()
    }
} 