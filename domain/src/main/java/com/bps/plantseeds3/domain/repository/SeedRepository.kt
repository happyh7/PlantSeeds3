package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Seed
import kotlinx.coroutines.flow.Flow

interface SeedRepository {
    fun getSeeds(): Flow<Resource<List<Seed>>>
    suspend fun getSeedById(id: String): Resource<Seed>
    suspend fun insertSeed(seed: Seed): Resource<Unit>
    suspend fun updateSeed(seed: Seed): Resource<Unit>
    suspend fun deleteSeed(id: String): Resource<Unit>
    suspend fun searchSeeds(query: String): Resource<List<Seed>>
} 