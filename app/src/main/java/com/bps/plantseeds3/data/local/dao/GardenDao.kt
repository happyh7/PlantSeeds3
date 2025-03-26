package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.Garden
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenDao {
    @Query("SELECT * FROM gardens ORDER BY name ASC")
    fun getAllGardens(): Flow<List<Garden>>

    @Query("SELECT * FROM gardens WHERE id = :id")
    suspend fun getGardenById(id: String): Garden?

    @Query("SELECT * FROM gardens WHERE name = :name")
    suspend fun getGardenByName(name: String): Garden?

    @Query("SELECT * FROM gardens WHERE name LIKE :query ORDER BY name ASC")
    fun searchGardens(query: String): Flow<List<Garden>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGarden(garden: Garden)

    @Update
    suspend fun updateGarden(garden: Garden)

    @Delete
    suspend fun deleteGarden(garden: Garden)
} 