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

    override suspend fun insertGarden(garden: Garden) {
        Log.d("GardenRepositoryImpl", "Försöker lägga till trädgård: ${garden.name}")
        try {
            dao.insertGarden(mapper.toEntity(garden))
            Log.d("GardenRepositoryImpl", "Trädgård tillagd: ${garden.name}")
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Fel vid tillägg av trädgård: ${garden.name}", e)
            throw e
        }
    }

    override suspend fun updateGarden(garden: Garden) {
        Log.d("GardenRepositoryImpl", "Försöker uppdatera trädgård: ${garden.name}")
        try {
            dao.updateGarden(mapper.toEntity(garden))
            Log.d("GardenRepositoryImpl", "Trädgård uppdaterad: ${garden.name}")
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Fel vid uppdatering av trädgård: ${garden.name}", e)
            throw e
        }
    }

    override suspend fun deleteGarden(garden: Garden) {
        Log.d("GardenRepositoryImpl", "Försöker ta bort trädgård: ${garden.name}")
        try {
            dao.deleteGarden(mapper.toEntity(garden))
            Log.d("GardenRepositoryImpl", "Trädgård borttagen: ${garden.name}")
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Fel vid borttagning av trädgård: ${garden.name}", e)
            throw e
        }
    }

    override suspend fun getGardenById(id: String): Garden? {
        return try {
            Log.d("GardenRepositoryImpl", "Fetching garden with id: $id")
            val garden = dao.getGardenById(id)?.let { mapper.toDomain(it) }
            if (garden != null) {
                Log.d("GardenRepositoryImpl", "Garden found: ${garden.name}")
            } else {
                Log.d("GardenRepositoryImpl", "No garden found with id: $id")
            }
            garden
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Error fetching garden: ${e.message}")
            throw e
        }
    }

    override fun getAllGardens(): Flow<List<Garden>> {
        return dao.getAllGardens().map { gardens -> gardens.map { mapper.toDomain(it) } }
    }

    override suspend fun getGardenByName(name: String): Garden? {
        return try {
            Log.d("GardenRepositoryImpl", "Fetching garden with name: $name")
            val garden = dao.getGardenByName(name)?.let { mapper.toDomain(it) }
            if (garden != null) {
                Log.d("GardenRepositoryImpl", "Garden found: ${garden.name}")
            } else {
                Log.d("GardenRepositoryImpl", "No garden found with name: $name")
            }
            garden
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Error fetching garden by name: ${e.message}")
            throw e
        }
    }

    override fun searchGardens(query: String): Flow<List<Garden>> {
        Log.d("GardenRepositoryImpl", "Searching gardens with query: $query")
        return dao.searchGardens("%$query%").map { gardens -> gardens.map { mapper.toDomain(it) } }
    }
} 