package com.bps.plantseeds3.di

import android.content.Context
import androidx.room.Room
import com.bps.plantseeds3.data.AppDatabase
import com.bps.plantseeds3.seeds.data.SeedDao
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
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "plantseeds.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideSeedDao(database: AppDatabase): SeedDao {
        return database.seedDao()
    }
} 