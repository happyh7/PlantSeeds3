package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.entity.Garden
import com.bps.plantseeds3.domain.repository.GardenRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class GardenRepositoryImpl @Inject constructor(
    private val dao: GardenDao
) : GardenRepository {

    override suspend fun insertGarden(garden: Garden) {
        try {
            Log.d("GardenRepositoryImpl", "Inserting garden: ${garden.name}")
            dao.insertGarden(garden)
            Log.d("GardenRepositoryImpl", "Garden inserted successfully")
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Error inserting garden: ${e.message}")
            throw e
        }
    }

    override suspend fun updateGarden(garden: Garden) {
        try {
            Log.d("GardenRepositoryImpl", "Updating garden: ${garden.name}")
            dao.updateGarden(garden)
            Log.d("GardenRepositoryImpl", "Garden updated successfully")
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Error updating garden: ${e.message}")
            throw e
        }
    }

    override suspend fun deleteGarden(garden: Garden) {
        try {
            Log.d("GardenRepositoryImpl", "Deleting garden: ${garden.name}")
            dao.deleteGarden(garden)
            Log.d("GardenRepositoryImpl", "Garden deleted successfully")
        } catch (e: Exception) {
            Log.e("GardenRepositoryImpl", "Error deleting garden: ${e.message}")
            throw e
        }
    }

    override suspend fun getGardenById(id: String): Garden? {
        return try {
            Log.d("GardenRepositoryImpl", "Fetching garden with id: $id")
            val garden = dao.getGardenById(id)
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
        return dao.getAllGardens()
    }

    override suspend fun getGardenByName(name: String): Garden? {
        return try {
            Log.d("GardenRepositoryImpl", "Fetching garden with name: $name")
            val garden = dao.getGardenByName(name)
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
        return dao.searchGardens("%$query%")
    }
} 