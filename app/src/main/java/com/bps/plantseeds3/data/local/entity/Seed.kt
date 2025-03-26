package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bps.plantseeds3.data.local.converter.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "seeds")
@TypeConverters(LocalDateConverter::class)
data class Seed(
    @PrimaryKey
    val id: String,
    
    // Grundläggande information
    val name: String,
    val scientificName: String = "",
    val species: String = "",
    val variety: String = "",
    val category: String = "",
    val description: String = "",
    
    // Planteringsinformation
    val plantingDepth: Float = 0f,        // i centimeter
    val plantingDistance: Float = 0f,      // i centimeter
    val plantSpacing: Float = 0f,          // i centimeter
    val rowSpacing: Float = 0f,            // i centimeter
    val plantingDates: String = "",        // JSON-array med rekommenderade planteringsdatum
    
    // Växtförhållanden
    val sunRequirement: String = "",       // t.ex. "full_sun", "partial_shade", "shade"
    val waterRequirement: String = "",     // t.ex. "low", "medium", "high"
    val soilRequirement: String = "",      // t.ex. "well_drained", "clay", "sandy"
    val soilPh: String = "",              // t.ex. "acidic", "neutral", "alkaline"
    val hardiness: String = "",           // växtzon
    
    // Instruktioner
    val sowingInstructions: String = "",
    val growingInstructions: String = "",
    val harvestInstructions: String = "",
    val storageInstructions: String = "",
    
    // Tidsinformation
    val daysToGermination: Int = 0,
    val daysToHarvest: Int = 0,
    val harvestPeriod: String = "",        // t.ex. "june_august"
    val lifespan: String = "",             // t.ex. "annual", "perennial", "biennial"
    
    // Underhåll och skötsel
    val maintenanceDates: String = "",     // JSON-array med underhållsdatum och åtgärder
    val fertilizingSchedule: String = "",   // JSON-objekt med gödslingsschema
    val pruningSchedule: String = "",       // JSON-objekt med beskärningsschema
    
    // Växtsamspel
    val companionPlants: String = "",      // JSON-array med gynnsamma följeväxter
    val avoidPlants: String = "",          // JSON-array med olämpliga följeväxter
    
    // Extra information
    val height: Float = 0f,                // förväntad höjd i centimeter
    val spread: Float = 0f,                // förväntad bredd i centimeter
    val yield: String = "",                // förväntad skörd
    val culinaryUses: String = "",         // JSON-array med kulinariska användningsområden
    val medicinalUses: String = "",        // JSON-array med medicinska användningsområden
    val tags: String = "",                 // JSON-array med taggar för kategorisering
    val notes: String = "",                // allmänna anteckningar
    
    // Metadata
    val imageUrl: String = "",             // URL till bild på växten
    val source: String = "",               // varifrån fröet kommer
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
) 