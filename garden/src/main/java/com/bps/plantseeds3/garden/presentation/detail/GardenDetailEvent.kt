package com.bps.plantseeds3.garden.presentation.detail

import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.domain.model.Plant

sealed class GardenDetailEvent {
    data class LoadGarden(val gardenId: String) : GardenDetailEvent()
    data class AddPlant(val plant: Plant) : GardenDetailEvent()
    data class EditPlant(val plant: Plant) : GardenDetailEvent()
    data class DeletePlant(val plant: Plant) : GardenDetailEvent()
    object ShowAddPlantDialog : GardenDetailEvent()
    object HideAddPlantDialog : GardenDetailEvent()
    data class ShowEditPlantDialog(val plant: Plant) : GardenDetailEvent()
    object HideEditPlantDialog : GardenDetailEvent()
} 