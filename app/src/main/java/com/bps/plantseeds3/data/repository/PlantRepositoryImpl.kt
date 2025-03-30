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

    override suspend fun insertPlant(plant: PlantDomain): Result<Unit> {
        return try {
            dao.insertPlant(mapper.toEntity(plant))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updatePlant(plant: PlantDomain): Result<Unit> {
        return try {
            dao.updatePlant(mapper.toEntity(plant))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deletePlant(plant: PlantDomain): Result<Unit> {
        return try {
            dao.deletePlant(mapper.toEntity(plant))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getPlantById(id: Long): Flow<PlantDomain?> {
        return dao.getPlantById(id).map { plant ->
            plant?.let { mapper.toDomain(it) }
        }
    }

    override fun getAllPlants(): Flow<List<PlantDomain>> {
        return getAll(dao::getAllPlants) { mapper.toDomain(it) }
    }

    override fun getPlantsByGardenId(gardenId: Long): Flow<List<PlantDomain>> {
        Log.d(TAG, "Hämtar växter för trädgård: $gardenId")
        return dao.getPlantsByGardenId(gardenId).map { plants ->
            Log.d(TAG, "Hämtat ${plants.size} växter för trädgård $gardenId")
            plants.map { mapper.toDomain(it) }
        }
    }

    override fun searchPlants(query: String): Flow<List<PlantDomain>> {
        return dao.searchPlants("%$query%").map { plants ->
            plants.map { mapper.toDomain(it) }
        }
    }
} 