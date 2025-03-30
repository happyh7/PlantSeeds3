package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.domain.model.Garden
import kotlinx.coroutines.flow.Flow

interface GardenRepository {
    fun getAllGardens(): Flow<List<Garden>>
    fun getGardenById(id: Long): Flow<Garden?>
    suspend fun insertGarden(garden: Garden): Result<Unit>
    suspend fun updateGarden(garden: Garden): Result<Unit>
    suspend fun deleteGarden(garden: Garden): Result<Unit>
    fun searchGardens(query: String): Flow<List<Garden>>
} 