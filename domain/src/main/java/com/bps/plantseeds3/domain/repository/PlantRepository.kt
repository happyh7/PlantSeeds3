package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Plant
import kotlinx.coroutines.flow.Flow

interface PlantRepository {
    fun getPlants(): Flow<Resource<List<Plant>>>
    suspend fun getPlantById(id: String): Resource<Plant>
    suspend fun insertPlant(plant: Plant): Resource<Unit>
    suspend fun updatePlant(plant: Plant): Resource<Unit>
    suspend fun deletePlant(id: String): Resource<Unit>
    suspend fun searchPlants(query: String): Resource<List<Plant>>
} 