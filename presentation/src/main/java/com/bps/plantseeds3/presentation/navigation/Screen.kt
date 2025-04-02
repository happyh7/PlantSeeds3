package com.bps.plantseeds3.presentation.navigation

sealed class Screen(val route: String) {
    object SeedList : Screen("seedList")
    object SeedDetail : Screen("seedDetail/{seedId}") {
        fun createRoute(seedId: String) = "seedDetail/$seedId"
    }
} 