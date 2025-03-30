package com.bps.plantseeds3.domain.model

import java.util.Date
import android.util.Log
import java.util.*
import java.time.LocalDate
import java.util.UUID
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "seeds")
data class Seed(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "scientific_name")
    val scientificName: String? = null,
    val species: String? = null,
    val variety: String? = null,
    val description: String? = null,
    val category: PlantCategory? = null,
    val quantity: Int = 0,
    val unit: String = "st",
    @ColumnInfo(name = "purchase_date")
    val purchaseDate: Date? = null,
    @ColumnInfo(name = "expiry_date")
    val expiryDate: Date? = null,
    val supplier: String? = null,
    val price: Float? = null,
    val currency: String = "SEK",
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    @ColumnInfo(name = "planting_depth")
    val plantingDepth: Float? = null,
    @ColumnInfo(name = "planting_distance")
    val plantingDistance: Float? = null,
    @ColumnInfo(name = "plant_spacing")
    val plantSpacing: Float? = null,
    @ColumnInfo(name = "row_spacing")
    val rowSpacing: Float? = null,
    @ColumnInfo(name = "planting_dates")
    val plantingDates: List<String> = emptyList(),
    @ColumnInfo(name = "sun_requirement")
    val sunRequirement: String? = null,
    @ColumnInfo(name = "water_requirement")
    val waterRequirement: String? = null,
    @ColumnInfo(name = "soil_type")
    val soilType: String? = null,
    @ColumnInfo(name = "soil_ph")
    val soilPh: Float? = null,
    @ColumnInfo(name = "hardiness_zone")
    val hardinessZone: String? = null,
    @ColumnInfo(name = "sowing_instructions")
    val sowingInstructions: String? = null,
    @ColumnInfo(name = "growing_instructions")
    val growingInstructions: String? = null,
    @ColumnInfo(name = "harvesting_instructions")
    val harvestingInstructions: String? = null,
    @ColumnInfo(name = "storage_instructions")
    val storageInstructions: String? = null,
    @ColumnInfo(name = "days_to_germination")
    val daysToGermination: Int? = null,
    @ColumnInfo(name = "days_to_maturity")
    val daysToMaturity: Int? = null,
    @ColumnInfo(name = "harvest_period")
    val harvestPeriod: String? = null,
    val lifespan: String? = null,
    @ColumnInfo(name = "maintenance_dates")
    val maintenanceDates: List<String> = emptyList(),
    @ColumnInfo(name = "fertilizing_schedule")
    val fertilizingSchedule: String? = null,
    @ColumnInfo(name = "pruning_schedule")
    val pruningSchedule: String? = null,
    @ColumnInfo(name = "companion_plants")
    val companionPlants: List<String> = emptyList(),
    @ColumnInfo(name = "avoid_plants")
    val avoidPlants: List<String> = emptyList(),
    val height: Float? = null,
    val spread: Float? = null,
    val yield: String? = null,
    @ColumnInfo(name = "culinary_uses")
    val culinaryUses: List<String> = emptyList(),
    @ColumnInfo(name = "medicinal_uses")
    val medicinalUses: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val notes: String? = null,
    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,
    val source: String? = null,
    @ColumnInfo(name = "last_planted")
    val lastPlanted: Date? = null,
    @ColumnInfo(name = "last_harvested")
    val lastHarvested: Date? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: Date = Date()
) {
    init {
        Log.d("SeedDomain", "Skapar nytt frö: $name")
    }

    companion object {
        fun createEmpty(): Seed {
            Log.d("SeedDomain", "Skapar tomt frö")
            return Seed(
                id = 0,
                name = "",
                scientificName = null,
                species = null,
                variety = null,
                description = null,
                category = null,
                quantity = 0,
                unit = "st",
                purchaseDate = null,
                expiryDate = null,
                supplier = null,
                price = null,
                currency = "SEK",
                isFavorite = false,
                plantingDepth = null,
                plantingDistance = null,
                plantSpacing = null,
                rowSpacing = null,
                plantingDates = emptyList(),
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
                maintenanceDates = emptyList(),
                fertilizingSchedule = null,
                pruningSchedule = null,
                companionPlants = emptyList(),
                avoidPlants = emptyList(),
                height = null,
                spread = null,
                yield = null,
                culinaryUses = emptyList(),
                medicinalUses = emptyList(),
                tags = emptyList(),
                notes = null,
                imageUrl = null,
                source = null,
                lastPlanted = null,
                lastHarvested = null,
                createdAt = Date(),
                updatedAt = Date()
            )
        }
    }
} 