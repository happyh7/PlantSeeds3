package com.bps.plantseeds3.data.mapper

import com.bps.plantseeds3.data.local.entity.GardenEntity
import com.bps.plantseeds3.domain.model.Garden
import java.time.Instant

fun GardenEntity.toGarden(): Garden {
    return Garden(
        id = id.toString(),
        name = name,
        description = description,
        location = location,
        createdAt = Instant.ofEpochMilli(createdAt),
        updatedAt = Instant.ofEpochMilli(createdAt) // Vi använder createdAt som updatedAt eftersom det inte finns i entiteten
    )
}

fun Garden.toEntity(): GardenEntity {
    return GardenEntity(
        id = if (id.isEmpty()) 0 else id.toLong(),
        name = name,
        description = description ?: "",
        location = location ?: "",
        size = 0.0, // Default värde eftersom det inte finns i domänmodellen
        createdAt = createdAt.toEpochMilli()
    )
} 