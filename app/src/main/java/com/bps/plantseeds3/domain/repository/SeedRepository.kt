package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.domain.model.Seed
import kotlinx.coroutines.flow.Flow

interface SeedRepository {
    suspend fun insertSeed(seed: Seed)
    suspend fun updateSeed(seed: Seed)
    suspend fun deleteSeed(seed: Seed)
    suspend fun deleteAllSeeds()
    suspend fun getSeedById(id: String): Seed?
    fun getAllSeeds(): Flow<List<Seed>>
    fun getFavoriteSeeds(): Flow<List<Seed>>
    fun searchSeeds(query: String): Flow<List<Seed>>
    fun getSeedsByCategory(category: String): Flow<List<Seed>>
    fun getSeedsByLifespan(lifespan: String): Flow<List<Seed>>
    fun getSeedsByHardinessZone(zone: String): Flow<List<Seed>>
    fun getDistinctCategories(): Flow<List<String>>
    suspend fun updateInvalidCategories()
} 