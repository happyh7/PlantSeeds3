package com.bps.plantseeds3.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.plants.PlantsScreen
import com.bps.plantseeds3.presentation.plants.add_edit.AddEditPlantScreen
import com.bps.plantseeds3.presentation.plants.detail.PlantDetailScreen
import com.bps.plantseeds3.presentation.gardens.GardensScreen
import com.bps.plantseeds3.presentation.gardens.add_edit.AddEditGardenScreen
import com.bps.plantseeds3.presentation.gardens.details.GardenDetailsScreen
import com.bps.plantseeds3.presentation.seeds.SeedBankScreen
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedScreen
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedViewModel
import com.bps.plantseeds3.presentation.seeds.list.SeedListScreen

sealed class Screen(val route: String) {
    object SeedList : Screen("seed_list")
    object AddEditSeed : Screen("add_edit_seed?seedId={seedId}") {
        fun createRoute(seedId: String = "") = "add_edit_seed?seedId=$seedId"
    }
}

@Composable
fun ComposeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "plants"
    ) {
        composable("plants") {
            PlantsScreen(navController = navController)
        }

        composable("gardens") {
            GardensScreen(navController = navController)
        }

        composable("seeds") {
            SeedBankScreen(
                onNavigateToAddEditSeed = { seedId ->
                    navController.navigate(Screen.AddEditSeed.createRoute(seedId ?: ""))
                }
            )
        }

        composable(
            route = "add_edit_garden?gardenId={gardenId}",
            arguments = listOf(
                navArgument("gardenId") {
                    type = androidx.navigation.NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { entry ->
            val gardenId = entry.arguments?.getString("gardenId")
            AddEditGardenScreen(
                gardenId = gardenId,
                navController = navController
            )
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
        ) { entry ->
            val plantId = entry.arguments?.getString("plantId")
            val gardenId = entry.arguments?.getString("gardenId")
            AddEditPlantScreen(navController = navController)
        }

        composable("plant/{plantId}") { backStackEntry ->
            val plantId = backStackEntry.arguments?.getString("plantId")?.toIntOrNull()
            PlantDetailScreen(
                plantId = plantId,
                navController = navController
            )
        }

        composable(
            route = "garden_details?gardenId={gardenId}",
            arguments = listOf(
                navArgument("gardenId") {
                    type = androidx.navigation.NavType.StringType
                    nullable = false
                }
            )
        ) { backStackEntry ->
            val gardenId = backStackEntry.arguments?.getString("gardenId") ?: return@composable
            GardenDetailsScreen(
                gardenId = gardenId,
                navController = navController
            )
        }

        composable(route = Screen.SeedList.route) {
            SeedListScreen(
                navController = navController,
                viewModel = hiltViewModel()
            )
        }
        
        composable(
            route = Screen.AddEditSeed.route,
            arguments = listOf(
                navArgument("seedId") {
                    type = NavType.StringType
                    nullable = true
                    defaultValue = null
                }
            )
        ) { entry ->
            val seedId = entry.arguments?.getString("seedId")
            AddEditSeedScreen(
                seedId = seedId,
                navController = navController
            )
        }
    }
} 