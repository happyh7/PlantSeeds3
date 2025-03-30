package com.bps.plantseeds3.data.mapper

import android.util.Log
import com.bps.plantseeds3.data.local.entity.Plant as PlantEntity
import com.bps.plantseeds3.domain.model.Plant as PlantDomain
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.model.PlantStatus
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class PlantMapper @Inject constructor() : BaseMapper<PlantEntity, PlantDomain>() {
    override val TAG = "PlantMapper"

    override fun PlantEntity.convertToDomain(): PlantDomain {
        return PlantDomain(
            id = id,
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            description = description,
            category = category,
            status = status,
            plantingDate = plantingDate,
            expectedHarvestDate = expectedHarvestDate,
            actualHarvestDate = actualHarvestDate,
            sowingDepth = sowingDepth,
            spacing = spacing,
            daysToGermination = daysToGermination,
            daysToMaturity = daysToMaturity,
            sunRequirement = sunRequirement,
            waterRequirement = waterRequirement,
            soilRequirement = soilRequirement,
            soilPh = soilPh,
            hardiness = hardiness,
            sowingInstructions = sowingInstructions,
            growingInstructions = growingInstructions,
            harvestInstructions = harvestInstructions,
            storageInstructions = storageInstructions,
            companionPlants = companionPlants,
            avoidPlants = avoidPlants,
            height = height,
            spread = spread,
            yield = yield,
            culinaryUses = culinaryUses,
            medicinalUses = medicinalUses,
            tags = tags,
            notes = notes,
            gardenId = gardenId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    override fun PlantDomain.convertToEntity(): PlantEntity {
        return PlantEntity(
            id = id,
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            description = description,
            category = category,
            status = status,
            plantingDate = plantingDate,
            expectedHarvestDate = expectedHarvestDate,
            actualHarvestDate = actualHarvestDate,
            sowingDepth = sowingDepth,
            spacing = spacing,
            daysToGermination = daysToGermination,
            daysToMaturity = daysToMaturity,
            sunRequirement = sunRequirement,
            waterRequirement = waterRequirement,
            soilRequirement = soilRequirement,
            soilPh = soilPh,
            hardiness = hardiness,
            sowingInstructions = sowingInstructions,
            growingInstructions = growingInstructions,
            harvestInstructions = harvestInstructions,
            storageInstructions = storageInstructions,
            companionPlants = companionPlants,
            avoidPlants = avoidPlants,
            height = height,
            spread = spread,
            yield = yield,
            culinaryUses = culinaryUses,
            medicinalUses = medicinalUses,
            tags = tags,
            notes = notes,
            gardenId = gardenId,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
} 