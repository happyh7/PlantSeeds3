package com.bps.plantseeds3.data.repository

import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(
    private val dao: SeedDao
) : SeedRepository {

    override suspend fun insertSeed(seed: Seed) {
        dao.insertSeed(seed)
    }

    override suspend fun updateSeed(seed: Seed) {
        dao.updateSeed(seed)
    }

    override suspend fun deleteSeed(seed: Seed) {
        dao.deleteSeed(seed)
    }

    override suspend fun getSeedById(id: String): Seed? {
        return dao.getSeedById(id)
    }

    override fun getAllSeeds(): Flow<List<Seed>> {
        return dao.getAllSeeds()
    }

    override fun searchSeeds(query: String): Flow<List<Seed>> {
        return dao.searchSeeds(query)
    }
} 