package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "plants",
    indices = [
        Index(value = ["name"]),
        Index(value = ["species"]),
        Index(value = ["description"]),
        Index(value = ["gardenId"])
    ]
)
data class PlantEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val species: String,
    val description: String?,
    val gardenId: String,
    val lastWatered: Long,
    val nextWatering: Long,
    val createdAt: Long,
    val updatedAt: Long
) 