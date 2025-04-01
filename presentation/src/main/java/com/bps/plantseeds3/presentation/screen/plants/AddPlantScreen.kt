package com.bps.plantseeds3.presentation.screen.plants

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.viewmodel.plants.AddPlantViewModel
import com.bps.plantseeds3.presentation.viewmodel.plants.AddPlantUiState
import kotlinx.coroutines.delay

private const val TAG = "AddPlantScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPlantScreen(
    gardenId: String,
    onNavigateBack: () -> Unit,
    viewModel: AddPlantViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var species by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var nextWateringDays by remember { mutableStateOf("7") }
    
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(gardenId) {
        Log.d(TAG, "Setting garden ID: $gardenId")
        viewModel.setGardenId(gardenId)
    }

    LaunchedEffect(uiState) {
        when (uiState) {
            is AddPlantUiState.Success -> {
                snackbarHostState.showSnackbar(
                    message = "Växten har lagts till",
                    duration = SnackbarDuration.Short
                )
                delay(1000) // Vänta så användaren hinner se meddelandet
                onNavigateBack()
            }
            is AddPlantUiState.Error -> {
                val error = (uiState as AddPlantUiState.Error)
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
                title = { Text("Lägg till växt") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Tillbaka")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
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

                OutlinedTextField(
                    value = nextWateringDays,
                    onValueChange = { 
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            nextWateringDays = it
                        }
                    },
                    label = { Text("Vattna om (dagar)") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.addPlant(
                            name = name,
                            species = species,
                            description = description,
                            nextWateringDays = nextWateringDays.toIntOrNull() ?: 7
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank() && species.isNotBlank()
                ) {
                    Text("Lägg till växt")
                }
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.align(Alignment.BottomCenter)
            )
        }
    }
} 