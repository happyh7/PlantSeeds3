package com.bps.plantseeds3.presentation.seeds.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bps.plantseeds3.navigation.Screen

@Composable
fun SeedListScreen(
    navController: NavController,
    viewModel: SeedListViewModel
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditSeed.createRoute())
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Lägg till frö"
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Text(
                text = "Fröbank",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )

            if (state.seeds.isEmpty()) {
                Text(
                    text = "Inga frön tillagda än",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            } else {
                // TODO: Implementera frölista
            }
        }
    }
} 