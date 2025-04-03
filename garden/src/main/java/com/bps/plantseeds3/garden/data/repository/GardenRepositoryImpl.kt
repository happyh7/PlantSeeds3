package com.bps.plantseeds3.garden.data.repository

import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.garden.domain.repository.GardenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GardenRepositoryImpl @Inject constructor() : GardenRepository {
    
    private val gardens = MutableStateFlow<List<Garden>>(emptyList())
    
    override fun getGardens(): Flow<List<Garden>> = gardens
    
    override suspend fun getGardenById(id: String): Garden? {
        return gardens.value.find { it.id == id }
    }
    
    override suspend fun insertGarden(garden: Garden) {
        gardens.update { currentGardens ->
            currentGardens + garden
        }
    }
    
    override suspend fun updateGarden(garden: Garden) {
        gardens.update { currentGardens ->
            currentGardens.map { if (it.id == garden.id) garden else it }
        }
    }
    
    override suspend fun deleteGarden(garden: Garden) {
        gardens.update { currentGardens ->
            currentGardens.filter { it.id != garden.id }
        }
    }
    
    override suspend fun addPlantToGarden(gardenId: String, plantId: String) {
        gardens.update { currentGardens ->
            currentGardens.map { garden ->
                if (garden.id == gardenId) {
                    garden.copy(plants = garden.plants + plantId)
                } else {
                    garden
                }
            }
        }
    }
    
    override suspend fun removePlantFromGarden(gardenId: String, plantId: String) {
        gardens.update { currentGardens ->
            currentGardens.map { garden ->
                if (garden.id == gardenId) {
                    garden.copy(plants = garden.plants - plantId)
                } else {
                    garden
                }
            }
        }
    }
    
    override fun getGardenWithPlants(gardenId: String): Flow<Garden> {
        TODO("Implementera när Plant-entiteten är klar")
    }
} 