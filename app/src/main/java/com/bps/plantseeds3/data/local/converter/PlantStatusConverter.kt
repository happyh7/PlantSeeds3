package com.bps.plantseeds3.data.local.converter

import androidx.room.TypeConverter
import com.bps.plantseeds3.data.local.entity.PlantStatus

class PlantStatusConverter {
    @TypeConverter
    fun fromString(value: String?): PlantStatus? {
        return when (value) {
            "PLANTED" -> PlantStatus.GROWING
            "GROWING" -> PlantStatus.GROWING
            "FLOWERING" -> PlantStatus.FLOWERING
            "FRUITING" -> PlantStatus.FRUITING
            "HARVESTED" -> PlantStatus.HARVESTED
            "DEAD" -> PlantStatus.DEAD
            null -> null
            else -> try {
                PlantStatus.valueOf(value)
            } catch (e: IllegalArgumentException) {
                PlantStatus.SEED
            }
        }
    }

    @TypeConverter
    fun toString(status: PlantStatus?): String? {
        return status?.name
    }
} 