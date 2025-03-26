package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.bps.plantseeds3.data.local.converter.LocalDateConverter
import java.time.LocalDate

@Entity(tableName = "gardens")
@TypeConverters(LocalDateConverter::class)
data class Garden(
    @PrimaryKey
    val id: String,
    val name: String,
    val location: String? = null,
    val description: String? = null,
    val size: Float? = null, // i kvadratmeter
    val width: Float? = null, // i meter
    val length: Float? = null, // i meter
    val elevation: Float? = null, // i meter över havet
    val slope: Float? = null, // i grader
    val soilType: String? = null,
    val sunExposure: String? = null,
    val irrigation: String? = null,
    val fence: String? = null,
    val notes: String? = null,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now()
) 