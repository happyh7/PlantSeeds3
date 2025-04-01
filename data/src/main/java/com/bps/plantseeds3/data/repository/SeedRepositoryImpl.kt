package com.bps.plantseeds3.data.repository

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.mapper.toSeed
import com.bps.plantseeds3.data.mapper.toEntity
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(
    private val seedDao: SeedDao
) : SeedRepository {
    override fun getSeeds(): Flow<Resource<List<Seed>>> {
        return seedDao.getAllSeeds().map { seeds ->
            Resource.Success(seeds.map { it.toSeed() })
        }
    }

    override suspend fun getSeedById(id: String): Resource<Seed> {
        return try {
            val seed = seedDao.getSeedById(id.toLong())?.toSeed()
            if (seed != null) {
                Resource.Success(seed)
            } else {
                Resource.Error("Frö hittades inte")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod")
        }
    }

    override suspend fun insertSeed(seed: Seed): Resource<Unit> {
        return try {
            seedDao.insertSeed(seed.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod vid insättning av frö")
        }
    }

    override suspend fun updateSeed(seed: Seed): Resource<Unit> {
        return try {
            seedDao.updateSeed(seed.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod vid uppdatering av frö")
        }
    }

    override suspend fun deleteSeed(id: String): Resource<Unit> {
        return try {
            val seed = seedDao.getSeedById(id.toLong())
            if (seed != null) {
                seedDao.deleteSeed(seed)
                Resource.Success(Unit)
            } else {
                Resource.Error("Frö hittades inte")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod vid borttagning av frö")
        }
    }

    override suspend fun searchSeeds(query: String): Resource<List<Seed>> {
        return try {
            val seeds = seedDao.getAllSeeds().first().map { it.toSeed() }
            val filteredSeeds = seeds.filter { seed -> 
                seed.name?.contains(query, ignoreCase = true) == true || 
                seed.description?.contains(query, ignoreCase = true) == true
            }
            Resource.Success(filteredSeeds)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod vid sökning av frön")
        }
    }
} 