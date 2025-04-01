package com.bps.plantseeds3.data.repository

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.Instant
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor() : PlantRepository {
    // Temporär implementation med hårdkodad data
    private val mockPlants = listOf(
        Plant(
            id = "1",
            name = "Monstera",
            species = "Monstera deliciosa",
            description = "En stor och vacker växt med karakteristiska blad",
            gardenId = null,
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
            gardenId = null,
            lastWatered = Instant.now(),
            nextWatering = Instant.now().plusSeconds(60 * 60 * 24 * 5), // 5 dagar
            createdAt = Instant.now(),
            updatedAt = Instant.now()
        )
    )

    override fun getPlants(): Flow<Resource<List<Plant>>> = flow {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(mockPlants))
        } catch (e: Exception) {
            emit(Resource.Error("Kunde inte hämta växterna: ${e.message}"))
        }
    }

    override suspend fun getPlantById(id: String): Resource<Plant> {
        return try {
            val plant = mockPlants.find { it.id == id }
            if (plant != null) {
                Resource.Success(plant)
            } else {
                Resource.Error("Kunde inte hitta växten")
            }
        } catch (e: Exception) {
            Resource.Error("Ett fel uppstod: ${e.message}")
        }
    }

    override suspend fun insertPlant(plant: Plant): Resource<Unit> {
        return try {
            // TODO: Implementera faktisk databaslagring
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Kunde inte lägga till växten: ${e.message}")
        }
    }

    override suspend fun updatePlant(plant: Plant): Resource<Unit> {
        return try {
            // TODO: Implementera faktisk databasuppdatering
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Kunde inte uppdatera växten: ${e.message}")
        }
    }

    override suspend fun deletePlant(id: String): Resource<Unit> {
        return try {
            // TODO: Implementera faktisk databasradering
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Kunde inte ta bort växten: ${e.message}")
        }
    }

    override suspend fun searchPlants(query: String): Resource<List<Plant>> {
        return try {
            val filteredPlants = mockPlants.filter { 
                it.name.contains(query, ignoreCase = true) || 
                it.species.contains(query, ignoreCase = true)
            }
            Resource.Success(filteredPlants)
        } catch (e: Exception) {
            Resource.Error("Kunde inte söka efter växter: ${e.message}")
        }
    }
} 