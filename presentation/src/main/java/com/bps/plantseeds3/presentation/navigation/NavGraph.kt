package com.bps.plantseeds3.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bps.plantseeds3.garden.presentation.overview.GardenOverviewScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.GardenOverview.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(route = Screen.GardenOverview.route) {
            GardenOverviewScreen(
                onNavigateToGardenDetail = { gardenId ->
                    // TODO: Implementera navigering till trädgårdsdetaljer när skärmen är klar
                }
            )
        }
        
        composable(route = Screen.SeedList.route) {
            // TODO: Implementera SeedListScreen
        }
        
        composable(route = Screen.Settings.route) {
            // TODO: Implementera SettingsScreen
        }
    }
} 