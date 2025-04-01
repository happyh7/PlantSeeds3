package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val scientificName: String,
    val description: String,
    val growthTime: Int,
    val waterNeeds: Int,
    val sunNeeds: Int,
    val createdAt: Long = System.currentTimeMillis()
) 