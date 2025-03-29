package com.bps.plantseeds3.data.local.entity

import android.util.Log
import androidx.room.*
import com.bps.plantseeds3.data.local.converter.Converters
import java.util.*

@Entity(tableName = "seeds")
@TypeConverters(Converters::class)
data class Seed(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    
    // Grundläggande information
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "scientificName")
    val scientificName: String? = null,
    @ColumnInfo(name = "species")
    val species: String? = null,
    @ColumnInfo(name = "variety")
    val variety: String? = null,
    @ColumnInfo(name = "category")
    val category: String,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "isFavorite")
    val isFavorite: Boolean = false,
    
    // Planteringsinformation
    @ColumnInfo(name = "plantingDepth")
    val plantingDepth: Double? = null,        // i centimeter
    @ColumnInfo(name = "plantingDistance")
    val plantingDistance: Double? = null,      // i centimeter
    @ColumnInfo(name = "plantSpacing")
    val plantSpacing: Float? = null,          // i centimeter
    @ColumnInfo(name = "rowSpacing")
    val rowSpacing: Float? = null,            // i centimeter
    @ColumnInfo(name = "plantingDates")
    val plantingDates: String? = null,        // JSON-array med rekommenderade planteringsdatum
    
    // Växtförhållanden
    @ColumnInfo(name = "sunRequirement")
    val sunRequirement: String? = null,       // t.ex. "full_sun", "partial_shade", "shade"
    @ColumnInfo(name = "waterRequirement")
    val waterRequirement: String? = null,     // t.ex. "low", "medium", "high"
    @ColumnInfo(name = "soilType")
    val soilType: String? = null,            // t.ex. "well_drained", "clay", "sandy"
    @ColumnInfo(name = "soilPh")
    val soilPh: Double? = null,              // t.ex. "acidic", "neutral", "alkaline"
    @ColumnInfo(name = "hardinessZone")
    val hardinessZone: String? = null,       // växtzon
    
    // Instruktioner
    @ColumnInfo(name = "sowingInstructions")
    val sowingInstructions: String? = null,
    @ColumnInfo(name = "growingInstructions")
    val growingInstructions: String? = null,
    @ColumnInfo(name = "harvestingInstructions")
    val harvestingInstructions: String? = null,
    @ColumnInfo(name = "storageInstructions")
    val storageInstructions: String? = null,
    
    // Tidsinformation
    @ColumnInfo(name = "daysToGermination")
    val daysToGermination: Int? = null,
    @ColumnInfo(name = "daysToMaturity")
    val daysToMaturity: Int? = null,
    @ColumnInfo(name = "harvestPeriod")
    val harvestPeriod: String? = null,        // t.ex. "june_august"
    @ColumnInfo(name = "lifespan")
    val lifespan: String? = null,             // t.ex. "annual", "perennial", "biennial"
    
    // Underhåll och skötsel
    @ColumnInfo(name = "maintenanceDates")
    val maintenanceDates: String? = null,     // JSON-array med underhållsdatum och åtgärder
    @ColumnInfo(name = "fertilizingSchedule")
    val fertilizingSchedule: String? = null,   // JSON-objekt med gödslingsschema
    @ColumnInfo(name = "pruningSchedule")
    val pruningSchedule: String? = null,       // JSON-objekt med beskärningsschema
    
    // Växtsamspel
    @ColumnInfo(name = "companionPlants")
    val companionPlants: String? = null,      // JSON-array med gynnsamma följeväxter
    @ColumnInfo(name = "avoidPlants")
    val avoidPlants: String? = null,          // JSON-array med olämpliga följeväxter
    
    // Extra information
    @ColumnInfo(name = "height")
    val height: Float? = null,                // förväntad höjd i centimeter
    @ColumnInfo(name = "spread")
    val spread: Float? = null,                // förväntad bredd i centimeter
    @ColumnInfo(name = "yield")
    val yield: String? = null,                // förväntad skörd
    @ColumnInfo(name = "culinaryUses")
    val culinaryUses: String? = null,         // JSON-array med kulinariska användningsområden
    @ColumnInfo(name = "medicinalUses")
    val medicinalUses: String? = null,        // JSON-array med medicinska användningsområden
    @ColumnInfo(name = "tags")
    val tags: String? = null,                 // JSON-array med taggar för kategorisering
    @ColumnInfo(name = "notes")
    val notes: String? = null,                // allmänna anteckningar
    
    // Metadata
    @ColumnInfo(name = "imageUrl")
    val imageUrl: String? = null,             // URL till bild på växten
    @ColumnInfo(name = "source")
    val source: String? = null,               // varifrån fröet kommer
    @ColumnInfo(name = "lastPlanted")
    val lastPlanted: Date? = null,            // senaste planteringsdatum
    @ColumnInfo(name = "lastHarvested")
    val lastHarvested: Date? = null,          // senaste skördedatum
    @ColumnInfo(name = "createdAt")
    val createdAt: Date = Date(),
    @ColumnInfo(name = "updatedAt")
    val updatedAt: Date = Date()
) {
    companion object {
        private const val TAG = "SeedEntity"

        fun createEmpty(): Seed {
            Log.d(TAG, "Skapar tomt frö")
            return Seed(
                id = UUID.randomUUID().toString(),
                name = "",
                scientificName = null,
                description = null,
                category = "OTHER",
                plantingDepth = null,
                plantingDistance = null,
                daysToGermination = null,
                daysToMaturity = null,
                lifespan = null,
                hardinessZone = null,
                sunRequirement = null,
                waterRequirement = null,
                soilType = null,
                soilPh = null,
                sowingInstructions = null,
                growingInstructions = null,
                harvestingInstructions = null,
                storageInstructions = null,
                imageUrl = null,
                source = null,
                isFavorite = false,
                createdAt = Date(),
                updatedAt = Date()
            )
        }
    }
} 