package com.bps.plantseeds3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bps.plantseeds3.presentation.navigation.ComposeNavGraph
import com.bps.plantseeds3.presentation.navigation.Screen
import com.bps.plantseeds3.presentation.theme.PlantSeedsTheme
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
    var selectedItem by remember { mutableStateOf(0) }
    val items = listOf(
        Triple("Trädgårdar", Icons.Default.Home, Screen.Gardens.route),
        Triple("Växter", Icons.Default.LocalFlorist, Screen.Gardens.route), // Temporärt navigera till trädgårdar
        Triple("Fröbank", Icons.Default.Grass, Screen.Seeds.route)
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, (title, icon, route) ->
                    NavigationBarItem(
                        icon = { Icon(icon, contentDescription = title) },
                        label = { Text(title) },
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            navController.navigate(route)
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