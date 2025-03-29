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
    suspend fun insertSeed(seed: Seed)

    /**
     * Uppdaterar ett befintligt frö i databasen.
     */
    @Update
    suspend fun updateSeed(seed: Seed)

    /**
     * Tar bort ett frö från databasen.
     */
    @Delete
    suspend fun deleteSeed(seed: Seed)

    /**
     * Tar bort alla frön från databasen.
     */
    @Query("DELETE FROM seeds")
    suspend fun deleteAllSeeds()

    /**
     * Hämtar ett frö med specifikt ID.
     */
    @Query("SELECT * FROM seeds WHERE id = :id")
    suspend fun getSeedById(id: String): Seed?

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
    @Query("SELECT * FROM seeds WHERE name LIKE '%' || :query || '%' OR scientificName LIKE '%' || :query || '%' ORDER BY name ASC")
    fun searchSeeds(query: String): Flow<List<Seed>>

    /**
     * Hämtar alla favoritfrön sorterade efter namn.
     */
    @Query("SELECT * FROM seeds WHERE isFavorite = 1 ORDER BY name ASC")
    fun getFavoriteSeeds(): Flow<List<Seed>>

    /**
     * Hämtar alla frön med specifik livslängd sorterade efter namn.
     */
    @Query("SELECT * FROM seeds WHERE lifespan = :lifespan ORDER BY name ASC")
    fun getSeedsByLifespan(lifespan: String): Flow<List<Seed>>

    /**
     * Hämtar alla frön i en specifik hårdhetszon sorterade efter namn.
     */
    @Query("SELECT * FROM seeds WHERE hardinessZone = :zone ORDER BY name ASC")
    fun getSeedsByHardinessZone(zone: String): Flow<List<Seed>>

    /**
     * Hämtar alla unika kategorier sorterade alfabetiskt.
     */
    @Query("SELECT DISTINCT category FROM seeds ORDER BY category ASC")
    fun getDistinctCategories(): Flow<List<String>>
} 