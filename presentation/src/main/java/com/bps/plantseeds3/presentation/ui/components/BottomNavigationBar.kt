package com.bps.plantseeds3.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bps.plantseeds3.presentation.navigation.Screen

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = listOf(
        NavigationItem(
            name = "Trädgårdar",
            route = Screen.GardenOverview.route,
            icon = Icons.Default.Home
        ),
        NavigationItem(
            name = "Fröer",
            route = Screen.SeedList.route,
            icon = Icons.Default.List
        ),
        NavigationItem(
            name = "Inställningar",
            route = Screen.Settings.route,
            icon = Icons.Default.Settings
        )
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                label = { Text(text = item.name) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

private data class NavigationItem(
    val name: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
) 