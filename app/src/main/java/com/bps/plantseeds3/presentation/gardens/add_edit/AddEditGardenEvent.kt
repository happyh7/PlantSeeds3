package com.bps.plantseeds3.presentation.gardens.add_edit

sealed class AddEditGardenEvent {
    data class EnteredName(val value: String) : AddEditGardenEvent()
    data class EnteredLocation(val value: String) : AddEditGardenEvent()
    data class EnteredDescription(val value: String) : AddEditGardenEvent()
    data class EnteredSize(val value: String) : AddEditGardenEvent()
    data class EnteredWidth(val value: String) : AddEditGardenEvent()
    data class EnteredLength(val value: String) : AddEditGardenEvent()
    data class EnteredElevation(val value: String) : AddEditGardenEvent()
    data class EnteredSlope(val value: String) : AddEditGardenEvent()
    data class EnteredSoilType(val value: String) : AddEditGardenEvent()
    data class EnteredSunExposure(val value: String) : AddEditGardenEvent()
    data class EnteredIrrigation(val value: String) : AddEditGardenEvent()
    data class EnteredFence(val value: String) : AddEditGardenEvent()
    data class EnteredNotes(val value: String) : AddEditGardenEvent()
    data object SaveGarden : AddEditGardenEvent()
} 