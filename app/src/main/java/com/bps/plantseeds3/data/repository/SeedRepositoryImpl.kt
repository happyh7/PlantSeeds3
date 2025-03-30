package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.data.mapper.SeedMapper
import com.bps.plantseeds3.domain.model.Seed as SeedDomain
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.repository.SeedRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Implementation av SeedRepository som hanterar frödata med Room-databas.
 */
class SeedRepositoryImpl @Inject constructor(
    private val seedDao: SeedDao,
    private val seedMapper: SeedMapper
) : BaseRepository<Seed, SeedDomain>(), SeedRepository {

    override val TAG = "SeedRepositoryImpl"
    override val dao = seedDao
    override val mapper = seedMapper

    override suspend fun insertSeed(seed: SeedDomain): Result<Unit> {
        Log.d(TAG, "Infogar frö: ${seed.name}")
        return try {
            dao.insertSeed(mapper.toEntity(seed))
            Log.d(TAG, "Frö infogat: ${seed.name}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid infogning av frö: ${seed.name}", e)
            Result.failure(e)
        }
    }

    override suspend fun updateSeed(seed: SeedDomain): Result<Unit> {
        Log.d(TAG, "Uppdaterar frö: ${seed.name}")
        return try {
            dao.updateSeed(mapper.toEntity(seed))
            Log.d(TAG, "Frö uppdaterat: ${seed.name}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid uppdatering av frö: ${seed.name}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteSeed(seed: SeedDomain): Result<Unit> {
        Log.d(TAG, "Tar bort frö: ${seed.name}")
        return try {
            dao.deleteSeed(mapper.toEntity(seed))
            Log.d(TAG, "Frö borttaget: ${seed.name}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid borttagning av frö: ${seed.name}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteAllSeeds() {
        Log.d(TAG, "Tar bort alla frön")
        dao.deleteAllSeeds()
        Log.d(TAG, "Alla frön borttagna")
    }

    override fun getSeedById(id: Long): Flow<SeedDomain?> {
        Log.d(TAG, "Hämtar frö med ID: $id")
        return dao.getSeedById(id).map { seed ->
            seed?.let { 
                mapper.toDomain(it).also {
                    Log.d(TAG, "Hämtat frö: ${it.name}")
                }
            }.also {
                if (it == null) Log.d(TAG, "Inget frö hittat med ID: $id")
            }
        }
    }

    override fun getAllSeeds(): Flow<List<SeedDomain>> {
        Log.d(TAG, "Hämtar alla frön från databasen")
        return dao.getAllSeeds().map { seeds ->
            Log.d(TAG, "Hämtade ${seeds.size} frön från databasen")
            if (seeds.isEmpty()) {
                Log.w(TAG, "Inga frön hittades i databasen")
            }
            seeds.map { seed ->
                val mappedSeed = mapper.toDomain(seed)
                Log.d(TAG, "Mappade frö: ${mappedSeed.name} (ID: ${mappedSeed.id}, Kategori: ${mappedSeed.category})")
                mappedSeed
            }
        }
    }

    override fun getFavoriteSeeds(): Flow<List<SeedDomain>> {
        Log.d(TAG, "Hämtar favoritfrön")
        return dao.getFavoriteSeeds().map { seeds ->
            Log.d(TAG, "Hämtat ${seeds.size} favoritfrön")
            seeds.map { seed ->
                val mappedSeed = mapper.toDomain(seed)
                Log.d(TAG, "Mappade favoritfrö: ${mappedSeed.name}")
                mappedSeed
            }
        }
    }

    override fun searchSeeds(query: String): Flow<List<SeedDomain>> {
        Log.d(TAG, "Söker efter frön med query: $query")
        return dao.searchSeeds(query).map { seeds ->
            Log.d(TAG, "Hittade ${seeds.size} frön som matchar sökningen")
            seeds.map { seed ->
                val mappedSeed = mapper.toDomain(seed)
                Log.d(TAG, "Mappade sökresultat: ${mappedSeed.name}")
                mappedSeed
            }
        }
    }

    override fun getSeedsByCategory(category: PlantCategory): Flow<List<SeedDomain>> {
        Log.d(TAG, "Hämtar frön i kategori: ${category.name}")
        return dao.getSeedsByCategory(category.name).map { seeds ->
            Log.d(TAG, "Hämtat ${seeds.size} frön i kategori: ${category.name}")
            seeds.map { seed ->
                val mappedSeed = mapper.toDomain(seed)
                Log.d(TAG, "Mappade frö: ${mappedSeed.name}")
                mappedSeed
            }
        }
    }

    override fun getSeedsByLifespan(lifespan: String): Flow<List<SeedDomain>> {
        Log.d(TAG, "Hämtar frön med livslängd: $lifespan")
        return dao.getSeedsByLifespan(lifespan).map { seeds ->
            Log.d(TAG, "Hämtat ${seeds.size} frön med livslängd: $lifespan")
            seeds.map { seed ->
                val mappedSeed = mapper.toDomain(seed)
                Log.d(TAG, "Mappade frö: ${mappedSeed.name}")
                mappedSeed
            }
        }
    }

    override fun getSeedsByHardinessZone(zone: String): Flow<List<SeedDomain>> {
        Log.d(TAG, "Hämtar frön för hårdhetszon: $zone")
        return dao.getSeedsByHardinessZone(zone).map { seeds ->
            Log.d(TAG, "Hämtat ${seeds.size} frön för hårdhetszon: $zone")
            seeds.map { seed ->
                val mappedSeed = mapper.toDomain(seed)
                Log.d(TAG, "Mappade frö: ${mappedSeed.name}")
                mappedSeed
            }
        }
    }

    override fun getDistinctCategories(): Flow<List<String>> {
        Log.d(TAG, "Hämtar distinkta kategorier")
        return dao.getDistinctCategories()
    }

    override suspend fun updateInvalidCategories() {
        Log.d(TAG, "Uppdaterar ogiltiga kategorier")
        // TODO: Implementera logik för att uppdatera ogiltiga kategorier
        Log.d(TAG, "Uppdatering av ogiltiga kategorier slutförd")
    }

    override suspend fun toggleFavorite(id: Long): Result<Unit> {
        Log.d(TAG, "Växlar favoritstatus för frö med ID: $id")
        return try {
            val seed = getSeedById(id).first() ?: return Result.failure(Exception("Frö hittades inte"))
            val updatedSeed = seed.copy(isFavorite = !seed.isFavorite)
            updateSeed(updatedSeed)
            Log.d(TAG, "Favoritstatus växlad för frö: ${seed.name}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "Fel vid växling av favoritstatus för frö med ID: $id", e)
            Result.failure(e)
        }
    }
} 