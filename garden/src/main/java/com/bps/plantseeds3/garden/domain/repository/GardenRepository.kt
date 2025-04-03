package com.bps.plantseeds3.garden.domain.repository

import com.bps.plantseeds3.garden.domain.model.Garden
import kotlinx.coroutines.flow.Flow

interface GardenRepository {
    fun getGardens(): Flow<List<Garden>>
    
    suspend fun getGardenById(id: String): Garden?
    
    suspend fun insertGarden(garden: Garden)
    
    suspend fun updateGarden(garden: Garden)
    
    suspend fun deleteGarden(garden: Garden)
    
    suspend fun addPlantToGarden(gardenId: String, plantId: String)
    
    suspend fun removePlantFromGarden(gardenId: String, plantId: String)
    
    fun getGardenWithPlants(gardenId: String): Flow<Garden>
} 