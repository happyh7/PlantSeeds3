package com.bps.plantseeds3.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bps.plantseeds3.data.local.converter.LocalDateConverter
import com.bps.plantseeds3.data.local.converter.PlantStatusConverter
import com.bps.plantseeds3.data.local.dao.GardenDao
import com.bps.plantseeds3.data.local.dao.PlantDao
import com.bps.plantseeds3.data.local.dao.PlantingDao
import com.bps.plantseeds3.data.local.dao.SeedDao
import com.bps.plantseeds3.data.local.entity.Garden
import com.bps.plantseeds3.data.local.entity.Plant
import com.bps.plantseeds3.data.local.entity.Planting
import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.data.local.migration.MIGRATION_6_7
import com.bps.plantseeds3.data.local.migration.MIGRATION_7_8
import com.bps.plantseeds3.data.local.migration.MIGRATION_8_9
import java.text.SimpleDateFormat
import java.util.*

@Database(
    entities = [Garden::class, Seed::class, Plant::class, Planting::class],
    version = 9,
    exportSchema = false
)
@TypeConverters(
    LocalDateConverter::class,
    PlantStatusConverter::class
)
abstract class PlantSeedsDatabase : RoomDatabase() {
    abstract fun gardenDao(): GardenDao
    abstract fun seedDao(): SeedDao
    abstract fun plantDao(): PlantDao
    abstract fun plantingDao(): PlantingDao

    companion object {
        const val DATABASE_NAME = "plantseeds_v9.db"

        val MIGRATIONS = arrayOf<Migration>(
            MIGRATION_6_7,
            MIGRATION_7_8,
            MIGRATION_8_9
        )

        private val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Skapa temporära tabeller med nya datumtyper
                database.execSQL("""
                    CREATE TABLE gardens_new (
                        id TEXT PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL,
                        location TEXT,
                        description TEXT,
                        size REAL,
                        soilType TEXT,
                        sunExposure TEXT,
                        createdAt TEXT NOT NULL,
                        updatedAt TEXT NOT NULL
                    )
                """)

                database.execSQL("""
                    CREATE TABLE seeds_new (
                        id TEXT PRIMARY KEY NOT NULL,
                        name TEXT NOT NULL,
                        description TEXT,
                        species TEXT,
                        variety TEXT,
                        sowingDepth REAL,
                        spacing REAL,
                        daysToGermination INTEGER,
                        daysToMaturity INTEGER,
                        createdAt TEXT NOT NULL,
                        updatedAt TEXT NOT NULL
                    )
                """)

                // Konvertera datum från Date till LocalDate
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                
                // Migrera gardens
                database.query("SELECT * FROM gardens").use { cursor ->
                    while (cursor.moveToNext()) {
                        val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                        val location = cursor.getString(cursor.getColumnIndexOrThrow("location"))
                        val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                        val size = cursor.getDouble(cursor.getColumnIndexOrThrow("size"))
                        val soilType = cursor.getString(cursor.getColumnIndexOrThrow("soilType"))
                        val sunExposure = cursor.getString(cursor.getColumnIndexOrThrow("sunExposure"))
                        val createdAt = cursor.getLong(cursor.getColumnIndexOrThrow("createdAt"))
                        val updatedAt = cursor.getLong(cursor.getColumnIndexOrThrow("updatedAt"))

                        val createdAtDate = dateFormat.format(Date(createdAt))
                        val updatedAtDate = dateFormat.format(Date(updatedAt))

                        database.execSQL("""
                            INSERT INTO gardens_new (
                                id, name, location, description, size, soilType, sunExposure, createdAt, updatedAt
                            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """, arrayOf(id, name, location, description, size, soilType, sunExposure, createdAtDate, updatedAtDate))
                    }
                }

                // Migrera seeds
                database.query("SELECT * FROM seeds").use { cursor ->
                    while (cursor.moveToNext()) {
                        val id = cursor.getString(cursor.getColumnIndexOrThrow("id"))
                        val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                        val description = cursor.getString(cursor.getColumnIndexOrThrow("description"))
                        val species = cursor.getString(cursor.getColumnIndexOrThrow("species"))
                        val variety = cursor.getString(cursor.getColumnIndexOrThrow("variety"))
                        val sowingDepth = cursor.getDouble(cursor.getColumnIndexOrThrow("sowingDepth"))
                        val spacing = cursor.getDouble(cursor.getColumnIndexOrThrow("spacing"))
                        val daysToGermination = cursor.getInt(cursor.getColumnIndexOrThrow("daysToGermination"))
                        val daysToMaturity = cursor.getInt(cursor.getColumnIndexOrThrow("daysToMaturity"))
                        val createdAt = cursor.getLong(cursor.getColumnIndexOrThrow("createdAt"))
                        val updatedAt = cursor.getLong(cursor.getColumnIndexOrThrow("updatedAt"))

                        val createdAtDate = dateFormat.format(Date(createdAt))
                        val updatedAtDate = dateFormat.format(Date(updatedAt))

                        database.execSQL("""
                            INSERT INTO seeds_new (
                                id, name, description, species, variety, sowingDepth, spacing,
                                daysToGermination, daysToMaturity, createdAt, updatedAt
                            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                        """, arrayOf(id, name, description, species, variety, sowingDepth, spacing,
                            daysToGermination, daysToMaturity, createdAtDate, updatedAtDate))
                    }
                }

                // Ta bort gamla tabeller
                database.execSQL("DROP TABLE gardens")
                database.execSQL("DROP TABLE seeds")

                // Byt namn på nya tabeller
                database.execSQL("ALTER TABLE gardens_new RENAME TO gardens")
                database.execSQL("ALTER TABLE seeds_new RENAME TO seeds")
            }
        }
    }
} 