package com.bps.plantseeds3.data.repository

import android.util.Log
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.entity.Plant
import com.bps.plantseeds3.data.mapper.PlantMapper
import com.bps.plantseeds3.domain.model.Plant as PlantDomain
import com.bps.plantseeds3.domain.repository.PlantRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlantRepositoryImpl @Inject constructor(
    private val plantDao: PlantDao,
    private val plantMapper: PlantMapper
) : BaseRepository<Plant, PlantDomain>(), PlantRepository {

    override val TAG = "PlantRepositoryImpl"
    override val dao = plantDao
    override val mapper = plantMapper

    override suspend fun insertPlant(plant: PlantDomain) {
        insert(plant, dao::insertPlant)
    }

    override suspend fun updatePlant(plant: PlantDomain) {
        update(plant, dao::updatePlant)
    }

    override suspend fun deletePlant(plant: PlantDomain) {
        delete(plant, dao::deletePlant)
    }

    override suspend fun getPlantById(id: String): PlantDomain? {
        return getById(id, dao::getPlantById)
    }

    override fun getAllPlants(): Flow<List<PlantDomain>> {
        return getAll(dao::getAllPlants) { mapper.toDomain(it) }
    }

    override fun getPlantsByGardenId(gardenId: String): Flow<List<PlantDomain>> {
        Log.d(TAG, "Hämtar växter för trädgård: $gardenId")
        return dao.getPlantsByGardenId(gardenId).map { plants ->
            Log.d(TAG, "Hämtat ${plants.size} växter för trädgård $gardenId")
            plants.map { mapper.toDomain(it) }
        }
    }
} 