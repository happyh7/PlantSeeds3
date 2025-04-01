package com.bps.plantseeds3.presentation.screens.gardens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.screens.gardens.viewmodel.AddGardenViewModel
import com.bps.plantseeds3.presentation.screens.gardens.viewmodel.AddGardenUiState
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGardenScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddGardenViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState) {
        when (uiState) {
            is AddGardenUiState.Success -> {
                onNavigateBack()
            }
            is AddGardenUiState.Error -> {
                val error = (uiState as AddGardenUiState.Error)
                snackbarHostState.showSnackbar(
                    message = error.message,
                    duration = SnackbarDuration.Short
                )
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lägg till trädgård") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is AddGardenUiState.Loading
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Plats") },
                modifier = Modifier.fillMaxWidth(),
                enabled = uiState !is AddGardenUiState.Loading
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Beskrivning") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                enabled = uiState !is AddGardenUiState.Loading
            )

            Button(
                onClick = {
                    viewModel.addGarden(
                        name = name,
                        description = description,
                        location = location
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && uiState !is AddGardenUiState.Loading
            ) {
                if (uiState is AddGardenUiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Spara")
                }
            }
        }
    }
} 