package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.data.local.entity.Garden
import kotlinx.coroutines.flow.Flow

interface GardenRepository {
    suspend fun insertGarden(garden: Garden)
    
    suspend fun updateGarden(garden: Garden)
    
    suspend fun deleteGarden(garden: Garden)
    
    suspend fun getGardenById(id: String): Garden?
    
    suspend fun getGardenByName(name: String): Garden?
    
    fun getAllGardens(): Flow<List<Garden>>
    
    fun searchGardens(query: String): Flow<List<Garden>>
} 