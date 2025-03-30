package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.domain.model.Seed
import kotlinx.coroutines.flow.Flow

interface SeedRepository {
    fun getAllSeeds(): Flow<List<Seed>>
    fun getFavoriteSeeds(): Flow<List<Seed>>
    fun getSeedById(id: Long): Flow<Seed?>
    suspend fun insertSeed(seed: Seed): Result<Unit>
    suspend fun updateSeed(seed: Seed): Result<Unit>
    suspend fun deleteSeed(seed: Seed): Result<Unit>
    suspend fun toggleFavorite(seedId: Long): Result<Unit>
    fun searchSeeds(query: String): Flow<List<Seed>>
    suspend fun deleteAllSeeds()
    fun getSeedsByCategory(category: String): Flow<List<Seed>>
    fun getSeedsByLifespan(lifespan: String): Flow<List<Seed>>
    fun getSeedsByHardinessZone(zone: String): Flow<List<Seed>>
    fun getDistinctCategories(): Flow<List<String>>
    suspend fun updateInvalidCategories()
} 