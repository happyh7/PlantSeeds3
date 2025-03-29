package com.bps.plantseeds3.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object Gardens : Screen("gardens")
    data object Plants : Screen("plants")
    data object Seeds : Screen("seeds")
    
    data object AddEditGarden : Screen("add_edit_garden")
    data object AddEditPlant : Screen("add_edit_plant")
    data object AddEditSeed : Screen("add_edit_seed?seedId={seedId}") {
        fun createRoute(seedId: String = "") = "add_edit_seed?seedId=$seedId"
    }
    
    data object GardenDetails : Screen("garden_details?gardenId={gardenId}") {
        fun createRoute(gardenId: String) = "garden_details?gardenId=$gardenId"
    }
} 