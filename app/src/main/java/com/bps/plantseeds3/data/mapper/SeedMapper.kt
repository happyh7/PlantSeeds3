package com.bps.plantseeds3.data.mapper

import android.util.Log
import com.bps.plantseeds3.data.local.entity.Seed as SeedEntity
import com.bps.plantseeds3.domain.model.Seed as SeedDomain
import com.bps.plantseeds3.domain.model.PlantCategory
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date
import javax.inject.Inject

class SeedMapper @Inject constructor() : BaseMapper<SeedEntity, SeedDomain>() {
    override val TAG = "SeedMapper"

    override fun SeedEntity.convertToDomain(): SeedDomain {
        Log.d(TAG, "Kategori från databasen: $category")
        val plantCategory = safeConvertEnum(category, PlantCategory::fromName, PlantCategory.OTHER)
        Log.d(TAG, "Konverterad kategori: $plantCategory")
        
        return SeedDomain(
            id = id.toLongOrNull() ?: 0,
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            description = description,
            category = plantCategory,
            quantity = quantity,
            unit = unit,
            purchaseDate = purchaseDate?.let { Date.from(it.atStartOfDay().toInstant(java.time.ZoneOffset.UTC)) },
            expiryDate = expiryDate?.let { Date.from(it.atStartOfDay().toInstant(java.time.ZoneOffset.UTC)) },
            supplier = supplier,
            price = price,
            currency = currency,
            isFavorite = isFavorite,
            plantingDepth = plantingDepth,
            plantingDistance = plantingDistance?.toFloatOrNull(),
            plantSpacing = plantSpacing?.toFloatOrNull(),
            rowSpacing = rowSpacing?.toFloatOrNull(),
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
            notes = notes?.joinToString("\n"),
            imageUrl = imageUrl,
            source = source,
            lastPlanted = lastPlanted?.let { Date.from(it.atStartOfDay().toInstant(java.time.ZoneOffset.UTC)) },
            lastHarvested = lastHarvested?.let { Date.from(it.atStartOfDay().toInstant(java.time.ZoneOffset.UTC)) },
            createdAt = Date.from(createdAt.toInstant(java.time.ZoneOffset.UTC)),
            updatedAt = Date.from(updatedAt.toInstant(java.time.ZoneOffset.UTC))
        )
    }

    override fun SeedDomain.convertToEntity(): SeedEntity {
        return SeedEntity(
            id = id.toString(),
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            description = description,
            category = category ?: PlantCategory.VEGETABLE,
            quantity = quantity,
            unit = unit,
            purchaseDate = purchaseDate?.toInstant()?.atZone(java.time.ZoneOffset.UTC)?.toLocalDate(),
            expiryDate = expiryDate?.toInstant()?.atZone(java.time.ZoneOffset.UTC)?.toLocalDate(),
            supplier = supplier,
            price = price,
            currency = currency,
            isFavorite = isFavorite,
            plantingDepth = plantingDepth,
            plantingDistance = plantingDistance?.toString(),
            plantSpacing = plantSpacing?.toString(),
            rowSpacing = rowSpacing?.toString(),
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
            notes = notes?.split("\n") ?: emptyList(),
            imageUrl = imageUrl,
            source = source,
            lastPlanted = lastPlanted?.toInstant()?.atZone(java.time.ZoneOffset.UTC)?.toLocalDate(),
            lastHarvested = lastHarvested?.toInstant()?.atZone(java.time.ZoneOffset.UTC)?.toLocalDate(),
            createdAt = createdAt.toInstant().atZone(java.time.ZoneOffset.UTC).toLocalDateTime(),
            updatedAt = updatedAt.toInstant().atZone(java.time.ZoneOffset.UTC).toLocalDateTime()
        )
    }
} 