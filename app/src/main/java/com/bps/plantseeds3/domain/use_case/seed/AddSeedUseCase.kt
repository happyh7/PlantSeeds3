package com.bps.plantseeds3.domain.use_case.seed

import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.domain.repository.SeedRepository
import javax.inject.Inject

class AddSeedUseCase @Inject constructor(
    private val repository: SeedRepository
) {
    suspend operator fun invoke(
        name: String,
        scientificName: String = "",
        species: String = "",
        variety: String = "",
        category: PlantCategory = PlantCategory.OTHER,
        description: String = "",
        plantingDepth: String = "",
        plantingDistance: String = "",
        plantSpacing: Float? = null,
        rowSpacing: Float? = null,
        plantingDates: String = "",
        sunRequirement: String = "",
        waterRequirement: String = "",
        soilType: String = "",
        soilPh: Double? = null,
        hardinessZone: String = "",
        sowingInstructions: String = "",
        growingInstructions: String = "",
        harvestingInstructions: String = "",
        storageInstructions: String = "",
        daysToGermination: Int? = null,
        daysToMaturity: Int? = null,
        harvestPeriod: String = "",
        lifespan: String = "",
        maintenanceDates: String = "",
        fertilizingSchedule: String = "",
        pruningSchedule: String = "",
        companionPlants: String = "",
        avoidPlants: String = "",
        height: Float? = null,
        spread: Float? = null,
        yield: String = "",
        culinaryUses: String = "",
        medicinalUses: String = "",
        tags: String = "",
        notes: String = "",
        imageUrl: String = "",
        source: String = ""
    ) {
        val seed = Seed(
            name = name,
            scientificName = scientificName,
            species = species,
            variety = variety,
            category = category,
            description = description,
            plantingDepth = plantingDepth.toDoubleOrNull(),
            plantingDistance = plantingDistance.toDoubleOrNull(),
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
            source = source
        )
        repository.insertSeed(seed)
    }
} 