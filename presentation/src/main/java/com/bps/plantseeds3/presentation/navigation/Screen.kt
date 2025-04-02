package com.bps.plantseeds3.presentation.navigation

sealed class Screen(val route: String) {
    object SeedList : Screen("seed_list")
    object SeedDetail : Screen("seed_detail/{seedId}") {
        fun createRoute(seedId: String) = "seed_detail/$seedId"
    }
    object EditSeed : Screen("edit_seed/{seedId}") {
        fun createRoute(seedId: String) = "edit_seed/$seedId"
    }
    object AddSeed : Screen("add_seed")
    object GardenList : Screen("garden_list")
    object PlantList : Screen("plant_list")
    object Settings : Screen("settings")
} 