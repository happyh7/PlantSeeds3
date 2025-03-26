package com.bps.plantseeds3.data.local.converters

import androidx.room.TypeConverter
import com.bps.plantseeds3.data.local.entity.PlantStatus

class PlantStatusConverter {
    @TypeConverter
    fun fromPlantStatus(status: PlantStatus?): String? {
        return status?.name
    }

    @TypeConverter
    fun toPlantStatus(value: String?): PlantStatus? {
        return value?.let { PlantStatus.valueOf(it) }
    }
} 