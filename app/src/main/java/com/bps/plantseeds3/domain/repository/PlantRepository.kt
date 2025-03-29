package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    suspend fun insertPlant(plant: Plant)
    suspend fun updatePlant(plant: Plant)
    suspend fun deletePlant(plant: Plant)
    suspend fun getPlantById(id: String): Plant?
    fun getAllPlants(): Flow<List<Plant>>
    fun getPlantsByGardenId(gardenId: String): Flow<List<Plant>>
} 