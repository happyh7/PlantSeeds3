package com.bps.plantseeds3.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

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
    indices = [
        Index("garden_id")
    ]
)
data class Plant(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "garden_id")
    val gardenId: String,
    val name: String,
    @ColumnInfo(name = "scientific_name")
    val scientificName: String? = null,
    val species: String? = null,
    val variety: String? = null,
    val description: String? = null,
    val category: PlantCategory = PlantCategory.VEGETABLE,
    @ColumnInfo(name = "planting_date")
    val plantingDate: LocalDate? = null,
    @ColumnInfo(name = "harvest_date")
    val expectedHarvestDate: LocalDate? = null,
    @ColumnInfo(name = "actual_harvest_date")
    val actualHarvestDate: LocalDate? = null,
    @ColumnInfo(name = "sowing_depth")
    val sowingDepth: Float? = null,
    val spacing: Float? = null,
    @ColumnInfo(name = "days_to_germination")
    val daysToGermination: Int? = null,
    @ColumnInfo(name = "days_to_maturity")
    val daysToMaturity: Int? = null,
    @ColumnInfo(name = "sun_requirement")
    val sunRequirement: String? = null,
    @ColumnInfo(name = "water_requirement")
    val waterRequirement: String? = null,
    @ColumnInfo(name = "soil_requirement")
    val soilRequirement: String? = null,
    @ColumnInfo(name = "soil_ph")
    val soilPh: Float? = null,
    val hardiness: String? = null,
    @ColumnInfo(name = "sowing_instructions")
    val sowingInstructions: String? = null,
    @ColumnInfo(name = "growing_instructions")
    val growingInstructions: String? = null,
    @ColumnInfo(name = "harvest_instructions")
    val harvestInstructions: String? = null,
    @ColumnInfo(name = "storage_instructions")
    val storageInstructions: String? = null,
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
    val notes: List<String> = emptyList(),
    val status: PlantStatus = PlantStatus.PLANNED,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) 