package com.bps.plantseeds3.garden.presentation.detail

import com.bps.plantseeds3.domain.model.Plant

sealed class GardenDetailEvent {
    object LoadGarden : GardenDetailEvent()
    data class AddPlant(
        val name: String,
        val species: String,
        val description: String?
    ) : GardenDetailEvent()
    data class EditPlant(
        val plant: Plant,
        val name: String,
        val species: String,
        val description: String?
    ) : GardenDetailEvent()
    data class DeletePlant(val plant: Plant) : GardenDetailEvent()
    object ShowAddPlantDialog : GardenDetailEvent()
    object HideAddPlantDialog : GardenDetailEvent()
    data class ShowEditPlantDialog(val plant: Plant) : GardenDetailEvent()
    object HideEditPlantDialog : GardenDetailEvent()
} 