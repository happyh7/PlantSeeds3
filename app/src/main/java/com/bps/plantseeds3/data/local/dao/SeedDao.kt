package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.Seed
import kotlinx.coroutines.flow.Flow

@Dao
interface SeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeed(seed: Seed)

    @Update
    suspend fun updateSeed(seed: Seed)

    @Delete
    suspend fun deleteSeed(seed: Seed)

    @Query("SELECT * FROM seeds WHERE id = :id")
    suspend fun getSeedById(id: String): Seed?

    @Query("SELECT * FROM seeds ORDER BY name ASC")
    fun getAllSeeds(): Flow<List<Seed>>

    @Query("""
        SELECT * FROM seeds 
        WHERE name LIKE '%' || :query || '%' 
        OR scientificName LIKE '%' || :query || '%'
        OR species LIKE '%' || :query || '%'
        OR variety LIKE '%' || :query || '%'
        OR category LIKE '%' || :query || '%'
        OR tags LIKE '%' || :query || '%'
        ORDER BY name ASC
    """)
    fun searchSeeds(query: String): Flow<List<Seed>>

    @Query("""
        SELECT * FROM seeds 
        WHERE category = :category 
        ORDER BY name ASC
    """)
    fun getSeedsByCategory(category: String): Flow<List<Seed>>

    @Query("""
        SELECT * FROM seeds 
        WHERE lifespan = :lifespan 
        ORDER BY name ASC
    """)
    fun getSeedsByLifespan(lifespan: String): Flow<List<Seed>>

    @Query("""
        SELECT * FROM seeds 
        WHERE hardiness LIKE '%' || :zone || '%' 
        ORDER BY name ASC
    """)
    fun getSeedsByHardinessZone(zone: String): Flow<List<Seed>>

    @Query("DELETE FROM seeds")
    suspend fun deleteAllSeeds()
} 