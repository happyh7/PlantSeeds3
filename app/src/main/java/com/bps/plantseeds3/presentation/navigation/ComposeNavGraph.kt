package com.bps.plantseeds3.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bps.plantseeds3.presentation.screens.gardens.GardensScreen
import com.bps.plantseeds3.presentation.screens.plants.PlantsScreen
import com.bps.plantseeds3.presentation.screens.seeds.SeedsScreen

@Composable
fun ComposeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Plants.route
    ) {
        // Plants
        composable(Screen.Plants.route) {
            PlantsScreen(
                onAddClick = { navController.navigate(Screen.AddPlant.route) },
                onPlantClick = { plantId -> navController.navigate(Screen.PlantDetails.createRoute(plantId)) }
            )
        }
        composable(Screen.AddPlant.route) {
            // TODO: Implementera AddPlantScreen
        }
        composable(
            route = Screen.PlantDetails.route,
            arguments = listOf(navArgument("plantId") { type = NavType.StringType })
        ) {
            // TODO: Implementera PlantDetailsScreen
        }

        // Gardens
        composable(Screen.Gardens.route) {
            GardensScreen(
                onAddClick = { navController.navigate(Screen.AddGarden.route) },
                onGardenClick = { gardenId -> navController.navigate(Screen.GardenDetails.createRoute(gardenId)) }
            )
        }
        composable(Screen.AddGarden.route) {
            // TODO: Implementera AddGardenScreen
        }
        composable(
            route = Screen.GardenDetails.route,
            arguments = listOf(navArgument("gardenId") { type = NavType.StringType })
        ) {
            // TODO: Implementera GardenDetailsScreen
        }

        // Seeds
        composable(Screen.Seeds.route) {
            SeedsScreen(
                onAddClick = { navController.navigate(Screen.AddSeed.route) },
                onSeedClick = { seedId -> navController.navigate(Screen.SeedDetails.createRoute(seedId)) }
            )
        }
        composable(Screen.AddSeed.route) {
            // TODO: Implementera AddSeedScreen
        }
        composable(
            route = Screen.SeedDetails.route,
            arguments = listOf(navArgument("seedId") { type = NavType.StringType })
        ) {
            // TODO: Implementera SeedDetailsScreen
        }
    }
} 