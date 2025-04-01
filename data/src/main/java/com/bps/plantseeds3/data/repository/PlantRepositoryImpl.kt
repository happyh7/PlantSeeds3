package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.mapper.toEntity
import com.bps.plantseeds3.data.local.mapper.toPlant
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val TAG = "PlantRepositoryImpl"

class PlantRepositoryImpl @Inject constructor(
    private val plantDao: PlantDao
) : PlantRepository {

    override fun getPlants(): Flow<Resource<List<Plant>>> = flow {
        Log.d(TAG, "getPlants: Starting to load all plants")
        emit(Resource.Loading())
        try {
            plantDao.getAllPlants()
                .map { entities -> 
                    Log.d(TAG, "getPlants: Found ${entities.size} plants in database")
                    entities.map { it.toPlant() }
                }
                .collect { plants ->
                    Log.d(TAG, "getPlants: Converted to ${plants.size} Plant objects")
                    emit(Resource.Success(plants))
                }
        } catch (e: Exception) {
            Log.e(TAG, "getPlants: Error loading plants", e)
            emit(Resource.Error("Kunde inte hämta växterna: ${e.message}"))
        }
    }

    override fun getPlantsByGardenId(gardenId: String): Flow<Resource<List<Plant>>> = flow {
        Log.d(TAG, "getPlantsByGardenId: Starting to load plants for garden $gardenId")
        emit(Resource.Loading())
        try {
            plantDao.getPlantsByGardenId(gardenId)
                .map { entities ->
                    Log.d(TAG, "getPlantsByGardenId: Found ${entities.size} plants for garden $gardenId")
                    entities.map { it.toPlant() }
                }
                .collect { plants ->
                    Log.d(TAG, "getPlantsByGardenId: Converted to ${plants.size} Plant objects")
                    emit(Resource.Success(plants))
                }
        } catch (e: Exception) {
            Log.e(TAG, "getPlantsByGardenId: Error loading plants for garden $gardenId", e)
            emit(Resource.Error("Kunde inte hämta växterna för trädgården: ${e.message}"))
        }
    }

    override suspend fun getPlantById(id: String): Resource<Plant> {
        Log.d(TAG, "getPlantById: Loading plant with id = $id")
        return try {
            val plant = plantDao.getPlantById(id)?.toPlant()
            if (plant != null) {
                Log.d(TAG, "getPlantById: Found plant = $plant")
                Resource.Success(plant)
            } else {
                Log.d(TAG, "getPlantById: Plant not found")
                Resource.Error("Växten hittades inte")
            }
        } catch (e: Exception) {
            Log.e(TAG, "getPlantById: Error", e)
            Resource.Error("Kunde inte hämta växten: ${e.message}")
        }
    }

    override suspend fun insertPlant(plant: Plant): Resource<Unit> {
        Log.d(TAG, "insertPlant: Inserting plant = $plant")
        return try {
            plantDao.insertPlant(plant.toEntity())
            Log.d(TAG, "insertPlant: Successfully inserted plant")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "insertPlant: Error", e)
            Resource.Error("Kunde inte lägga till växten: ${e.message}")
        }
    }

    override suspend fun updatePlant(plant: Plant): Resource<Unit> {
        Log.d(TAG, "updatePlant: Updating plant = $plant")
        return try {
            plantDao.updatePlant(plant.toEntity())
            Log.d(TAG, "updatePlant: Successfully updated plant")
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updatePlant: Error", e)
            Resource.Error("Kunde inte uppdatera växten: ${e.message}")
        }
    }

    override suspend fun deletePlant(id: String): Resource<Unit> {
        Log.d(TAG, "deletePlant: Deleting plant with id = $id")
        return try {
            val plant = plantDao.getPlantById(id)
            if (plant != null) {
                plantDao.deletePlant(plant)
                Log.d(TAG, "deletePlant: Successfully deleted plant")
                Resource.Success(Unit)
            } else {
                Log.d(TAG, "deletePlant: Plant not found")
                Resource.Error("Växten hittades inte")
            }
        } catch (e: Exception) {
            Log.e(TAG, "deletePlant: Error", e)
            Resource.Error("Kunde inte ta bort växten: ${e.message}")
        }
    }

    override suspend fun searchPlants(query: String): Resource<List<Plant>> {
        Log.d(TAG, "searchPlants: Searching for plants with query = $query")
        return try {
            val plants = plantDao.searchPlants(query).map { it.toPlant() }
            Log.d(TAG, "searchPlants: Found ${plants.size} plants")
            Resource.Success(plants)
        } catch (e: Exception) {
            Log.e(TAG, "searchPlants: Error", e)
            Resource.Error("Kunde inte söka efter växter: ${e.message}")
        }
    }
} 