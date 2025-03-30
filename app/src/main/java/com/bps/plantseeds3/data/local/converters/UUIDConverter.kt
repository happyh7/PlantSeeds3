package com.bps.plantseeds3.data.local.converters

import androidx.room.TypeConverter
import java.util.UUID

class UUIDConverter {
    @TypeConverter
    fun fromString(value: String?): UUID? {
        return value?.let { UUID.fromString(it) }
    }

    @TypeConverter
    fun toString(uuid: UUID?): String? {
        return uuid?.toString()
    }
} 