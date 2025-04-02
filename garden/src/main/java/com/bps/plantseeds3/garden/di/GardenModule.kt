package com.bps.plantseeds3.garden.di

import com.bps.plantseeds3.garden.data.repository.GardenRepositoryImpl
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GardenModule {
    
    @Binds
    @Singleton
    abstract fun bindGardenRepository(
        gardenRepositoryImpl: GardenRepositoryImpl
    ): GardenRepository
} 