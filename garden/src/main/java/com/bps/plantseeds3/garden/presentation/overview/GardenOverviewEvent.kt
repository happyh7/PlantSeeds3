package com.bps.plantseeds3.garden.presentation.overview

import com.bps.plantseeds3.garden.domain.model.Garden

sealed class GardenOverviewEvent {
    object LoadGardens : GardenOverviewEvent()
    data class AddGarden(val garden: Garden) : GardenOverviewEvent()
    data class EditGarden(val garden: Garden) : GardenOverviewEvent()
    data class DeleteGarden(val garden: Garden) : GardenOverviewEvent()
    object ShowAddGardenDialog : GardenOverviewEvent()
    object HideAddGardenDialog : GardenOverviewEvent()
    data class ShowEditGardenDialog(val garden: Garden) : GardenOverviewEvent()
    object HideEditGardenDialog : GardenOverviewEvent()
} 