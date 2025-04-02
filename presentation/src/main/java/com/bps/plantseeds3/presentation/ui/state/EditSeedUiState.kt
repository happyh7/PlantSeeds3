package com.bps.plantseeds3.presentation.ui.state

data class EditSeedUiState(
    val id: String = "",
    val plantId: String = "",
    val name: String = "",
    val species: String? = null,
    val description: String? = null,
    val plantingInstructions: String? = null,
    val daysToGermination: Int? = null,
    val daysToHarvest: Int? = null,
    val lightNeeds: String? = null,
    val waterNeeds: String? = null,
    val soilType: String? = null,
    val temperature: String? = null,
    val spacing: String? = null,
    val companionPlants: List<String>? = null,
    val avoidPlants: List<String>? = null,
    val imageUrl: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null
) 