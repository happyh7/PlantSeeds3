package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.Plant
import com.bps.plantseeds3.data.local.entity.PlantStatus
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlantById(id: String): Plant?

    @Query("SELECT * FROM plants WHERE gardenId = :gardenId")
    fun getPlantsByGardenId(gardenId: String): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE status = :status")
    fun getPlantsByStatus(status: PlantStatus): Flow<List<Plant>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)

    @Update
    suspend fun updatePlant(plant: Plant)
} 