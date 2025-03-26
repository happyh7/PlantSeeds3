package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.entity.Plant
import com.bps.plantseeds3.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val dao: PlantDao
) : PlantRepository {

    override suspend fun insertPlant(plant: Plant) {
        try {
            Log.d("PlantRepositoryImpl", "Försöker spara ny växt: ${plant.name}")
            dao.insertPlant(plant)
            Log.d("PlantRepositoryImpl", "Växt sparad framgångsrikt: ${plant.name}")
        } catch (e: Exception) {
            Log.e("PlantRepositoryImpl", "Fel vid sparning av växt: ${plant.name}", e)
            throw e
        }
    }

    override suspend fun updatePlant(plant: Plant) {
        try {
            Log.d("PlantRepositoryImpl", "Försöker uppdatera växt: ${plant.name}")
            dao.updatePlant(plant)
            Log.d("PlantRepositoryImpl", "Växt uppdaterad framgångsrikt: ${plant.name}")
        } catch (e: Exception) {
            Log.e("PlantRepositoryImpl", "Fel vid uppdatering av växt: ${plant.name}", e)
            throw e
        }
    }

    override suspend fun deletePlant(plant: Plant) {
        try {
            Log.d("PlantRepositoryImpl", "Försöker ta bort växt: ${plant.name}")
            dao.deletePlant(plant)
            Log.d("PlantRepositoryImpl", "Växt borttagen framgångsrikt: ${plant.name}")
        } catch (e: Exception) {
            Log.e("PlantRepositoryImpl", "Fel vid borttagning av växt: ${plant.name}", e)
            throw e
        }
    }

    override suspend fun getPlantById(id: String): Plant? {
        return try {
            Log.d("PlantRepositoryImpl", "Hämtar växt med ID: $id")
            val plant = dao.getPlantById(id)
            if (plant != null) {
                Log.d("PlantRepositoryImpl", "Hittade växt: ${plant.name}")
            } else {
                Log.d("PlantRepositoryImpl", "Ingen växt hittades med ID: $id")
            }
            plant
        } catch (e: Exception) {
            Log.e("PlantRepositoryImpl", "Fel vid hämtning av växt med ID: $id", e)
            throw e
        }
    }

    override fun getAllPlants(): Flow<List<Plant>> {
        return try {
            Log.d("PlantRepositoryImpl", "Hämtar alla växter")
            dao.getAllPlants()
        } catch (e: Exception) {
            Log.e("PlantRepositoryImpl", "Fel vid hämtning av alla växter", e)
            throw e
        }
    }

    override fun getPlantsByGardenId(gardenId: String): Flow<List<Plant>> {
        return try {
            Log.d("PlantRepositoryImpl", "Hämtar växter för trädgård: $gardenId")
            dao.getPlantsByGardenId(gardenId)
        } catch (e: Exception) {
            Log.e("PlantRepositoryImpl", "Fel vid hämtning av växter för trädgård: $gardenId", e)
            throw e
        }
    }
} 