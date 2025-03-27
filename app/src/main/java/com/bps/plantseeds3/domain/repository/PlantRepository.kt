package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.data.local.entity.Plant
import com.bps.plantseeds3.data.local.entity.PlantStatus
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getAllPlants(): Flow<List<Plant>>
    
    suspend fun getPlantById(id: String): Plant?
    
    suspend fun insertPlant(plant: Plant)
    
    suspend fun updatePlant(plant: Plant)
    
    suspend fun deletePlant(plant: Plant)
    
    fun getPlantsByGardenId(gardenId: String): Flow<List<Plant>>
} 