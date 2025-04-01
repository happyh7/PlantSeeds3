package com.bps.plantseeds3.data.repository

import com.bps.plantseeds3.common.model.Resource
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.mapper.toEntity
import com.bps.plantseeds3.data.mapper.toGarden
import com.bps.plantseeds3.domain.model.Garden
import com.bps.plantseeds3.domain.repository.GardenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GardenRepositoryImpl @Inject constructor(
    private val gardenDao: GardenDao
) : GardenRepository {

    override fun getGardens(): Flow<Resource<List<Garden>>> = flow {
        emit(Resource.Loading())
        try {
            gardenDao.getAllGardens()
                .map { entities -> entities.map { it.toGarden() } }
                .collect { gardens ->
                    emit(Resource.Success(gardens))
                }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Ett fel uppstod"))
        }
    }

    override suspend fun getGardenById(id: String): Resource<Garden> {
        return try {
            val garden = gardenDao.getGardenById(id.toLong())?.toGarden()
            if (garden != null) {
                Resource.Success(garden)
            } else {
                Resource.Error("Trädgård hittades inte")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod")
        }
    }

    override suspend fun insertGarden(garden: Garden): Resource<Unit> {
        return try {
            gardenDao.insertGarden(garden.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod")
        }
    }

    override suspend fun updateGarden(garden: Garden): Resource<Unit> {
        return try {
            gardenDao.updateGarden(garden.toEntity())
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod")
        }
    }

    override suspend fun deleteGarden(id: String): Resource<Unit> {
        return try {
            val garden = gardenDao.getGardenById(id.toLong())
            if (garden != null) {
                gardenDao.deleteGarden(garden)
                Resource.Success(Unit)
            } else {
                Resource.Error("Trädgård hittades inte")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod")
        }
    }

    override suspend fun searchGardens(query: String): Resource<List<Garden>> {
        return try {
            // Eftersom vi inte har en sökfunktion i DAO:n än, returnerar vi en tom lista
            Resource.Success(emptyList())
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Ett fel uppstod")
        }
    }
} 