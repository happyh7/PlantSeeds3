package com.bps.plantseeds3.presentation.plants

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.R
import com.bps.plantseeds3.data.local.entity.Plant

@Composable
fun PlantItem(
    plant: Plant,
    gardenName: String,
    onPlantClick: (String) -> Unit,
    onDeletePlant: (Plant) -> Unit
) {
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
                    .clickable { onPlantClick(plant.id) }
            ) {
                Text(
                    text = plant.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = gardenName,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            IconButton(onClick = { onDeletePlant(plant) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Ta bort växt",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}

@Composable
fun PlantScreen(
    onNavigateToAddPlant: () -> Unit,
    onNavigateToPlantDetails: (String) -> Unit,
    viewModel: PlantViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddPlant
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_plant)
                )
            }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.plants),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            if (state.plants.isEmpty()) {
                Text(
                    text = stringResource(R.string.no_plants),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.plants) { plant ->
                        PlantItem(
                            plant = plant,
                            gardenName = viewModel.getGardenName(plant.gardenId),
                            onPlantClick = onNavigateToPlantDetails,
                            onDeletePlant = { viewModel.deletePlant(it) }
                        )
                    }
                }
            }
        }
    }
} 