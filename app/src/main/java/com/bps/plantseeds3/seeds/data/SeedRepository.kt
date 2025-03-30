package com.bps.plantseeds3.seeds.data

import com.bps.plantseeds3.common.util.Result
import com.bps.plantseeds3.seeds.model.Seed
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
} 