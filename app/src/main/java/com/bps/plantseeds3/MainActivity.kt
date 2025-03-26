package com.bps.plantseeds3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material.icons.filled.Park
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bps.plantseeds3.navigation.ComposeNavGraph
import com.bps.plantseeds3.ui.theme.PlantSeedsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantSeedsTheme {
                MainScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "plants"
    
    val items = listOf(
        "plants" to Icons.Default.LocalFlorist,
        "gardens" to Icons.Default.Park
    )
    
    val selectedIndex = when (currentRoute) {
        "plants" -> 0
        "gardens" -> 1
        else -> 0
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, (route, icon) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = if (route == "plants") "Växter" else "Trädgårdar") },
                        label = { Text(if (route == "plants") "Växter" else "Trädgårdar") },
                        selected = selectedIndex == index,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ComposeNavGraph(navController = navController)
        }
    }
}