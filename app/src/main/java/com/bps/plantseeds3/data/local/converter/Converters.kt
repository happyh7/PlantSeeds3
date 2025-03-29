package com.bps.plantseeds3.data.local.converter

import android.util.Log
import androidx.room.TypeConverter
import com.bps.plantseeds3.domain.model.PlantCategory
import java.util.*

class Converters {
    private val TAG = "Converters"

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        Log.d(TAG, "Konverterar timestamp till Date: $value")
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        Log.d(TAG, "Konverterar Date till timestamp: $date")
        return date?.time
    }

    @TypeConverter
    fun fromString(value: String?): List<String> {
        Log.d(TAG, "Konverterar String till List<String>: $value")
        return value?.split(",")?.map { it.trim() } ?: emptyList()
    }

    @TypeConverter
    fun toString(list: List<String>?): String? {
        Log.d(TAG, "Konverterar List<String> till String: $list")
        return list?.joinToString(",")
    }

    @TypeConverter
    fun fromDouble(value: Double?): String? {
        Log.d(TAG, "Konverterar Double till String: $value")
        return value?.toString()
    }

    @TypeConverter
    fun toDouble(value: String?): Double? {
        Log.d(TAG, "Konverterar String till Double: $value")
        return try {
            value?.toDouble()
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Fel vid konvertering av String till Double: $value", e)
            null
        }
    }

    @TypeConverter
    fun fromInt(value: Int?): String? {
        Log.d(TAG, "Konverterar Int till String: $value")
        return value?.toString()
    }

    @TypeConverter
    fun toInt(value: String?): Int? {
        Log.d(TAG, "Konverterar String till Int: $value")
        return try {
            value?.toInt()
        } catch (e: NumberFormatException) {
            Log.e(TAG, "Fel vid konvertering av String till Int: $value", e)
            null
        }
    }

    @TypeConverter
    fun fromBoolean(value: Boolean?): Int? {
        Log.d(TAG, "Konverterar Boolean till Int: $value")
        return if (value == true) 1 else 0
    }

    @TypeConverter
    fun toBoolean(value: Int?): Boolean? {
        Log.d(TAG, "Konverterar Int till Boolean: $value")
        return value == 1
    }

    @TypeConverter
    fun fromPlantCategory(category: PlantCategory?): String? {
        Log.d(TAG, "Konverterar PlantCategory till String: $category")
        return category?.name
    }

    @TypeConverter
    fun toPlantCategory(value: String?): PlantCategory? {
        Log.d(TAG, "Konverterar String till PlantCategory: $value")
        return try {
            value?.let { PlantCategory.valueOf(it) }
        } catch (e: IllegalArgumentException) {
            Log.e(TAG, "Fel vid konvertering av String till PlantCategory: $value", e)
            null
        }
    }
} 