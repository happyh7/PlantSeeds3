package com.bps.plantseeds3.data.local.converter

import androidx.room.TypeConverter
import com.bps.plantseeds3.domain.model.PlantStatus

class PlantStatusConverter {
    @TypeConverter
    fun fromString(value: String?): PlantStatus {
        return value?.let { PlantStatus.valueOf(it) } ?: PlantStatus.PLANNED
    }

    @TypeConverter
    fun toString(status: PlantStatus): String {
        return status.name
    }
} 