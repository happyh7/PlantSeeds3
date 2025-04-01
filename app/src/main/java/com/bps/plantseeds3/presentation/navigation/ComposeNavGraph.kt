package com.bps.plantseeds3.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bps.plantseeds3.presentation.screens.gardens.GardensScreen
import com.bps.plantseeds3.presentation.screens.plants.PlantsScreen
import com.bps.plantseeds3.presentation.screens.seeds.SeedsScreen

@Composable
fun ComposeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "plants"
    ) {
        composable("plants") {
            PlantsScreen()
        }
        composable("gardens") {
            GardensScreen()
        }
        composable("seeds") {
            SeedsScreen()
        }
    }
} 