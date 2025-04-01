package com.bps.plantseeds3.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "seeds",
    foreignKeys = [
        ForeignKey(
            entity = PlantEntity::class,
            parentColumns = ["id"],
            childColumns = ["plantId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SeedEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val plantId: Long,
    val quantity: Int,
    val source: String,
    val purchaseDate: Long,
    val expiryDate: Long? = null,
    val notes: String? = null,
    val createdAt: Long = System.currentTimeMillis()
) 