package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.SeedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeedDao {
    @Query("SELECT * FROM seeds ORDER BY createdAt DESC")
    fun getAllSeeds(): Flow<List<SeedEntity>>

    @Query("SELECT * FROM seeds WHERE id = :seedId")
    suspend fun getSeedById(seedId: String): SeedEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeed(seed: SeedEntity)

    @Update
    suspend fun updateSeed(seed: SeedEntity)

    @Delete
    suspend fun deleteSeed(seed: SeedEntity)

    @Query("DELETE FROM seeds WHERE id = :seedId")
    suspend fun deleteSeedById(seedId: String)
} 