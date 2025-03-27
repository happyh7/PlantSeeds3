package com.bps.plantseeds3.presentation.plants.add_edit

import com.bps.plantseeds3.data.local.entity.PlantStatus
import java.time.LocalDate

data class AddEditPlantState(
    val name: String = "",
    val scientificName: String = "",
    val species: String = "",
    val variety: String = "",
    val category: String = "",
    val description: String = "",
    val selectedGardenId: String? = null,
    val status: PlantStatus = PlantStatus.SEED,
    val plantingDate: LocalDate = LocalDate.now(),
    val harvestDate: LocalDate? = null,
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
    val error: String? = null,
    val isSaved: Boolean = false
) 