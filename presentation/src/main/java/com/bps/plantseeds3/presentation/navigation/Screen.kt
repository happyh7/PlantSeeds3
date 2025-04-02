package com.bps.plantseeds3.presentation.navigation

sealed class Screen(val route: String) {
    object SeedList : Screen("seeds")
    object AddSeed : Screen("seeds/add")
    object SeedDetail : Screen("seeds/{seedId}") {
        fun createRoute(seedId: String) = "seeds/$seedId"
    }
    object GardenList : Screen("gardens")
    object GardenDetail : Screen("gardens/{gardenId}") {
        fun createRoute(gardenId: String) = "gardens/$gardenId"
    }
    object Profile : Screen("profile")
} 