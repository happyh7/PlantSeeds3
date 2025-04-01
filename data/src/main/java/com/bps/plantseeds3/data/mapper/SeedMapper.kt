package com.bps.plantseeds3.data.mapper

import com.bps.plantseeds3.data.local.entity.SeedEntity
import com.bps.plantseeds3.domain.model.Seed
import java.time.Instant

fun Seed.toEntity(): SeedEntity {
    return SeedEntity(
        id = id,
        plantId = plantId,
        name = name,
        species = species,
        description = description,
        plantingInstructions = plantingInstructions,
        daysToGermination = daysToGermination,
        daysToHarvest = daysToHarvest,
        lightNeeds = lightNeeds,
        waterNeeds = waterNeeds,
        soilType = soilType,
        temperature = temperature,
        spacing = spacing,
        companionPlants = companionPlants,
        avoidPlants = avoidPlants,
        imageUrl = imageUrl,
        createdAt = createdAt.toEpochMilli(),
        updatedAt = updatedAt.toEpochMilli(),
        isSynced = isSynced
    )
}

fun SeedEntity.toSeed(): Seed {
    return Seed(
        id = id,
        plantId = plantId,
        name = name,
        species = species,
        description = description,
        plantingInstructions = plantingInstructions,
        daysToGermination = daysToGermination,
        daysToHarvest = daysToHarvest,
        lightNeeds = lightNeeds,
        waterNeeds = waterNeeds,
        soilType = soilType,
        temperature = temperature,
        spacing = spacing,
        companionPlants = companionPlants,
        avoidPlants = avoidPlants,
        imageUrl = imageUrl,
        createdAt = Instant.ofEpochMilli(createdAt),
        updatedAt = Instant.ofEpochMilli(updatedAt),
        isSynced = isSynced
    )
} 