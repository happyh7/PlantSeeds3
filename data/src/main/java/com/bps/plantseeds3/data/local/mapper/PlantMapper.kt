package com.bps.plantseeds3.data.local.mapper

import com.bps.plantseeds3.data.local.entity.PlantEntity
import com.bps.plantseeds3.domain.model.Plant
import java.time.Instant

fun Plant.toEntity(): PlantEntity {
    return PlantEntity(
        id = id,
        name = name,
        species = species,
        description = description ?: "",
        gardenId = gardenId ?: "",
        lastWatered = lastWatered?.toEpochMilli() ?: Instant.now().toEpochMilli(),
        nextWatering = nextWatering?.toEpochMilli() ?: Instant.now().plusSeconds(60 * 60 * 24 * 7).toEpochMilli(),
        createdAt = createdAt.toEpochMilli(),
        updatedAt = updatedAt.toEpochMilli()
    )
}

fun PlantEntity.toPlant(): Plant {
    return Plant(
        id = id,
        name = name,
        species = species,
        description = description.takeIf { !it.isNullOrEmpty() },
        gardenId = gardenId.takeIf { it.isNotBlank() },
        lastWatered = Instant.ofEpochMilli(lastWatered),
        nextWatering = Instant.ofEpochMilli(nextWatering),
        createdAt = Instant.ofEpochMilli(createdAt),
        updatedAt = Instant.ofEpochMilli(updatedAt)
    )
} 