package com.bps.plantseeds3.presentation.navigation

sealed class Screen(val route: String) {
    object Plants : Screen("plants/{gardenId}") {
        fun createRoute(gardenId: String) = "plants/$gardenId"
    }
    object AddPlant : Screen("add_plant")
    object PlantDetails : Screen("plant/{plantId}") {
        fun createRoute(plantId: String) = "plant/$plantId"
    }
    object Gardens : Screen("gardens")
    object AddGarden : Screen("add_garden")
    object GardenDetails : Screen("garden/{gardenId}") {
        fun createRoute(gardenId: String) = "garden/$gardenId"
    }
    object Seeds : Screen("seeds")
    object AddSeed : Screen("add_seed")
    object SeedDetails : Screen("seed/{seedId}") {
        fun createRoute(seedId: String) = "seed/$seedId"
    }
} 