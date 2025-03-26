package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.data.local.entity.Seed
import com.bps.plantseeds3.domain.repository.SeedRepository
import java.util.UUID
import javax.inject.Inject

class AddSeedUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(
        name: String,
        scientificName: String = "",
        species: String = "",
        variety: String = "",
        category: String = "",
        description: String = "",
        plantingDepth: Float = 0f,
        plantingDistance: Float = 0f,
        plantSpacing: Float = 0f,
        rowSpacing: Float = 0f,
        plantingDates: String = "",
        sunRequirement: String = "",
        waterRequirement: String = "",
        soilRequirement: String = "",
        soilPh: String = "",
        hardiness: String = "",
        sowingInstructions: String = "",
        growingInstructions: String = "",
        harvestInstructions: String = "",
        storageInstructions: String = "",
        daysToGermination: Int = 0,
        daysToHarvest: Int = 0,
        harvestPeriod: String = "",
        lifespan: String = "",
        maintenanceDates: String = "",
        fertilizingSchedule: String = "",
        pruningSchedule: String = "",
        companionPlants: String = "",
        avoidPlants: String = "",
        height: Float = 0f,
        spread: Float = 0f,
        yield: String = "",
        culinaryUses: String = "",
        medicinalUses: String = "",
        tags: String = "",
        notes: String = "",
        imageUrl: String = "",
        source: String = ""
    ) {
        val seed = Seed(
            id = UUID.randomUUID().toString(),
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            category = category,
            description = description,
            plantingDepth = plantingDepth,
            plantingDistance = plantingDistance,
            plantSpacing = plantSpacing,
            rowSpacing = rowSpacing,
            plantingDates = plantingDates,
            sunRequirement = sunRequirement,
            waterRequirement = waterRequirement,
            soilRequirement = soilRequirement,
            soilPh = soilPh,
            hardiness = hardiness,
            sowingInstructions = sowingInstructions,
            growingInstructions = growingInstructions,
            harvestInstructions = harvestInstructions,
            storageInstructions = storageInstructions,
            daysToGermination = daysToGermination,
            daysToHarvest = daysToHarvest,
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
            source = source
        )
        repository.insertSeed(seed)
    }
} 