package com.bps.plantseeds3.domain.repository

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.domain.model.Garden
import kotlinx.coroutines.flow.Flow

interface GardenRepository {
    fun getGardens(): Flow<Resource<List<Garden>>>
    suspend fun getGardenById(id: String): Resource<Garden>
    suspend fun insertGarden(garden: Garden): Resource<Unit>
    suspend fun updateGarden(garden: Garden): Resource<Unit>
    suspend fun deleteGarden(id: String): Resource<Unit>
    suspend fun searchGardens(query: String): Resource<List<Garden>>
} 