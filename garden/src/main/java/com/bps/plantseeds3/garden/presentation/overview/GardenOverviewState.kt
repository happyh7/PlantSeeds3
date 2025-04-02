package com.bps.plantseeds3.garden.presentation.overview

import com.bps.plantseeds3.garden.domain.model.Garden

data class GardenOverviewState(
    val gardens: List<Garden> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAddGardenDialogVisible: Boolean = false
)

sealed class GardenOverviewEvent {
    data object LoadGardens : GardenOverviewEvent()
    data class AddGarden(val garden: Garden) : GardenOverviewEvent()
    data class DeleteGarden(val garden: Garden) : GardenOverviewEvent()
    data object ShowAddGardenDialog : GardenOverviewEvent()
    data object HideAddGardenDialog : GardenOverviewEvent()
} 