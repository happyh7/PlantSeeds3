package com.bps.plantseeds3.garden.presentation.detail

import com.bps.plantseeds3.domain.model.Plant
import com.bps.plantseeds3.garden.domain.model.Garden

data class GardenDetailState(
    val garden: Garden? = null,
    val plants: List<Plant> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAddPlantDialogVisible: Boolean = false,
    val isEditPlantDialogVisible: Boolean = false,
    val plantToEdit: Plant? = null
)

// ... existing code ... 