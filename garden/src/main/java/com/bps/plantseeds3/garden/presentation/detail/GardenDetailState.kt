package com.bps.plantseeds3.garden.presentation.detail

import com.bps.plantseeds3.garden.domain.model.Garden
import com.bps.plantseeds3.domain.model.Plant

data class GardenDetailState(
    val isLoading: Boolean = false,
    val garden: Garden? = null,
    val plants: List<Plant> = emptyList(),
    val error: String? = null,
    val showAddPlantDialog: Boolean = false,
    val plantToEdit: Plant? = null
) 