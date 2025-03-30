package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.mapper.GardenMapper
import com.bps.plantseeds3.domain.model.Garden
import com.bps.plantseeds3.domain.repository.GardenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GardenRepositoryImpl @Inject constructor(
    private val dao: GardenDao,
    private val mapper: GardenMapper
) : GardenRepository {

    override suspend fun insertGarden(garden: Garden): Result<Unit> {
        Log.d("GardenRepositoryImpl", "Försöker lägga till trädgård: ${garden.name}")
        return try {
            dao.insertGarden(mapper.toEntity(garden))
            Log.d("GardenRepositoryImpl", "Trädgård tillagd: ${garden.name}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Fel vid tillägg av trädgård: ${garden.name}", e)
            Result.failure(e)
        }
    }

    override suspend fun updateGarden(garden: Garden): Result<Unit> {
        Log.d("GardenRepositoryImpl", "Försöker uppdatera trädgård: ${garden.name}")
        return try {
            dao.updateGarden(mapper.toEntity(garden))
            Log.d("GardenRepositoryImpl", "Trädgård uppdaterad: ${garden.name}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Fel vid uppdatering av trädgård: ${garden.name}", e)
            Result.failure(e)
        }
    }

    override suspend fun deleteGarden(garden: Garden): Result<Unit> {
        Log.d("GardenRepositoryImpl", "Försöker ta bort trädgård: ${garden.name}")
        return try {
            dao.deleteGarden(mapper.toEntity(garden))
            Log.d("GardenRepositoryImpl", "Trädgård borttagen: ${garden.name}")
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Fel vid borttagning av trädgård: ${garden.name}", e)
            Result.failure(e)
        }
    }

    override fun getGardenById(id: Long): Flow<Garden?> {
        Log.d("GardenRepositoryImpl", "Hämtar trädgård med id: $id")
        return dao.getGardenById(id).map { garden ->
            garden?.let { 
                mapper.toDomain(it).also { 
                    Log.d("GardenRepositoryImpl", "Trädgård hittad: ${it.name}")
                }
            }.also { 
                if (it == null) Log.d("GardenRepositoryImpl", "Ingen trädgård hittad med id: $id")
            }
        }
    }

    override fun getAllGardens(): Flow<List<Garden>> {
        return dao.getAllGardens().map { gardens -> gardens.map { mapper.toDomain(it) } }
    }

    override fun getGardenByName(name: String): Flow<Garden?> {
        Log.d("GardenRepositoryImpl", "Hämtar trädgård med namn: $name")
        return dao.getGardenByName(name).map { garden ->
            garden?.let { 
                mapper.toDomain(it).also { 
                    Log.d("GardenRepositoryImpl", "Trädgård hittad: ${it.name}")
                }
            }.also { 
                if (it == null) Log.d("GardenRepositoryImpl", "Ingen trädgård hittad med namn: $name")
            }
        }
    }

    override fun searchGardens(query: String): Flow<List<Garden>> {
        Log.d("GardenRepositoryImpl", "Söker trädgårdar med sökord: $query")
        return dao.searchGardens("%$query%").map { gardens -> gardens.map { mapper.toDomain(it) } }
    }
} 