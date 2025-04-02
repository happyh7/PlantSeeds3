package com.bps.plantseeds3.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bps.plantseeds3.presentation.ui.components.BottomNavigationBar
import com.bps.plantseeds3.presentation.ui.screens.*
import com.bps.plantseeds3.presentation.ui.viewmodel.SeedListViewModel
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun NavGraph(navController: NavHostController) {
    val seedListViewModel: SeedListViewModel = hiltViewModel()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screen.SeedList.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screen.SeedList.route) {
                SeedListScreen(
                    onNavigateToSeedDetail = { seedId ->
                        navController.navigate(Screen.SeedDetail.createRoute(seedId))
                    },
                    onNavigateToAddSeed = {
                        navController.navigate(Screen.AddSeed.route)
                    }
                )
            }

            composable(
                route = Screen.SeedDetail.route,
                arguments = listOf(
                    navArgument("seedId") { type = NavType.StringType }
                )
            ) { backStackEntry ->
                val seedId = backStackEntry.arguments?.getString("seedId") ?: return@composable
                SeedDetailScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onEditSeed = {
                        navController.navigate(Screen.EditSeed.createRoute(seedId))
                    },
                    onDeleteSeed = {
                        navController.popBackStack()
                        seedListViewModel.loadSeeds()
                    }
                )
            }

            composable(
                route = Screen.EditSeed.route,
                arguments = listOf(
                    navArgument("seedId") {
                        type = NavType.StringType
                    }
                )
            ) { backStackEntry ->
                EditSeedScreen(
                    onNavigateBack = {
                        navController.popBackStack()
                    },
                    onSaveSeed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.AddSeed.route) {
                AddSeedScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onSaveSeed = { _, _, _ ->
                        seedListViewModel.loadSeeds()
                        navController.popBackStack()
                    }
                )
            }

            composable(Screen.GardenList.route) {
                GardenListScreen(
                    onNavigateToGardenDetail = { /* TODO: Implementera navigering */ }
                )
            }

            composable(
                route = Screen.Settings.route
            ) {
                SettingsScreen()
            }
        }
    }
} 