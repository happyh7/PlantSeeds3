package com.bps.plantseeds3.domain.model

import java.util.UUID

data class Plant(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val scientificName: String? = null,
    val species: String? = null,
    val variety: String? = null,
    val description: String? = null,
    val category: PlantCategory? = null,
    val status: PlantStatus? = null,
    val plantingDate: String? = null,
    val harvestDate: String? = null,
    val sowingDepth: String? = null,
    val spacing: String? = null,
    val daysToGermination: String? = null,
    val daysToMaturity: String? = null,
    val sunRequirement: String? = null,
    val waterRequirement: String? = null,
    val soilRequirement: String? = null,
    val soilPh: String? = null,
    val hardiness: String? = null,
    val sowingInstructions: String? = null,
    val growingInstructions: String? = null,
    val harvestInstructions: String? = null,
    val storageInstructions: String? = null,
    val companionPlants: String? = null,
    val avoidPlants: String? = null,
    val height: String? = null,
    val spread: String? = null,
    val yield: String? = null,
    val culinaryUses: String? = null,
    val medicinalUses: String? = null,
    val tags: String? = null,
    val notes: String? = null,
    val gardenId: String
) 