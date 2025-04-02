package com.bps.plantseeds3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bps.plantseeds3.presentation.navigation.NavGraph
import com.bps.plantseeds3.presentation.ui.theme.PlantSeeds3Theme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.ui.viewmodel.SeedListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantSeeds3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val seedListViewModel = hiltViewModel<SeedListViewModel>()
                    NavGraph(
                        navController = navController,
                        seedListViewModel = seedListViewModel
                    )
                }
            }
        }
    }
}