package com.bps.plantseeds3.domain.model

enum class PlantStatus(val displayName: String) {
    PLANNED("Planerad"),
    SEEDED("Sådd"),
    GERMINATED("Grott"),
    TRANSPLANTED("Omplantering"),
    ACTIVE("Aktiv"),
    INACTIVE("Inaktiv"),
    FAILED("Misslyckad");

    companion object {
        fun fromString(value: String?): PlantStatus {
            return try {
                value?.let { valueOf(it) } ?: PLANNED
            } catch (e: IllegalArgumentException) {
                PLANNED
            }
        }
    }
} 