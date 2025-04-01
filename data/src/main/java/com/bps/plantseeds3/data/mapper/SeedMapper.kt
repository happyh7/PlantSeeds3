package com.bps.plantseeds3.data.mapper

import com.bps.plantseeds3.data.local.entity.SeedEntity
import com.bps.plantseeds3.domain.model.Seed
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

fun SeedEntity.toSeed(): Seed {
    return Seed(
        id = id.toString(),
        name = source,
        description = notes ?: "",
        plantingDate = null,
        germinationDate = null,
        harvestDate = Instant.ofEpochMilli(purchaseDate)
            .atZone(ZoneId.systemDefault())
            .toLocalDate(),
        notes = notes ?: "",
        imageUrl = null,
        createdAt = Instant.ofEpochMilli(createdAt)
            .atZone(ZoneId.systemDefault())
            .toLocalDate(),
        updatedAt = Instant.ofEpochMilli(createdAt)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    )
}

fun Seed.toSeedEntity(): SeedEntity {
    return SeedEntity(
        id = if (id.isEmpty()) 0 else id.toLong(),
        plantId = 0, // Detta behöver hanteras på något sätt
        quantity = 1, // Detta är en förenkling
        source = name,
        purchaseDate = createdAt.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
        expiryDate = null,
        notes = notes,
        createdAt = createdAt.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    )
} 