package com.bps.plantseeds3.data.mapper

import com.bps.plantseeds3.data.local.entity.GardenEntity
import com.bps.plantseeds3.domain.model.Garden
import java.time.Instant

fun GardenEntity.toGarden(): Garden {
    return Garden(
        id = id,
        name = name,
        description = description,
        location = location,
        createdAt = Instant.ofEpochMilli(createdAt),
        updatedAt = Instant.ofEpochMilli(updatedAt)
    )
}

fun Garden.toEntity(): GardenEntity {
    return GardenEntity(
        id = id,
        name = name,
        description = description ?: "",
        location = location ?: "",
        createdAt = createdAt.toEpochMilli(),
        updatedAt = updatedAt.toEpochMilli()
    )
} 