package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.data.local.dao.PlantingDao
import com.bps.plantseeds3.data.local.entity.Planting
import com.bps.plantseeds3.data.local.entity.PlantingStatus
import com.bps.plantseeds3.domain.repository.PlantingRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import javax.inject.Inject

class PlantingRepositoryImpl @Inject constructor(
    private val dao: PlantingDao
) : PlantingRepository {

    override fun getAllPlantings(): Flow<List<Planting>> {
        return dao.getAllPlantings()
    }

    override suspend fun getPlantingById(id: String): Planting? {
        return dao.getPlantingById(id)
    }

    override fun getPlantingsByPlantId(plantId: String): Flow<List<Planting>> {
        return dao.getPlantingsByPlantId(plantId)
    }

    override fun getPlantingsByGardenId(gardenId: String): Flow<List<Planting>> {
        return dao.getPlantingsByGardenId(gardenId)
    }

    override fun getPlantingsByStatus(status: PlantingStatus): Flow<List<Planting>> {
        return dao.getPlantingsByStatus(status)
    }

    override fun getPlantingsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Planting>> {
        return dao.getPlantingsByDateRange(startDate, endDate)
    }

    override suspend fun insertPlanting(planting: Planting) {
        try {
            Log.d("PlantingRepositoryImpl", "Försöker spara ny plantering: ${planting.id}")
            dao.insertPlanting(planting)
            Log.d("PlantingRepositoryImpl", "Plantering sparad framgångsrikt: ${planting.id}")
        } catch (e: Exception) {
            Log.e("PlantingRepositoryImpl", "Fel vid sparning av plantering: ${planting.id}", e)
            throw e
        }
    }

    override suspend fun deletePlanting(planting: Planting) {
        try {
            Log.d("PlantingRepositoryImpl", "Försöker ta bort plantering: ${planting.id}")
            dao.deletePlanting(planting)
            Log.d("PlantingRepositoryImpl", "Plantering borttagen framgångsrikt: ${planting.id}")
        } catch (e: Exception) {
            Log.e("PlantingRepositoryImpl", "Fel vid borttagning av plantering: ${planting.id}", e)
            throw e
        }
    }

    override suspend fun updatePlanting(planting: Planting) {
        try {
            Log.d("PlantingRepositoryImpl", "Försöker uppdatera plantering: ${planting.id}")
            dao.updatePlanting(planting)
            Log.d("PlantingRepositoryImpl", "Plantering uppdaterad framgångsrikt: ${planting.id}")
        } catch (e: Exception) {
            Log.e("PlantingRepositoryImpl", "Fel vid uppdatering av plantering: ${planting.id}", e)
            throw e
        }
    }

    override fun getPlantingsByStatusAndGarden(status: PlantingStatus, gardenId: String): Flow<List<Planting>> {
        return dao.getPlantingsByStatusAndGarden(status, gardenId)
    }
} 