package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gardens")
data class GardenEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val description: String,
    val location: String,
    val size: Double,
    val createdAt: Long = System.currentTimeMillis()
) 