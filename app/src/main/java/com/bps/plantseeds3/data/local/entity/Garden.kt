package com.bps.plantseeds3.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "gardens")
data class Garden(
    @PrimaryKey
    val id: String,
    
    @ColumnInfo(name = "name")
    val name: String,
    
    @ColumnInfo(name = "description")
    val description: String?,
    
    @ColumnInfo(name = "location")
    val location: String?,
    
    @ColumnInfo(name = "size")
    val size: Float?,
    
    @ColumnInfo(name = "width")
    val width: Float?,
    
    @ColumnInfo(name = "length")
    val length: Float?,
    
    @ColumnInfo(name = "elevation")
    val elevation: Float?,
    
    @ColumnInfo(name = "slope")
    val slope: Float?,
    
    @ColumnInfo(name = "soil_type")
    val soilType: String?,
    
    @ColumnInfo(name = "sun_exposure")
    val sunExposure: String?,
    
    @ColumnInfo(name = "irrigation")
    val irrigation: String?,
    
    @ColumnInfo(name = "fence")
    val fence: Boolean = false,
    
    @ColumnInfo(name = "notes")
    val notes: String?,
    
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
) 