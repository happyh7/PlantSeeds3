package com.bps.plantseeds3.presentation.navigation

sealed class Screen(val route: String) {
    object GardenOverview : Screen("garden_overview")
    object GardenDetail : Screen("garden_detail/{gardenId}") {
        fun createRoute(gardenId: String) = "garden_detail/$gardenId"
    }
    object SeedList : Screen("seed_list")
    object SeedDetail : Screen("seed_detail/{seedId}") {
        fun createRoute(seedId: String) = "seed_detail/$seedId"
    }
    object AddSeed : Screen("add_seed")
    object EditSeed : Screen("edit_seed/{seedId}") {
        fun createRoute(seedId: String) = "edit_seed/$seedId"
    }
    object Settings : Screen("settings")
} 