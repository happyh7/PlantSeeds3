package com.bps.plantseeds3.data.local.converter

import android.util.Log
import androidx.room.TypeConverter
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset

class Converters {
    private val TAG = "Converters"
    private val gson = Gson()
    private val listType = object : TypeToken<List<String>>() {}.type

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDate? {
        Log.d(TAG, "Konverterar timestamp till Date: $value")
        return value?.let { LocalDate.ofEpochDay(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): Long? {
        Log.d(TAG, "Konverterar Date till timestamp: $date")
        return date?.toEpochDay()
    }

    @TypeConverter
    fun fromDateTime(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateTimeToTimestamp(dateTime: LocalDateTime?): Long? {
        return dateTime?.toEpochSecond(ZoneOffset.UTC)
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
    fun fromStringList(value: String?): List<String> {
        Log.d(TAG, "Konverterar List<String> till JSON: $value")
        return value?.let { gson.fromJson(it, listType) } ?: emptyList()
    }

    @TypeConverter
    fun toStringList(list: List<String>?): String? {
        Log.d(TAG, "Konverterar List<String> till JSON: $list")
        return list?.let { gson.toJson(it) }
    }
} 