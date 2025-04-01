package com.bps.plantseeds3.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bps.plantseeds3.data.local.entity.GardenEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GardenDao {
    @Query("SELECT * FROM gardens")
    fun getAllGardens(): Flow<List<GardenEntity>>

    @Query("SELECT * FROM gardens WHERE id = :id")
    suspend fun getGardenById(id: Long): GardenEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGarden(garden: GardenEntity): Long

    @Update
    suspend fun updateGarden(garden: GardenEntity)

    @Delete
    suspend fun deleteGarden(garden: GardenEntity)
} 