package com.bps.plantseeds3.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bps.plantseeds3.presentation.plants.PlantsScreen
import com.bps.plantseeds3.presentation.plants.add_edit.AddEditPlantScreen
import com.bps.plantseeds3.presentation.plants.detail.PlantDetailScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "plants"
    ) {
        composable("plants") {
            PlantsScreen(navController = navController)
        }

        composable(
            route = "add_edit_plant?plantId={plantId}&gardenId={gardenId}",
            arguments = listOf(
                navArgument("plantId") {
                    type = androidx.navigation.NavType.StringType
                    nullable = true
                    defaultValue = "-1"
                },
                navArgument("gardenId") {
                    type = androidx.navigation.NavType.StringType
                    nullable = true
                    defaultValue = "-1"
                }
            )
        ) { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")
            val gardenId = backStackEntry.arguments?.getString("gardenId")
            AddEditPlantScreen(navController = navController)
        }

        composable("plant/{plantId}") { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")?.toIntOrNull()
            PlantDetailScreen(
                plantId = plantId,
                navController = navController
            )
        }
    }
} 