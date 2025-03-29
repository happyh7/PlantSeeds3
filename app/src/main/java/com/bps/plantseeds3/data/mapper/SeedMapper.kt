package com.bps.plantseeds3.data.mapper

import android.util.Log
import com.bps.plantseeds3.data.local.entity.Seed as SeedEntity
import com.bps.plantseeds3.domain.model.Seed as SeedDomain
import com.bps.plantseeds3.domain.model.PlantCategory
import javax.inject.Inject

class SeedMapper @Inject constructor() : BaseMapper<SeedEntity, SeedDomain>() {
    override val TAG = "SeedMapper"

    override fun SeedEntity.convertToDomain(): SeedDomain {
        Log.d(TAG, "Kategori från databasen: $category")
        val plantCategory = safeConvertEnum(category, PlantCategory::fromName, PlantCategory.OTHER)
        Log.d(TAG, "Konverterad kategori: $plantCategory")
        
        return SeedDomain(
            id = id,
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            description = description,
            category = plantCategory,
            plantingDepth = plantingDepth,
            plantingDistance = plantingDistance,
            plantSpacing = plantSpacing,
            rowSpacing = rowSpacing,
            plantingDates = plantingDates,
            sunRequirement = sunRequirement,
            waterRequirement = waterRequirement,
            soilType = soilType,
            soilPh = soilPh,
            hardinessZone = hardinessZone,
            sowingInstructions = sowingInstructions,
            growingInstructions = growingInstructions,
            harvestingInstructions = harvestingInstructions,
            storageInstructions = storageInstructions,
            daysToGermination = daysToGermination,
            daysToMaturity = daysToMaturity,
            harvestPeriod = harvestPeriod,
            lifespan = lifespan,
            maintenanceDates = maintenanceDates,
            fertilizingSchedule = fertilizingSchedule,
            pruningSchedule = pruningSchedule,
            companionPlants = companionPlants,
            avoidPlants = avoidPlants,
            height = height,
            spread = spread,
            yield = yield,
            culinaryUses = culinaryUses,
            medicinalUses = medicinalUses,
            tags = tags,
            notes = notes,
            imageUrl = imageUrl,
            source = source,
            lastPlanted = lastPlanted,
            lastHarvested = lastHarvested,
            isFavorite = isFavorite,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    override fun SeedDomain.convertToEntity(): SeedEntity {
        return SeedEntity(
            id = id,
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            description = description,
            category = category.name,
            plantingDepth = plantingDepth,
            plantingDistance = plantingDistance,
            plantSpacing = plantSpacing,
            rowSpacing = rowSpacing,
            plantingDates = plantingDates,
            sunRequirement = sunRequirement,
            waterRequirement = waterRequirement,
            soilType = soilType,
            soilPh = soilPh,
            hardinessZone = hardinessZone,
            sowingInstructions = sowingInstructions,
            growingInstructions = growingInstructions,
            harvestingInstructions = harvestingInstructions,
            storageInstructions = storageInstructions,
            daysToGermination = daysToGermination,
            daysToMaturity = daysToMaturity,
            harvestPeriod = harvestPeriod,
            lifespan = lifespan,
            maintenanceDates = maintenanceDates,
            fertilizingSchedule = fertilizingSchedule,
            pruningSchedule = pruningSchedule,
            companionPlants = companionPlants,
            avoidPlants = avoidPlants,
            height = height,
            spread = spread,
            yield = yield,
            culinaryUses = culinaryUses,
            medicinalUses = medicinalUses,
            tags = tags,
            notes = notes,
            imageUrl = imageUrl,
            source = source,
            lastPlanted = lastPlanted,
            lastHarvested = lastHarvested,
            isFavorite = isFavorite,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
} 