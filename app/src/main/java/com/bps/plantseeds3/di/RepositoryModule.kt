package com.bps.plantseeds3.di

import com.bps.plantseeds3.data.repository.GardenRepositoryImpl
import com.bps.plantseeds3.data.repository.PlantRepositoryImpl
import com.bps.plantseeds3.data.repository.PlantingRepositoryImpl
import com.bps.plantseeds3.data.repository.SeedRepositoryImpl
import com.bps.plantseeds3.domain.repository.GardenRepository
import com.bps.plantseeds3.domain.repository.PlantRepository
import com.bps.plantseeds3.domain.repository.PlantingRepository
import com.bps.plantseeds3.domain.repository.SeedRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    
    @Binds
    @Singleton
    abstract fun bindSeedRepository(
        seedRepositoryImpl: SeedRepositoryImpl
    ): SeedRepository

    @Binds
    @Singleton
    abstract fun bindGardenRepository(
        gardenRepositoryImpl: GardenRepositoryImpl
    ): GardenRepository

    @Binds
    @Singleton
    abstract fun bindPlantRepository(
        plantRepositoryImpl: PlantRepositoryImpl
    ): PlantRepository

    @Binds
    @Singleton
    abstract fun bindPlantingRepository(
        plantingRepositoryImpl: PlantingRepositoryImpl
    ): PlantingRepository
} 