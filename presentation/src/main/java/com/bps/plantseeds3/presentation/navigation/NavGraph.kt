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

@Composable
fun NavGraph(navController: NavHostController) {
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

            composable(Screen.AddSeed.route) {
                AddSeedScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onSaveSeed = { name, species, description ->
                        // TODO: Implementera sparande av frö
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.SeedDetail.route,
                arguments = listOf(
                    navArgument("seedId") { type = NavType.StringType }
                )
            ) {
                SeedDetailScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onEditSeed = { /* TODO: Implementera redigering */ },
                    onDeleteSeed = { navController.popBackStack() }
                )
            }

            composable(Screen.GardenList.route) {
                GardenListScreen(
                    onNavigateToGardenDetail = { /* TODO: Implementera navigering */ }
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen()
            }
        }
    }
} 