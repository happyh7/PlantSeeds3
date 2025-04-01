package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gardens")
data class GardenEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String,
    val location: String,
    val createdAt: Long,
    val updatedAt: Long
) 