package com.bps.plantseeds3.presentation.plants.add_edit

import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import java.time.LocalDate

data class AddEditPlantState(
    val name: String = "",
    val scientificName: String? = null,
    val species: String? = null,
    val variety: String? = null,
    val description: String? = null,
    val category: PlantCategory = PlantCategory.VEGETABLE,
    val status: PlantStatus = PlantStatus.PLANNED,
    val plantingDate: LocalDate? = null,
    val expectedHarvestDate: LocalDate? = null,
    val actualHarvestDate: LocalDate? = null,
    val sowingDepth: Float? = null,
    val spacing: Float? = null,
    val daysToGermination: Int? = null,
    val daysToMaturity: Int? = null,
    val sunRequirement: String? = null,
    val waterRequirement: String? = null,
    val soilRequirement: String? = null,
    val soilPh: Float? = null,
    val hardiness: String? = null,
    val sowingInstructions: String? = null,
    val growingInstructions: String? = null,
    val harvestInstructions: String? = null,
    val storageInstructions: String? = null,
    val companionPlants: List<String> = emptyList(),
    val avoidPlants: List<String> = emptyList(),
    val height: Float? = null,
    val spread: Float? = null,
    val yield: String? = null,
    val culinaryUses: List<String> = emptyList(),
    val medicinalUses: List<String> = emptyList(),
    val tags: List<String> = emptyList(),
    val notes: List<String> = emptyList(),
    val gardenId: String? = null,
    val showCategoryDialog: Boolean = false,
    val showStatusDialog: Boolean = false,
    val error: String? = null,
    val isSaved: Boolean = false
) 