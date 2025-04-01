package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "seeds",
    foreignKeys = [
        ForeignKey(
            entity = PlantEntity::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("plantId")
    ]
)
data class SeedEntity(
    @PrimaryKey
    val id: String,
    val plantId: String,
    val name: String,
    val species: String?,
    val description: String?,
    val plantingInstructions: String?,
    val daysToGermination: Int?,
    val daysToHarvest: Int?,
    val lightNeeds: String?,
    val waterNeeds: String?,
    val soilType: String?,
    val temperature: String?,
    val spacing: String?,
    val companionPlants: List<String>?,
    val avoidPlants: List<String>?,
    val imageUrl: String?,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
) 