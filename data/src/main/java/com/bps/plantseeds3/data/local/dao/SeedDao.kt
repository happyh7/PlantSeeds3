package com.bps.plantseeds3.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bps.plantseeds3.data.local.entity.SeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeedDao {
    @Query("SELECT * FROM seeds")
    fun getAllSeeds(): Flow<List<SeedEntity>>

    @Query("SELECT * FROM seeds WHERE id = :id")
    suspend fun getSeedById(id: Long): SeedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeed(seed: SeedEntity): Long

    @Update
    suspend fun updateSeed(seed: SeedEntity)

    @Delete
    suspend fun deleteSeed(seed: SeedEntity)
} 