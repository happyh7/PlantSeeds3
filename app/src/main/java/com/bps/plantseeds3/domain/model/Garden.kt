package com.bps.plantseeds3.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gardens")
data class Garden(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String? = null,
    val location: String? = null,
    val size: Float? = null,
    @ColumnInfo(name = "width")
    val width: Float? = null,
    @ColumnInfo(name = "length")
    val length: Float? = null,
    @ColumnInfo(name = "elevation")
    val elevation: Float? = null,
    @ColumnInfo(name = "slope")
    val slope: Float? = null,
    @ColumnInfo(name = "soil_type")
    val soilType: String? = null,
    @ColumnInfo(name = "sun_exposure")
    val sunExposure: String? = null,
    @ColumnInfo(name = "irrigation")
    val irrigation: String? = null,
    @ColumnInfo(name = "fence")
    val fence: String? = null,
    val notes: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long = System.currentTimeMillis()
) 