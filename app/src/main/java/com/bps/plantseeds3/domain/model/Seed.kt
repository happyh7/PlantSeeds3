package com.bps.plantseeds3.domain.model

import android.util.Log
import java.util.*
import java.time.LocalDate
import java.util.UUID

data class Seed(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val scientificName: String? = null,
    val species: String? = null,
    val variety: String? = null,
    val category: PlantCategory,
    val description: String? = null,
    val plantingDepth: Double? = null,
    val plantingDistance: Double? = null,
    val plantSpacing: Float? = null,
    val rowSpacing: Float? = null,
    val plantingDates: String? = null,
    val sunRequirement: String? = null,
    val waterRequirement: String? = null,
    val soilType: String? = null,
    val soilPh: Double? = null,
    val hardinessZone: String? = null,
    val sowingInstructions: String? = null,
    val growingInstructions: String? = null,
    val harvestingInstructions: String? = null,
    val storageInstructions: String? = null,
    val daysToGermination: Int? = null,
    val daysToMaturity: Int? = null,
    val harvestPeriod: String? = null,
    val lifespan: String? = null,
    val maintenanceDates: String? = null,
    val fertilizingSchedule: String? = null,
    val pruningSchedule: String? = null,
    val companionPlants: String? = null,
    val avoidPlants: String? = null,
    val height: Float? = null,
    val spread: Float? = null,
    val yield: String? = null,
    val culinaryUses: String? = null,
    val medicinalUses: String? = null,
    val tags: String? = null,
    val notes: String? = null,
    val imageUrl: String? = null,
    val source: String? = null,
    val lastPlanted: Date? = null,
    val lastHarvested: Date? = null,
    val isFavorite: Boolean = false,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
) {
    private val TAG = "SeedDomain"

    init {
        Log.d(TAG, "Skapar nytt frö: $name")
    }

    companion object {
        fun createEmpty(): Seed {
            Log.d("SeedDomain", "Skapar tomt frö")
            return Seed(
                id = UUID.randomUUID().toString(),
                name = "",
                scientificName = null,
                species = null,
                variety = null,
                category = PlantCategory.OTHER,
                description = null,
                plantingDepth = null,
                plantingDistance = null,
                plantSpacing = null,
                rowSpacing = null,
                plantingDates = null,
                sunRequirement = null,
                waterRequirement = null,
                soilType = null,
                soilPh = null,
                hardinessZone = null,
                sowingInstructions = null,
                growingInstructions = null,
                harvestingInstructions = null,
                storageInstructions = null,
                daysToGermination = null,
                daysToMaturity = null,
                harvestPeriod = null,
                lifespan = null,
                maintenanceDates = null,
                fertilizingSchedule = null,
                pruningSchedule = null,
                companionPlants = null,
                avoidPlants = null,
                height = null,
                spread = null,
                yield = null,
                culinaryUses = null,
                medicinalUses = null,
                tags = null,
                notes = null,
                imageUrl = null,
                source = null,
                lastPlanted = null,
                lastHarvested = null,
                isFavorite = false,
                createdAt = Date(),
                updatedAt = Date()
            )
        }
    }
} 