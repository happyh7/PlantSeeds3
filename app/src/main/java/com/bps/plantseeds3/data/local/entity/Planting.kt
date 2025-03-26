package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bps.plantseeds3.data.local.converter.LocalDateConverter
import java.time.LocalDate

@Entity(
    tableName = "plantings",
    foreignKeys = [
        ForeignKey(
            entity = Plant::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Garden::class,
            parentColumns = ["id"],
            childColumns = ["gardenId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("plantId"),
        Index("gardenId")
    ]
)
@TypeConverters(LocalDateConverter::class)
data class Planting(
    @PrimaryKey
    val id: String,
    
    // Referenser till relaterade entiteter
    val plantId: String,
    val gardenId: String,
    
    // Planteringsinformation
    val plantingDate: LocalDate,
    val expectedHarvestDate: LocalDate?,
    val actualHarvestDate: LocalDate?,
    val plantingDepth: Float?,
    val spacing: Float?,
    val quantity: Int = 1,
    
    // Växtförhållanden
    val soilType: String?,
    val soilPh: String?,
    val sunExposure: String?,
    val waterFrequency: String?,
    
    // Skötselinformation
    val lastWatered: LocalDate?,
    val lastFertilized: LocalDate?,
    val lastPruned: LocalDate?,
    val maintenanceNotes: String?,
    
    // Status och utveckling
    val status: PlantingStatus = PlantingStatus.PLANNED,
    val germinationDate: LocalDate?,
    val floweringDate: LocalDate?,
    val fruitingDate: LocalDate?,
    
    // Metadata
    val notes: String?,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
)

enum class PlantingStatus {
    PLANNED,        // Planerad plantering
    PLANTED,        // Planterad
    GERMINATING,    // Grodde
    GROWING,        // Växande
    FLOWERING,      // Blommar
    FRUITING,       // Bär frukt
    HARVESTED,      // Skördad
    FAILED,         // Misslyckad
    COMPLETED       // Avslutad
} 