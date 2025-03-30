package com.bps.plantseeds3.seeds.data

import com.bps.plantseeds3.common.util.Result
import com.bps.plantseeds3.seeds.model.Seed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(
    private val seedDao: SeedDao
) : SeedRepository {
    override fun getAllSeeds(): Flow<List<Seed>> = seedDao.getAllSeeds()

    override fun getFavoriteSeeds(): Flow<List<Seed>> = seedDao.getFavoriteSeeds()

    override fun getSeedById(id: Long): Flow<Seed?> = seedDao.getSeedById(id)

    override suspend fun insertSeed(seed: Seed): Result<Unit> = try {
        seedDao.insertSeed(seed)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun updateSeed(seed: Seed): Result<Unit> = try {
        seedDao.updateSeed(seed)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun deleteSeed(seed: Seed): Result<Unit> = try {
        seedDao.deleteSeed(seed)
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    override suspend fun toggleFavorite(seedId: Long): Result<Unit> = try {
        val seed = seedDao.getSeedByIdSync(seedId)
        seed?.let {
            it.isFavorite = !it.isFavorite
            seedDao.updateSeed(it)
        }
        Result.Success(Unit)
    } catch (e: Exception) {
        Result.Error(e)
    }

    override fun searchSeeds(query: String): Flow<List<Seed>> = flow {
        emit(seedDao.searchSeeds("%$query%"))
    }
} 