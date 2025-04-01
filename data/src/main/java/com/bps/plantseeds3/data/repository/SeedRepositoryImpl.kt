package com.bps.plantseeds3.data.repository

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.mapper.toSeed
import com.bps.plantseeds3.data.mapper.toEntity
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import com.bps.plantseeds3.common.exceptions.DatabaseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SeedRepositoryImpl @Inject constructor(
    private val seedDao: SeedDao
) : SeedRepository {
    override fun getSeeds(): Flow<Resource<List<Seed>>> {
        return seedDao.getAllSeeds().map { seeds ->
            try {
                Resource.Success(seeds.map { it.toSeed() })
            } catch (e: Exception) {
                Resource.Error(DatabaseException.QueryFailedException("hämta alla frön", e).message ?: "Ett fel uppstod")
            }
        }
    }

    override suspend fun getSeedById(id: String): Resource<Seed> {
        return try {
            val seedEntity = seedDao.getSeedById(id)
            if (seedEntity != null) {
                Resource.Success(seedDao.getSeedById(id.toLong())?.toSeed() ?: throw IllegalStateException("Seed entity found but no corresponding seed entity"))
            } else {
                Resource.Error(DatabaseException.EntityNotFoundException("Seed med ID $id hittades inte"))
            }
        } catch (e: Exception) {
            Resource.Error(DatabaseException.QueryFailedException("Kunde inte hämta seed: ${e.message}"))
        }
    }

    override suspend fun insertSeed(seed: Seed): Resource<Unit> {
        return try {
            seedDao.insertSeed(seed.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(DatabaseException.InsertionFailedException("Frö", e).message ?: "Ett fel uppstod")
        }
    }

    override suspend fun updateSeed(seed: Seed): Resource<Unit> {
        return try {
            seedDao.updateSeed(seed.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(DatabaseException.UpdateFailedException("Frö", seed.id, e).message ?: "Ett fel uppstod")
        }
    }

    override suspend fun deleteSeed(id: String): Resource<Unit> {
        return try {
            val seed = seedDao.getSeedById(id.toLong())
            if (seed != null) {
                seedDao.deleteSeed(seed)
                Resource.Success(Unit)
            } else {
                Resource.Error(DatabaseException.EntityNotFoundException("Frö", id).message ?: "Frö hittades inte")
            }
        } catch (e: Exception) {
            Resource.Error(DatabaseException.DeletionFailedException("Frö", id, e).message ?: "Ett fel uppstod")
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
            Resource.Error(DatabaseException.QueryFailedException("söka efter frön", e).message ?: "Ett fel uppstod")
        }
    }
} 