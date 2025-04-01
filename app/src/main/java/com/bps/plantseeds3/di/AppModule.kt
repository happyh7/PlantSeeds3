package com.bps.plantseeds3.di

import android.content.Context
import android.util.Log
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.data.repository.GardenRepositoryImpl
import com.bps.plantseeds3.data.repository.SeedRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val TAG = "AppModule"

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGardenRepository(gardenDao: GardenDao): GardenRepository {
        return GardenRepositoryImpl(gardenDao)
    }

    @Provides
    @Singleton
    fun provideSeedRepository(seedDao: SeedDao): SeedRepository {
        return SeedRepositoryImpl(seedDao)
    }
} 