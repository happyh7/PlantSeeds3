package com.bps.plantseeds3.common.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
abstract class BaseEntity {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()
} 