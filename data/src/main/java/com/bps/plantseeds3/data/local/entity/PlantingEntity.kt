package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "plantings",
    foreignKeys = [
        ForeignKey(
            entity = GardenEntity::class,
            parentColumns = ["id"],
            childColumns = ["gardenId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PlantEntity::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("gardenId"),
        Index("plantId")
    ]
)
data class PlantingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val gardenId: Long,
    val plantId: Long,
    val quantity: Int,
    val plantedAt: Long = System.currentTimeMillis(),
    val harvestedAt: Long? = null,
    val notes: String? = null
) 