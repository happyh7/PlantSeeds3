package com.bps.plantseeds3.data.local.converters

import androidx.room.TypeConverter
import java.util.Date

class GardenDateConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
} 