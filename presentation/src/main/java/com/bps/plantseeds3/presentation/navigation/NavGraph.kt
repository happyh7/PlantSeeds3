package com.bps.plantseeds3.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bps.plantseeds3.presentation.ui.screens.SeedDetailScreen
import com.bps.plantseeds3.presentation.ui.screens.SeedListScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.SeedList.route
    ) {
        composable(Screen.SeedList.route) {
            SeedListScreen(
                onNavigateToSeedDetail = { seedId ->
                    navController.navigate(Screen.SeedDetail.createRoute(seedId))
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
    }
} 