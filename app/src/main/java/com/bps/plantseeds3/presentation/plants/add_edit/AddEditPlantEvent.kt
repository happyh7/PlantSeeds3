package com.bps.plantseeds3.presentation.plants.add_edit

import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus

sealed class AddEditPlantEvent {
    data class SavePlant(
        val name: String,
        val scientificName: String? = null,
        val species: String? = null,
        val variety: String? = null,
        val description: String? = null,
        val category: PlantCategory? = null,
        val status: PlantStatus? = null,
        val plantingDate: String? = null,
        val harvestDate: String? = null,
        val sunRequirement: String? = null,
        val waterRequirement: String? = null,
        val soilRequirement: String? = null,
        val notes: String? = null
    ) : AddEditPlantEvent()
} 