package com.bps.plantseeds3.data.local.dao

import androidx.room.*
import com.bps.plantseeds3.data.local.entity.Seed
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object för hantering av frödata i databasen.
 */
@Dao
interface SeedDao {
    /**
     * Infogar ett nytt frö i databasen eller uppdaterar ett befintligt om det redan finns.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeed(seed: Seed): Long

    /**
     * Uppdaterar ett befintligt frö i databasen.
     */
    @Update
    suspend fun updateSeed(seed: Seed): Int

    /**
     * Tar bort ett frö från databasen.
     */
    @Delete
    suspend fun deleteSeed(seed: Seed): Int

    /**
     * Tar bort alla frön från databasen.
     */
    @Query("DELETE FROM seeds")
    suspend fun deleteAllSeeds()

    /**
     * Hämtar ett frö med specifikt ID.
     */
    @Query("SELECT * FROM seeds WHERE id = :id")
    fun getSeedById(id: Long): Flow<Seed?>

    /**
     * Hämtar alla frön sorterade efter namn.
     */
    @Query("SELECT * FROM seeds ORDER BY name ASC")
    fun getAllSeeds(): Flow<List<Seed>>

    /**
     * Hämtar alla frön i en specifik kategori sorterade efter namn.
     */
    @Query("SELECT * FROM seeds WHERE category = :category ORDER BY name ASC")
    fun getSeedsByCategory(category: String): Flow<List<Seed>>

    /**
     * Söker efter frön baserat på namn eller vetenskapligt namn.
     */
    @Query("SELECT * FROM seeds WHERE name LIKE '%' || :query || '%' OR scientific_name LIKE '%' || :query || '%' OR species LIKE '%' || :query || '%' OR variety LIKE '%' || :query || '%'")
    fun searchSeeds(query: String): Flow<List<Seed>>

    /**
     * Hämtar alla favoritfrön sorterade efter namn.
     */
    @Query("SELECT * FROM seeds WHERE is_favorite = 1 ORDER BY name ASC")
    fun getFavoriteSeeds(): Flow<List<Seed>>

    /**
     * Hämtar alla frön med specifik livslängd sorterade efter namn.
     */
    @Query("SELECT * FROM seeds WHERE lifespan = :lifespan ORDER BY name ASC")
    fun getSeedsByLifespan(lifespan: String): Flow<List<Seed>>

    /**
     * Hämtar alla frön i en specifik hårdhetszon sorterade efter namn.
     */
    @Query("SELECT * FROM seeds WHERE hardiness_zone = :zone ORDER BY name ASC")
    fun getSeedsByHardinessZone(zone: String): Flow<List<Seed>>

    /**
     * Hämtar alla unika kategorier sorterade alfabetiskt.
     */
    @Query("SELECT DISTINCT category FROM seeds WHERE category IS NOT NULL")
    fun getDistinctCategories(): Flow<List<String>>

    @Query("UPDATE seeds SET is_favorite = NOT is_favorite WHERE id = :seedId")
    suspend fun toggleFavorite(seedId: Long): Int
} 