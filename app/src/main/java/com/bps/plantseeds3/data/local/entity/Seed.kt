package com.bps.plantseeds3.data.local.entity

import android.util.Log
import androidx.room.*
import com.bps.plantseeds3.data.local.converter.Converters
import com.bps.plantseeds3.domain.model.PlantCategory
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity(tableName = "seeds")
@TypeConverters(Converters::class)
data class Seed(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    
    @ColumnInfo(name = "name")
    val name: String,
    
    @ColumnInfo(name = "scientific_name")
    val scientificName: String?,
    
    @ColumnInfo(name = "species")
    val species: String?,
    
    @ColumnInfo(name = "variety")
    val variety: String?,
    
    @ColumnInfo(name = "category")
    val category: PlantCategory = PlantCategory.VEGETABLE,
    
    @ColumnInfo(name = "description")
    val description: String?,
    
    @ColumnInfo(name = "quantity")
    val quantity: Int = 0,
    
    @ColumnInfo(name = "unit")
    val unit: String = "st",
    
    @ColumnInfo(name = "purchase_date")
    val purchaseDate: LocalDate?,
    
    @ColumnInfo(name = "expiry_date")
    val expiryDate: LocalDate?,
    
    @ColumnInfo(name = "supplier")
    val supplier: String?,
    
    @ColumnInfo(name = "price")
    val price: Float?,
    
    @ColumnInfo(name = "currency")
    val currency: String = "SEK",
    
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,
    
    @ColumnInfo(name = "planting_depth")
    val plantingDepth: Float?,
    
    @ColumnInfo(name = "planting_distance")
    val plantingDistance: String?,
    
    @ColumnInfo(name = "plant_spacing")
    val plantSpacing: String?,
    
    @ColumnInfo(name = "row_spacing")
    val rowSpacing: String?,
    
    @ColumnInfo(name = "planting_dates")
    val plantingDates: List<String> = emptyList(),
    
    @ColumnInfo(name = "sun_requirement")
    val sunRequirement: String?,
    
    @ColumnInfo(name = "water_requirement")
    val waterRequirement: String?,
    
    @ColumnInfo(name = "soil_type")
    val soilType: String?,
    
    @ColumnInfo(name = "soil_ph")
    val soilPh: Float?,
    
    @ColumnInfo(name = "hardiness_zone")
    val hardinessZone: String?,
    
    @ColumnInfo(name = "sowing_instructions")
    val sowingInstructions: String?,
    
    @ColumnInfo(name = "growing_instructions")
    val growingInstructions: String?,
    
    @ColumnInfo(name = "harvesting_instructions")
    val harvestingInstructions: String?,
    
    @ColumnInfo(name = "storage_instructions")
    val storageInstructions: String?,
    
    @ColumnInfo(name = "days_to_germination")
    val daysToGermination: Int?,
    
    @ColumnInfo(name = "days_to_maturity")
    val daysToMaturity: Int?,
    
    @ColumnInfo(name = "harvest_period")
    val harvestPeriod: String?,
    
    @ColumnInfo(name = "lifespan")
    val lifespan: String?,
    
    @ColumnInfo(name = "maintenance_dates")
    val maintenanceDates: List<String> = emptyList(),
    
    @ColumnInfo(name = "fertilizing_schedule")
    val fertilizingSchedule: String?,
    
    @ColumnInfo(name = "pruning_schedule")
    val pruningSchedule: String?,
    
    @ColumnInfo(name = "companion_plants")
    val companionPlants: List<String> = emptyList(),
    
    @ColumnInfo(name = "avoid_plants")
    val avoidPlants: List<String> = emptyList(),
    
    @ColumnInfo(name = "height")
    val height: Float?,
    
    @ColumnInfo(name = "spread")
    val spread: Float?,
    
    @ColumnInfo(name = "yield")
    val yield: String?,
    
    @ColumnInfo(name = "culinary_uses")
    val culinaryUses: List<String> = emptyList(),
    
    @ColumnInfo(name = "medicinal_uses")
    val medicinalUses: List<String> = emptyList(),
    
    @ColumnInfo(name = "tags")
    val tags: List<String> = emptyList(),
    
    @ColumnInfo(name = "notes")
    val notes: List<String> = emptyList(),
    
    @ColumnInfo(name = "image_url")
    val imageUrl: String?,
    
    @ColumnInfo(name = "source")
    val source: String?,
    
    @ColumnInfo(name = "last_planted")
    val lastPlanted: LocalDate?,
    
    @ColumnInfo(name = "last_harvested")
    val lastHarvested: LocalDate?,
    
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        private const val TAG = "SeedEntity"

        fun createEmpty(): Seed {
            Log.d(TAG, "Skapar tomt frö")
            return Seed(
                id = UUID.randomUUID().toString(),
                name = "",
                scientificName = null,
                species = null,
                variety = null,
                category = PlantCategory.VEGETABLE,
                description = null,
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
                notes = emptyList(),
                imageUrl = null,
                source = null,
                lastPlanted = null,
                lastHarvested = null
            )
        }
    }
} 