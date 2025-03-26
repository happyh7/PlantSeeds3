package com.bps.plantseeds3.presentation.gardens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.R
import com.bps.plantseeds3.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardensScreen(
    navController: NavController,
    viewModel: GardenViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            // Visa felmeddelande
            // TODO: Implementera felhantering
        }
    }

    if (state.showDeleteWarning) {
        AlertDialog(
            onDismissRequest = { viewModel.cancelDeleteGarden() },
            title = { Text("Ta bort trädgård") },
            text = {
                Text(
                    "Denna trädgård innehåller ${state.plantCountToDelete} växter som kommer att tas bort. " +
                    "Är du säker på att du vill fortsätta?"
                )
            },
            confirmButton = {
                TextButton(
                    onClick = { viewModel.confirmDeleteGarden() }
                ) {
                    Text("Ta bort", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { viewModel.cancelDeleteGarden() }
                ) {
                    Text("Avbryt")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.gardens)) }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditGarden.route) }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_garden)
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (state.gardens.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_gardens),
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.gardens) { garden ->
                        Card(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clickable {
                                            navController.navigate(
                                                Screen.AddEditGarden.route + "?gardenId=${garden.id}"
                                            )
                                        }
                                ) {
                                    Text(
                                        text = garden.name,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    if (garden.location != null && garden.location.isNotBlank()) {
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = garden.location,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }
                                }
                                IconButton(
                                    onClick = { viewModel.deleteGarden(garden) }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Ta bort trädgård",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
} 