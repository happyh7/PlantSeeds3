package com.bps.plantseeds3.data.local.migration

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.bps.plantseeds3.data.local.PlantSeedsDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class MigrationTest {
    private val TEST_DB = "migration-test"

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        PlantSeedsDatabase::class.java,
        listOf(),
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    @Throws(IOException::class)
    fun migrate13To14() {
        // Skapa version 13 av databasen
        helper.createDatabase(TEST_DB, 13).apply {
            // Lägg till testdata för version 13
            execSQL("""
                CREATE TABLE IF NOT EXISTS seeds (
                    id TEXT NOT NULL PRIMARY KEY,
                    plantId TEXT NOT NULL,
                    name TEXT NOT NULL,
                    species TEXT,
                    description TEXT,
                    plantingInstructions TEXT,
                    daysToGermination INTEGER,
                    daysToHarvest INTEGER,
                    lightNeeds TEXT,
                    waterNeeds TEXT,
                    soilType TEXT,
                    temperature TEXT,
                    spacing TEXT,
                    companionPlants TEXT,
                    avoidPlants TEXT,
                    imageUrl TEXT,
                    createdAt INTEGER NOT NULL,
                    updatedAt INTEGER NOT NULL,
                    isSynced INTEGER NOT NULL DEFAULT 0
                )
            """)

            // Lägg till testdata
            execSQL("""
                INSERT INTO seeds (
                    id, plantId, name, species, description, createdAt, updatedAt, isSynced
                ) VALUES (
                    'test1', 'plant1', 'Test Seed', 'Test Species', 'Test Description',
                    1234567890, 1234567890, 0
                )
            """)

            close()
        }

        // Öppna version 14 av databasen
        val db = helper.runMigrationsAndValidate(TEST_DB, 14, true, DatabaseMigrations.MIGRATION_13_14)

        // Verifiera att migrationen lyckades
        val cursor = db.query("SELECT * FROM seeds WHERE id = 'test1'")
        assert(cursor.moveToFirst())
        assert(cursor.getString(cursor.getColumnIndex("name")) == "Test Seed")
        assert(cursor.getString(cursor.getColumnIndex("species")) == "Test Species")
        cursor.close()

        // Verifiera att index skapades
        val indexCursor = db.query("SELECT name FROM sqlite_master WHERE type='index' AND tbl_name='seeds'")
        val indexNames = mutableListOf<String>()
        while (indexCursor.moveToNext()) {
            indexNames.add(indexCursor.getString(0))
        }
        indexCursor.close()

        assert(indexNames.contains("index_seeds_plantId"))
        assert(indexNames.contains("index_seeds_name"))
        assert(indexNames.contains("index_seeds_createdAt"))
        assert(indexNames.contains("index_seeds_updatedAt"))
    }
} 