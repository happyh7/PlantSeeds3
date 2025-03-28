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
    val scientificName: String? = null,
    val species: String? = null,
    val variety: String? = null,
    val category: String? = null,
    val description: String? = null,
    
    // Planteringsinformation
    val plantingDepth: Float? = null,        // i centimeter
    val plantingDistance: Float? = null,      // i centimeter
    val plantSpacing: Float? = null,          // i centimeter
    val rowSpacing: Float? = null,            // i centimeter
    val plantingDates: String? = null,        // JSON-array med rekommenderade planteringsdatum
    
    // Växtförhållanden
    val sunRequirement: String? = null,       // t.ex. "full_sun", "partial_shade", "shade"
    val waterRequirement: String? = null,     // t.ex. "low", "medium", "high"
    val soilRequirement: String? = null,      // t.ex. "well_drained", "clay", "sandy"
    val soilPh: String? = null,              // t.ex. "acidic", "neutral", "alkaline"
    val hardiness: String? = null,           // växtzon
    
    // Instruktioner
    val sowingInstructions: String? = null,
    val growingInstructions: String? = null,
    val harvestInstructions: String? = null,
    val storageInstructions: String? = null,
    
    // Tidsinformation
    val daysToGermination: Int? = null,
    val daysToHarvest: Int? = null,
    val harvestPeriod: String? = null,        // t.ex. "june_august"
    val lifespan: String? = null,             // t.ex. "annual", "perennial", "biennial"
    
    // Underhåll och skötsel
    val maintenanceDates: String? = null,     // JSON-array med underhållsdatum och åtgärder
    val fertilizingSchedule: String? = null,   // JSON-objekt med gödslingsschema
    val pruningSchedule: String? = null,       // JSON-objekt med beskärningsschema
    
    // Växtsamspel
    val companionPlants: String? = null,      // JSON-array med gynnsamma följeväxter
    val avoidPlants: String? = null,          // JSON-array med olämpliga följeväxter
    
    // Extra information
    val height: Float? = null,                // förväntad höjd i centimeter
    val spread: Float? = null,                // förväntad bredd i centimeter
    val yield: String? = null,                // förväntad skörd
    val culinaryUses: String? = null,         // JSON-array med kulinariska användningsområden
    val medicinalUses: String? = null,        // JSON-array med medicinska användningsområden
    val tags: String? = null,                 // JSON-array med taggar för kategorisering
    val notes: String? = null,                // allmänna anteckningar
    
    // Metadata
    val imageUrl: String? = null,             // URL till bild på växten
    val source: String? = null,               // varifrån fröet kommer
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
) 