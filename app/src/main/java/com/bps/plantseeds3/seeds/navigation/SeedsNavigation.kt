package com.bps.plantseeds3.seeds.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bps.plantseeds3.seeds.ui.AddEditSeedScreen
import com.bps.plantseeds3.seeds.ui.SeedsScreen
import com.bps.plantseeds3.seeds.ui.SeedDetailScreen

sealed class SeedsScreen(val route: String) {
    object SeedsList : SeedsScreen("seeds_list")
    object AddEditSeed : SeedsScreen("add_edit_seed?seedId={seedId}") {
        fun createRoute(seedId: Long? = null) = "add_edit_seed?seedId=$seedId"
    }
    object SeedDetail : SeedsScreen("seed_detail/{seedId}") {
        fun createRoute(seedId: Long) = "seed_detail/$seedId"
    }
}

@Composable
fun SeedsNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = SeedsScreen.SeedsList.route
    ) {
        composable(SeedsScreen.SeedsList.route) {
            SeedsScreen(
                onNavigateToAddSeed = {
                    navController.navigate(SeedsScreen.AddEditSeed.createRoute())
                },
                onNavigateToSeedDetail = { seedId ->
                    navController.navigate(SeedsScreen.SeedDetail.createRoute(seedId))
                }
            )
        }

        composable(
            route = SeedsScreen.AddEditSeed.route,
            arguments = listOf(
                navArgument("seedId") {
                    type = NavType.LongType
                    nullable = true
                    defaultValue = null
                }
            )
        ) {
            AddEditSeedScreen(
                seedId = it.arguments?.getLong("seedId"),
                onNavigateBack = { navController.popBackStack() },
                onSaveComplete = { navController.popBackStack() }
            )
        }

        composable(
            route = SeedsScreen.SeedDetail.route,
            arguments = listOf(
                navArgument("seedId") {
                    type = NavType.LongType
                    nullable = false
                }
            )
        ) {
            SeedDetailScreen(
                seedId = it.arguments?.getLong("seedId") ?: return@composable,
                onNavigateBack = { navController.popBackStack() },
                onEditSeed = {
                    navController.navigate(
                        SeedsScreen.AddEditSeed.createRoute(it.arguments?.getLong("seedId"))
                    )
                }
            )
        }
    }
} 