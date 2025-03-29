package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.data.local.entity.Planting
import com.bps.plantseeds3.data.local.entity.PlantingStatus
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface PlantingRepository {
    fun getAllPlantings(): Flow<List<Planting>>
    
    suspend fun getPlantingById(id: String): Planting?
    
    fun getPlantingsByPlantId(plantId: String): Flow<List<Planting>>
    
    fun getPlantingsByGardenId(gardenId: String): Flow<List<Planting>>
    
    fun getPlantingsByStatus(status: PlantingStatus): Flow<List<Planting>>
    
    fun getPlantingsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Planting>>
    
    suspend fun insertPlanting(planting: Planting)
    
    suspend fun deletePlanting(planting: Planting)
    
    suspend fun updatePlanting(planting: Planting)
    
    fun getPlantingsByStatusAndGarden(status: PlantingStatus, gardenId: String): Flow<List<Planting>>
} 