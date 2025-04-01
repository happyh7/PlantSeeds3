package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

private const val TAG = "PlantRepositoryImpl"

class PlantRepositoryImpl @Inject constructor() : PlantRepository {
    // Temporär implementation med hårdkodad data
    private val mockPlants = listOf(
        Plant(
            id = "1",
            name = "Monstera",
            species = "Monstera deliciosa",
            description = "En stor och vacker växt med karakteristiska blad",
            gardenId = "garden_1",
            lastWatered = Instant.now(),
            nextWatering = Instant.now().plusSeconds(60 * 60 * 24 * 7), // 7 dagar
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Plant(
            id = "2",
            name = "Fikus",
            species = "Ficus lyrata",
            description = "En populär inomhusväxt med stora, fiolformade blad",
            gardenId = "garden_1",
            lastWatered = Instant.now(),
            nextWatering = Instant.now().plusSeconds(60 * 60 * 24 * 5), // 5 dagar
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Plant(
            id = "3",
            name = "Tomat",
            species = "Solanum lycopersicum",
            description = "Körsbärstomater för balkongodling",
            gardenId = "garden_2",
            lastWatered = Instant.now(),
            nextWatering = Instant.now().plusSeconds(60 * 60 * 24 * 2), // 2 dagar
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Plant(
            id = "4",
            name = "Basilika",
            species = "Ocimum basilicum",
            description = "Färsk basilika för matlagning",
            gardenId = "garden_2",
            lastWatered = Instant.now(),
            nextWatering = Instant.now().plusSeconds(60 * 60 * 24 * 3), // 3 dagar
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        ),
        Plant(
            id = "5",
            name = "Gurka",
            species = "Cucumis sativus",
            description = "Växthusgurka för sallader",
            gardenId = "garden_3",
            lastWatered = Instant.now(),
            nextWatering = Instant.now().plusSeconds(60 * 60 * 24 * 2), // 2 dagar
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    )

    override fun getPlants(): Flow<Resource<List<Plant>>> = flow {
        Log.d(TAG, "getPlants: Loading all plants")
        emit(Resource.Loading())
        try {
            Log.d(TAG, "getPlants: Returning ${mockPlants.size} plants")
            emit(Resource.Success(mockPlants))
        } catch (e: Exception) {
            Log.e(TAG, "getPlants: Error", e)
            emit(Resource.Error("Kunde inte hämta växterna: ${e.message}"))
        }
    }

    override fun getPlantsByGardenId(gardenId: String): Flow<Resource<List<Plant>>> = flow {
        Log.d(TAG, "getPlantsByGardenId: Starting to load plants for garden $gardenId")
        emit(Resource.Loading())
        try {
            val filteredPlants = mockPlants.filter { it.gardenId == gardenId }
            Log.d(TAG, "getPlantsByGardenId: Found ${filteredPlants.size} plants for garden $gardenId")
            Log.d(TAG, "getPlantsByGardenId: Plants details: ${filteredPlants.map { it.name }}")
            emit(Resource.Success(filteredPlants))
        } catch (e: Exception) {
            Log.e(TAG, "getPlantsByGardenId: Error loading plants for garden $gardenId", e)
            emit(Resource.Error("Kunde inte hämta växterna för trädgården: ${e.message}"))
        }
    }

    override suspend fun getPlantById(id: String): Resource<Plant> {
        Log.d(TAG, "getPlantById: Loading plant with id = $id")
        return try {
            val plant = mockPlants.find { it.id == id }
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
            // TODO: Implementera när vi har en databas
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "insertPlant: Error", e)
            Resource.Error("Kunde inte lägga till växten: ${e.message}")
        }
    }

    override suspend fun updatePlant(plant: Plant): Resource<Unit> {
        Log.d(TAG, "updatePlant: Updating plant = $plant")
        return try {
            // TODO: Implementera när vi har en databas
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "updatePlant: Error", e)
            Resource.Error("Kunde inte uppdatera växten: ${e.message}")
        }
    }

    override suspend fun deletePlant(id: String): Resource<Unit> {
        Log.d(TAG, "deletePlant: Deleting plant with id = $id")
        return try {
            // TODO: Implementera när vi har en databas
            Resource.Success(Unit)
        } catch (e: Exception) {
            Log.e(TAG, "deletePlant: Error", e)
            Resource.Error("Kunde inte ta bort växten: ${e.message}")
        }
    }

    override suspend fun searchPlants(query: String): Resource<List<Plant>> {
        Log.d(TAG, "searchPlants: Searching for query = $query")
        return try {
            val filteredPlants = mockPlants.filter { plant -> 
                plant.name.contains(query, ignoreCase = true) ||
                plant.species.contains(query, ignoreCase = true) ||
                (plant.description?.contains(query, ignoreCase = true) ?: false)
            }
            Log.d(TAG, "searchPlants: Found ${filteredPlants.size} plants")
            Resource.Success(filteredPlants)
        } catch (e: Exception) {
            Log.e(TAG, "searchPlants: Error", e)
            Resource.Error("Kunde inte söka efter växter: ${e.message}")
        }
    }
} 