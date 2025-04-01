package com.bps.plantseeds3.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bps.plantseeds3.data.local.entity.PlantingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PlantingDao {
    @Query("SELECT * FROM plantings")
    fun getAllPlantings(): Flow<List<PlantingEntity>>

    @Query("SELECT * FROM plantings WHERE id = :id")
    suspend fun getPlantingById(id: Long): PlantingEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanting(planting: PlantingEntity): Long

    @Update
    suspend fun updatePlanting(planting: PlantingEntity)

    @Delete
    suspend fun deletePlanting(planting: PlantingEntity)
} 