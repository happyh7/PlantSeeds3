package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey
    val id: String,
    val name: String,
    val scientificName: String,
    val species: String,
    val variety: String,
    val category: String,
    val description: String,
    val gardenId: String,
    val status: PlantStatus,
    val plantingDate: LocalDate,
    val harvestDate: LocalDate?,
    val sowingDepth: String?,
    val spacing: String?,
    val daysToGermination: String?,
    val daysToMaturity: String?,
    val sunRequirement: String?,
    val waterRequirement: String?,
    val soilRequirement: String?,
    val soilPh: String?,
    val hardiness: String?,
    val sowingInstructions: String?,
    val growingInstructions: String?,
    val harvestInstructions: String?,
    val storageInstructions: String?,
    val companionPlants: String?,
    val avoidPlants: String?,
    val height: String?,
    val spread: String?,
    val yield: String?,
    val culinaryUses: String?,
    val medicinalUses: String?,
    val tags: String?,
    val notes: String?,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
) 