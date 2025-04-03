package com.bps.plantseeds3.garden.presentation.overview

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.garden.presentation.components.AddGardenDialog
import com.bps.plantseeds3.garden.presentation.components.EditGardenDialog
import com.bps.plantseeds3.garden.presentation.components.GardenList

@Composable
fun GardenOverviewScreen(
    onNavigateToGardenDetail: (String) -> Unit,
    viewModel: GardenOverviewViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(GardenOverviewEvent.ShowAddGardenDialog) }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till trädgård")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                GardenList(
                    gardens = state.gardens,
                    onGardenClick = onNavigateToGardenDetail,
                    onDeleteGarden = { garden ->
                        viewModel.onEvent(GardenOverviewEvent.DeleteGarden(garden))
                    },
                    onEditGarden = { garden ->
                        viewModel.onEvent(GardenOverviewEvent.ShowEditGardenDialog(garden))
                    }
                )
            }

            if (state.isAddGardenDialogVisible) {
                AddGardenDialog(
                    onDismiss = { viewModel.onEvent(GardenOverviewEvent.HideAddGardenDialog) },
                    onConfirm = { garden ->
                        viewModel.onEvent(GardenOverviewEvent.AddGarden(garden))
                    }
                )
            }

            state.gardenToEdit?.let { garden ->
                if (state.isEditGardenDialogVisible) {
                    EditGardenDialog(
                        garden = garden,
                        onDismiss = { viewModel.onEvent(GardenOverviewEvent.HideEditGardenDialog) },
                        onConfirm = { updatedGarden ->
                            viewModel.onEvent(GardenOverviewEvent.EditGarden(updatedGarden))
                        }
                    )
                }
            }

            state.error?.let { error ->
                Snackbar(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    Text(error)
                }
            }
        }
    }
} 