package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.Garden
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenDao {
    @Query("SELECT * FROM gardens")
    fun getAllGardens(): Flow<List<Garden>>

    @Query("SELECT * FROM gardens WHERE id = :id")
    fun getGardenById(id: Long): Flow<Garden?>

    @Query("SELECT * FROM gardens WHERE name = :name")
    suspend fun getGardenByName(name: String): Garden?

    @Query("SELECT * FROM gardens WHERE name LIKE '%' || :query || '%' OR location LIKE '%' || :query || '%'")
    fun searchGardens(query: String): Flow<List<Garden>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGarden(garden: Garden): Long

    @Update
    suspend fun updateGarden(garden: Garden): Int

    @Delete
    suspend fun deleteGarden(garden: Garden): Int
} 