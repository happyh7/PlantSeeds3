package com.bps.plantseeds3.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object Gardens : Screen("gardens")
    data object Plants : Screen("plants")
    data object Seeds : Screen("seeds")
    
    data object AddEditGarden : Screen("add_edit_garden")
    data object AddEditPlant : Screen("add_edit_plant")
    data object AddEditSeed : Screen("add_edit_seed")
} 