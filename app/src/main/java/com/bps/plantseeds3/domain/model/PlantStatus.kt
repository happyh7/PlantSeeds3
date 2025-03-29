package com.bps.plantseeds3.domain.model

enum class PlantStatus {
    SEED,
    SEEDLING,
    GROWING,
    MATURE,
    FLOWERING,
    FRUITING,
    HARVESTED,
    DORMANT,
    DEAD;

    val displayName: String
        get() = when (this) {
            SEED -> "Frö"
            SEEDLING -> "Grodd"
            GROWING -> "Växande"
            MATURE -> "Mogen"
            FLOWERING -> "Blommande"
            FRUITING -> "Fruktbärande"
            HARVESTED -> "Skördad"
            DORMANT -> "Vilande"
            DEAD -> "Död"
        }
} 