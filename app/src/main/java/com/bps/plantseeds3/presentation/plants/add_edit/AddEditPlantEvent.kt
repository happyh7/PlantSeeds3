package com.bps.plantseeds3.presentation.plants.add_edit

import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import java.time.LocalDate

sealed class AddEditPlantEvent {
    data class OnNameChange(val name: String) : AddEditPlantEvent()
    data class OnScientificNameChange(val name: String?) : AddEditPlantEvent()
    data class OnSpeciesChange(val species: String?) : AddEditPlantEvent()
    data class OnVarietyChange(val variety: String?) : AddEditPlantEvent()
    data class OnDescriptionChange(val description: String?) : AddEditPlantEvent()
    data class OnCategoryChange(val category: PlantCategory) : AddEditPlantEvent()
    data class OnStatusChange(val status: PlantStatus) : AddEditPlantEvent()
    data class OnPlantingDateChange(val date: LocalDate?) : AddEditPlantEvent()
    data class OnExpectedHarvestDateChange(val date: LocalDate?) : AddEditPlantEvent()
    data class OnActualHarvestDateChange(val date: LocalDate?) : AddEditPlantEvent()
    data class OnSowingDepthChange(val depth: String) : AddEditPlantEvent()
    data class OnSpacingChange(val spacing: String) : AddEditPlantEvent()
    data class OnDaysToGerminationChange(val days: String) : AddEditPlantEvent()
    data class OnDaysToMaturityChange(val days: String) : AddEditPlantEvent()
    data class OnSunRequirementChange(val requirement: String?) : AddEditPlantEvent()
    data class OnWaterRequirementChange(val requirement: String?) : AddEditPlantEvent()
    data class OnSoilRequirementChange(val requirement: String?) : AddEditPlantEvent()
    data class OnSoilPhChange(val ph: String) : AddEditPlantEvent()
    data class OnHardinessChange(val hardiness: String?) : AddEditPlantEvent()
    data class OnSowingInstructionsChange(val instructions: String?) : AddEditPlantEvent()
    data class OnGrowingInstructionsChange(val instructions: String?) : AddEditPlantEvent()
    data class OnHarvestInstructionsChange(val instructions: String?) : AddEditPlantEvent()
    data class OnStorageInstructionsChange(val instructions: String?) : AddEditPlantEvent()
    data class OnCompanionPlantsChange(val plants: String) : AddEditPlantEvent()
    data class OnAvoidPlantsChange(val plants: String) : AddEditPlantEvent()
    data class OnHeightChange(val height: String) : AddEditPlantEvent()
    data class OnSpreadChange(val spread: String) : AddEditPlantEvent()
    data class OnYieldChange(val yield: String?) : AddEditPlantEvent()
    data class OnCulinaryUsesChange(val uses: String) : AddEditPlantEvent()
    data class OnMedicinalUsesChange(val uses: String) : AddEditPlantEvent()
    data class OnTagsChange(val tags: String) : AddEditPlantEvent()
    data class OnNotesChange(val notes: String) : AddEditPlantEvent()
    data class OnGardenIdChange(val gardenId: String) : AddEditPlantEvent()
    data class OnShowCategoryDialogChange(val show: Boolean) : AddEditPlantEvent()
    data class OnShowStatusDialogChange(val show: Boolean) : AddEditPlantEvent()
    object OnSavePlant : AddEditPlantEvent()
} 