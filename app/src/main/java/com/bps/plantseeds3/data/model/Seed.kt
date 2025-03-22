package com.bps.plantseeds3.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.PropertyName
import java.util.Date

data class Seed(
    @DocumentId
    override val id: String = "",
    @PropertyName("name")
    val name: String = "",
    @PropertyName("scientificName")
    val scientificName: String? = null,
    @PropertyName("species")
    val species: String? = null,
    @PropertyName("variety")
    val variety: String? = null,
    @PropertyName("category")
    val category: String? = null,
    @PropertyName("description")
    val description: String? = null,
    @PropertyName("imageUrls")
    val imageUrls: List<String> = emptyList(),
    @PropertyName("needsPreparation")
    val needsPreparation: Boolean = false,
    @PropertyName("preparationDescription")
    val preparationDescription: String? = null,
    @PropertyName("plantingInstructions")
    val plantingInstructions: String? = null,
    @PropertyName("plantingDepth")
    val plantingDepth: Double? = null,
    @PropertyName("plantingDistance")
    val plantingDistance: Double? = null,
    @PropertyName("plantingDates")
    val plantingDates: String? = null,
    @PropertyName("harvestPeriod")
    val harvestPeriod: String? = null,
    @PropertyName("maintenanceDates")
    val maintenanceDates: String? = null,
    @PropertyName("includeInTimeline")
    val includeInTimeline: Boolean = true,
    @PropertyName("notes")
    val notes: String? = null,
    @PropertyName("tags")
    val tags: List<String> = emptyList(),
    @PropertyName("userId")
    val userId: String? = null,
    @PropertyName("createdAt")
    val createdAt: Date? = null,
    @PropertyName("updatedAt")
    val updatedAt: Date? = null,
    @PropertyName("sowingInstructions")
    val sowingInstructions: String? = null,
    @PropertyName("growingInstructions")
    val growingInstructions: String? = null,
    @PropertyName("harvestInstructions")
    val harvestInstructions: String? = null,
    @PropertyName("daysToGermination")
    val daysToGermination: Int? = null,
    @PropertyName("daysToHarvest")
    val daysToHarvest: Int? = null,
    @PropertyName("plantSpacing")
    val plantSpacing: Double? = null,
    @PropertyName("rowSpacing")
    val rowSpacing: Double? = null,
    @PropertyName("sunRequirement")
    val sunRequirement: String? = null,
    @PropertyName("waterRequirement")
    val waterRequirement: String? = null,
    @PropertyName("soilRequirement")
    val soilRequirement: String? = null,
    @PropertyName("hardiness")
    val hardiness: String? = null,
    @PropertyName("companionPlants")
    val companionPlants: List<String> = emptyList(),
    @PropertyName("avoidPlants")
    val avoidPlants: List<String> = emptyList()
) : HasId

data class PlantingPeriod(
    @PropertyName("start_month")
    val startMonth: Int? = null,
    @PropertyName("end_month")
    val endMonth: Int? = null,
    @PropertyName("start_week")
    val startWeek: Int? = null,
    @PropertyName("end_week")
    val endWeek: Int? = null,
    @PropertyName("exact_date")
    val exactDate: Timestamp? = null,
    @PropertyName("description")
    val description: String = ""
)

data class GrowthPeriod(
    @PropertyName("days_after_planting")
    val daysAfterPlanting: Int? = null,
    @PropertyName("start_month")
    val startMonth: Int? = null,
    @PropertyName("end_month")
    val endMonth: Int? = null,
    @PropertyName("start_week")
    val startWeek: Int? = null,
    @PropertyName("end_week")
    val endWeek: Int? = null,
    @PropertyName("exact_date")
    val exactDate: Timestamp? = null,
    @PropertyName("description")
    val description: String = ""
)

data class Timeline(
    @PropertyName("include_in_timeline")
    val includeInTimeline: Boolean = true
)

data class Planting(
    @PropertyName("preparation")
    val preparation: String = "",
    @PropertyName("planting_depth")
    val plantingDepth: Double = 0.0,
    @PropertyName("best_planting_time")
    val bestPlantingTime: List<String> = emptyList()
)

data class GrowingConditions(
    @PropertyName("light")
    val light: String = ""
)

data class Harvest(
    @PropertyName("days_to_harvest")
    val daysToHarvest: Int = 0,
    @PropertyName("harvest_time")
    val harvestTime: List<String> = emptyList()
)

data class Companions(
    @PropertyName("good")
    val good: List<String> = emptyList(),
    @PropertyName("bad")
    val bad: List<String> = emptyList()
)

data class PestsAndDiseases(
    @PropertyName("common_pests")
    val commonPests: List<String> = emptyList()
)

data class Guide(
    @PropertyName("name")
    val name: String = "",
    @PropertyName("overview")
    val overview: String = "",
    @PropertyName("instructions")
    val instructions: String = ""
)

data class Source(
    @PropertyName("name")
    val name: String = "OpenFarm",
    @PropertyName("url")
    val url: String = "https://openfarm.cc",
    @PropertyName("license")
    val license: String = "CC BY-SA 4.0",
    @PropertyName("last_updated")
    val lastUpdated: String = ""
) 