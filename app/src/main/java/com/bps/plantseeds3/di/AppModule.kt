package com.bps.plantseeds3.di

import android.content.Context
import androidx.room.Room
import com.bps.plantseeds3.data.local.PlantSeedsDatabase
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.dao.PlantDao
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

    @Provides
    @Singleton
    fun providePlantSeedsDatabase(
        @ApplicationContext context: Context
    ): PlantSeedsDatabase {
        return Room.databaseBuilder(
            context,
            PlantSeedsDatabase::class.java,
            PlantSeedsDatabase.DATABASE_NAME
        )
        .addMigrations(*PlantSeedsDatabase.MIGRATIONS)
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    @Singleton
    fun provideGardenDao(database: PlantSeedsDatabase): GardenDao {
        return database.gardenDao()
    }

    @Provides
    @Singleton
    fun providePlantDao(database: PlantSeedsDatabase): PlantDao {
        return database.plantDao()
    }

    @Provides
    @Singleton
    fun provideSeedDao(database: PlantSeedsDatabase): SeedDao {
        return database.seedDao()
    }
} 