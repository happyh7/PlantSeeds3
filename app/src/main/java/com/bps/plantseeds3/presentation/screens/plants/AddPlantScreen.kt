package com.bps.plantseeds3.presentation.screens.plants

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.screens.plants.viewmodel.AddPlantViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlantScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddPlantViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lägg till växt") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Namn") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = species,
                onValueChange = { species = it },
                label = { Text("Art") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Beskrivning") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            Button(
                onClick = {
                    viewModel.addPlant(
                        name = name,
                        species = species,
                        description = description
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && species.isNotBlank()
            ) {
                Text("Spara")
            }
        }
    }
} 