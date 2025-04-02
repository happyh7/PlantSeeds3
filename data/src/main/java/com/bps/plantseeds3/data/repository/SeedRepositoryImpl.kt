package com.bps.plantseeds3.data.repository

import android.util.Log
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

private const val TAG = "SeedRepositoryImpl"

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
                Resource.Success(seedEntity.toSeed())
            } else {
                Resource.Error(DatabaseException.EntityNotFoundException("Seed", id).message ?: "Seed hittades inte")
            }
        } catch (e: Exception) {
            Resource.Error(DatabaseException.QueryFailedException("Kunde inte hämta seed", e).message ?: "Ett fel uppstod")
        }
    }

    override suspend fun insertSeed(seed: Seed): Resource<Unit> {
        Log.d(TAG, "insertSeed: Inserting seed = $seed")
        return try {
            val seedEntity = seed.toEntity()
            Log.d(TAG, "insertSeed: Converted to entity = $seedEntity")
            seedDao.insertSeed(seedEntity)
            Log.d(TAG, "insertSeed: Successfully inserted seed")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "insertSeed: Failed to insert seed", e)
            Resource.Error(DatabaseException.InsertionFailedException("Frö", e).message ?: "Ett fel uppstod")
        }
    }

    override suspend fun updateSeed(seed: Seed): Resource<Unit> {
        return try {
            seedDao.updateSeed(seed.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(DatabaseException.UpdateFailedException("Kunde inte uppdatera seed", e).message ?: "Ett fel uppstod")
        }
    }

    override suspend fun deleteSeed(id: String): Resource<Unit> {
        return try {
            seedDao.deleteSeedById(id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(DatabaseException.DeletionFailedException("Frö", id, e).message ?: "Ett fel uppstod")
        }
    }

    override suspend fun searchSeeds(query: String): Resource<List<Seed>> {
        return try {
            val seeds = seedDao.getAllSeeds().first().map { it.toSeed() }
            val filteredSeeds = seeds.filter { seed -> 
                seed.name.contains(query, ignoreCase = true) || 
                seed.description?.contains(query, ignoreCase = true) == true
            }
            Resource.Success(filteredSeeds)
        } catch (e: Exception) {
            Resource.Error(DatabaseException.QueryFailedException("söka efter frön", e).message ?: "Ett fel uppstod")
        }
    }
} 