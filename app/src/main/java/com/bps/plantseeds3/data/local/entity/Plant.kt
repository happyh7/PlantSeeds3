package com.bps.plantseeds3.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(
    tableName = "plants",
    foreignKeys = [
        ForeignKey(
            entity = Garden::class,
            parentColumns = ["id"],
            childColumns = ["garden_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("garden_id")]
)
data class Plant(
    @PrimaryKey
    val id: String,
    
    @ColumnInfo(name = "garden_id")
    val gardenId: String,
    
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
    
    @ColumnInfo(name = "status")
    val status: PlantStatus = PlantStatus.PLANNED,
    
    @ColumnInfo(name = "planting_date")
    val plantingDate: LocalDate?,
    
    @ColumnInfo(name = "expected_harvest_date")
    val expectedHarvestDate: LocalDate?,
    
    @ColumnInfo(name = "actual_harvest_date")
    val actualHarvestDate: LocalDate?,
    
    @ColumnInfo(name = "sowing_depth")
    val sowingDepth: Float?,
    
    @ColumnInfo(name = "spacing")
    val spacing: Float?,
    
    @ColumnInfo(name = "days_to_germination")
    val daysToGermination: Int?,
    
    @ColumnInfo(name = "days_to_maturity")
    val daysToMaturity: Int?,
    
    @ColumnInfo(name = "sun_requirement")
    val sunRequirement: String?,
    
    @ColumnInfo(name = "water_requirement")
    val waterRequirement: String?,
    
    @ColumnInfo(name = "soil_requirement")
    val soilRequirement: String?,
    
    @ColumnInfo(name = "soil_ph")
    val soilPh: Float?,
    
    @ColumnInfo(name = "hardiness")
    val hardiness: String?,
    
    @ColumnInfo(name = "sowing_instructions")
    val sowingInstructions: String?,
    
    @ColumnInfo(name = "growing_instructions")
    val growingInstructions: String?,
    
    @ColumnInfo(name = "harvest_instructions")
    val harvestInstructions: String?,
    
    @ColumnInfo(name = "storage_instructions")
    val storageInstructions: String?,
    
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
    
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) 