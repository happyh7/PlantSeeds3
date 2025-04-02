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
import com.bps.plantseeds3.presentation.ui.screens.EditSeedScreen
import com.bps.plantseeds3.presentation.ui.viewmodel.EditSeedViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import com.bps.plantseeds3.presentation.ui.viewmodel.AddSeedViewModel
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.rememberCoroutineScope
import com.bps.plantseeds3.presentation.ui.viewmodel.SeedDetailViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    seedListViewModel: SeedListViewModel
) {
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
            composable(
                route = Screen.SeedList.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                SeedListScreen(
                    viewModel = seedListViewModel,
                    onNavigateToSeedDetail = { seedId -> 
                        navController.navigate(Screen.SeedDetail.createRoute(seedId))
                    },
                    onNavigateToAddSeed = { navController.navigate(Screen.AddSeed.route) }
                )
            }

            composable(
                route = Screen.SeedDetail.route,
                arguments = listOf(
                    navArgument("seedId") { type = NavType.StringType }
                ),
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                val viewModel = hiltViewModel<SeedDetailViewModel>()
                val seedId = it.arguments?.getString("seedId")
                SeedDetailScreen(
                    onNavigateBack = { navController.popBackStack() },
                    onEditSeed = { 
                        seedId?.let { id -> 
                            navController.navigate(Screen.EditSeed.createRoute(id))
                        }
                    },
                    onDeleteSeed = {
                        navController.popBackStack()
                    }
                )
            }

            composable(
                route = Screen.AddSeed.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                val viewModel = hiltViewModel<AddSeedViewModel>()
                val scope = rememberCoroutineScope()
                AddSeedScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onSaveSeed = { name, species, description, plantingInstructions, daysToGermination, daysToHarvest, lightNeeds, waterNeeds, soilType, temperature, spacing ->
                        viewModel.saveSeed {
                            scope.launch {
                                delay(500)
                                navController.popBackStack()
                            }
                        }
                    }
                )
            }

            composable(
                route = Screen.EditSeed.route,
                arguments = listOf(
                    navArgument("seedId") { type = NavType.StringType }
                ),
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                val viewModel = hiltViewModel<EditSeedViewModel>()
                val seedDetailViewModel = hiltViewModel<SeedDetailViewModel>()
                val scope = rememberCoroutineScope()
                EditSeedScreen(
                    viewModel = viewModel,
                    onNavigateBack = { navController.popBackStack() },
                    onSaveSeed = {
                        scope.launch {
                            delay(500)
                            seedDetailViewModel.refreshSeed()
                            navController.popBackStack()
                        }
                    }
                )
            }

            composable(
                route = Screen.GardenList.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                GardenListScreen()
            }

            composable(
                route = Screen.Settings.route,
                enterTransition = { fadeIn(animationSpec = tween(300)) },
                exitTransition = { fadeOut(animationSpec = tween(300)) }
            ) {
                SettingsScreen()
            }
        }
    }
} 