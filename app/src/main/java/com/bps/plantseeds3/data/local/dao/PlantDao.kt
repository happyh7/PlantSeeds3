package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.Plant
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface PlantDao {
    @Query("SELECT * FROM plants")
    fun getAllPlants(): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE garden_id = :gardenId")
    fun getPlantsByGardenId(gardenId: Long): Flow<List<Plant>>

    @Query("SELECT * FROM plants WHERE id = :id")
    fun getPlantById(id: UUID): Flow<Plant?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlant(plant: Plant): Long

    @Update
    suspend fun updatePlant(plant: Plant): Int

    @Delete
    suspend fun deletePlant(plant: Plant): Int

    @Query("SELECT * FROM plants WHERE name LIKE '%' || :query || '%' OR scientific_name LIKE '%' || :query || '%' OR species LIKE '%' || :query || '%' OR variety LIKE '%' || :query || '%'")
    fun searchPlants(query: String): Flow<List<Plant>>
} 