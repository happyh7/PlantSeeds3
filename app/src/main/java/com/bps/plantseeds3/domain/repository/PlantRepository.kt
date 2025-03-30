package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getAllPlants(): Flow<List<Plant>>
    fun getPlantById(id: Long): Flow<Plant?>
    fun getPlantsByGardenId(gardenId: Long): Flow<List<Plant>>
    suspend fun insertPlant(plant: Plant): Result<Unit>
    suspend fun updatePlant(plant: Plant): Result<Unit>
    suspend fun deletePlant(plant: Plant): Result<Unit>
    fun searchPlants(query: String): Flow<List<Plant>>
} 