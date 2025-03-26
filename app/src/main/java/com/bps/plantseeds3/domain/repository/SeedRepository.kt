package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.data.local.entity.Seed
import kotlinx.coroutines.flow.Flow

interface SeedRepository {
    suspend fun insertSeed(seed: Seed)
    suspend fun updateSeed(seed: Seed)
    suspend fun deleteSeed(seed: Seed)
    suspend fun getSeedById(id: String): Seed?
    fun getAllSeeds(): Flow<List<Seed>>
    fun searchSeeds(query: String): Flow<List<Seed>>
} 