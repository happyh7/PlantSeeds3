package com.bps.plantseeds3.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalFlorist
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.bps.plantseeds3.presentation.navigation.Screen

@Composable
fun BottomBar(
    currentRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.LocalFlorist, contentDescription = "Frö/Växt") },
            label = { Text("Frö/Växt") },
            selected = currentRoute == Screen.Plants.route,
            onClick = { onNavigate(Screen.Plants.route) }
        )
    }
} 