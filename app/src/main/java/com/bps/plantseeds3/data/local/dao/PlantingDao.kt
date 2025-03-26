package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.Planting
import com.bps.plantseeds3.data.local.entity.PlantingStatus
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface PlantingDao {
    @Query("SELECT * FROM plantings")
    fun getAllPlantings(): Flow<List<Planting>>

    @Query("SELECT * FROM plantings WHERE id = :id")
    suspend fun getPlantingById(id: String): Planting?

    @Query("SELECT * FROM plantings WHERE plantId = :plantId")
    fun getPlantingsByPlantId(plantId: String): Flow<List<Planting>>

    @Query("SELECT * FROM plantings WHERE gardenId = :gardenId")
    fun getPlantingsByGardenId(gardenId: String): Flow<List<Planting>>

    @Query("SELECT * FROM plantings WHERE status = :status")
    fun getPlantingsByStatus(status: PlantingStatus): Flow<List<Planting>>

    @Query("SELECT * FROM plantings WHERE plantingDate BETWEEN :startDate AND :endDate")
    fun getPlantingsByDateRange(startDate: LocalDate, endDate: LocalDate): Flow<List<Planting>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanting(planting: Planting)

    @Delete
    suspend fun deletePlanting(planting: Planting)

    @Update
    suspend fun updatePlanting(planting: Planting)

    @Query("SELECT * FROM plantings WHERE status = :status AND gardenId = :gardenId")
    fun getPlantingsByStatusAndGarden(status: PlantingStatus, gardenId: String): Flow<List<Planting>>
} 