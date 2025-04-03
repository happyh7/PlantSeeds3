package com.bps.plantseeds3.garden.presentation.overview

import com.bps.plantseeds3.garden.domain.model.Garden

data class GardenOverviewState(
    val gardens: List<Garden> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAddGardenDialogVisible: Boolean = false,
    val isEditGardenDialogVisible: Boolean = false,
    val gardenToEdit: Garden? = null
) 