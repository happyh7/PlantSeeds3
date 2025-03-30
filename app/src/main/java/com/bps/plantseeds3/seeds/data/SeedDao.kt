package com.bps.plantseeds3.seeds.data

import androidx.room.*
import com.bps.plantseeds3.seeds.model.Seed
import kotlinx.coroutines.flow.Flow

@Dao
interface SeedDao {
    @Query("SELECT * FROM seeds ORDER BY name ASC")
    fun getAllSeeds(): Flow<List<Seed>>

    @Query("SELECT * FROM seeds WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteSeeds(): Flow<List<Seed>>

    @Query("SELECT * FROM seeds WHERE id = :id")
    fun getSeedById(id: Long): Flow<Seed?>

    @Query("SELECT * FROM seeds WHERE id = :id")
    suspend fun getSeedByIdSync(id: Long): Seed?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeed(seed: Seed)

    @Update
    suspend fun updateSeed(seed: Seed)

    @Delete
    suspend fun deleteSeed(seed: Seed)

    @Query("SELECT * FROM seeds WHERE name LIKE :query OR species LIKE :query OR variety LIKE :query")
    suspend fun searchSeeds(query: String): List<Seed>
} 