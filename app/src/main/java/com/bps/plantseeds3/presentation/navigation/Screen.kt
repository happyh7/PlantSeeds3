package com.bps.plantseeds3.presentation.navigation

sealed class Screen(val route: String) {
    object Plants : Screen("plants")
    object AddPlant : Screen("add_plant")
    object PlantDetails : Screen("plant_details/{plantId}") {
        fun createRoute(plantId: String) = "plant_details/$plantId"
    }

    object Gardens : Screen("gardens")
    object AddGarden : Screen("add_garden")
    object GardenDetails : Screen("garden_details/{gardenId}") {
        fun createRoute(gardenId: String) = "garden_details/$gardenId"
    }

    object Seeds : Screen("seeds")
    object AddSeed : Screen("add_seed")
    object SeedDetails : Screen("seed_details/{seedId}") {
        fun createRoute(seedId: String) = "seed_details/$seedId"
    }
} 