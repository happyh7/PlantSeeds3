package com.bps.plantseeds3.presentation.gardens.add_edit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditGardenScreen(
    gardenId: String?,
    navController: NavController,
    onNavigateBack: () -> Unit = { navController.navigateUp() },
    viewModel: AddEditGardenViewModel = hiltViewModel()
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var size by remember { mutableStateOf("") }
    var width by remember { mutableStateOf("") }
    var length by remember { mutableStateOf("") }
    var elevation by remember { mutableStateOf("") }
    var slope by remember { mutableStateOf("") }
    var soilType by remember { mutableStateOf("") }
    var sunExposure by remember { mutableStateOf("") }
    var irrigation by remember { mutableStateOf("") }
    var fence by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()
    val formData by viewModel.formData

    LaunchedEffect(formData) {
        name = formData.name
        location = formData.location
        description = formData.description
        size = formData.size
        width = formData.width
        length = formData.length
        elevation = formData.elevation
        slope = formData.slope
        soilType = formData.soilType
        sunExposure = formData.sunExposure
        irrigation = formData.irrigation
        fence = formData.fence
        notes = formData.notes
    }

    LaunchedEffect(state.isSaved) {
        if (state.isSaved) {
            onNavigateBack()
        }
    }

    LaunchedEffect(state.error) {
        state.error?.let { error ->
            // Visa felmeddelande
            // TODO: Implementera felhantering
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (gardenId != null) "Redigera trädgård" else "Lägg till trädgård") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Tillbaka"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.validateAndSaveGarden(
                                name = name,
                                location = location,
                                description = description,
                                size = size,
                                width = width,
                                length = length,
                                elevation = elevation,
                                slope = slope,
                                soilType = soilType,
                                sunExposure = sunExposure,
                                irrigation = irrigation,
                                fence = fence,
                                notes = notes
                            )
                        }
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Spara")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Namn *") },
                modifier = Modifier.fillMaxWidth(),
                isError = state.error != null && name.isBlank()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Plats") },
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
                value = size,
                onValueChange = { size = it },
                label = { Text("Storlek (m²)") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = width,
                    onValueChange = { width = it },
                    label = { Text("Bredd (m)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = length,
                    onValueChange = { length = it },
                    label = { Text("Längd (m)") },
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = elevation,
                    onValueChange = { elevation = it },
                    label = { Text("Höjd (m)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = slope,
                    onValueChange = { slope = it },
                    label = { Text("Lutning (°)") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = soilType,
                onValueChange = { soilType = it },
                label = { Text("Jordtyp") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = sunExposure,
                onValueChange = { sunExposure = it },
                label = { Text("Solsken") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = irrigation,
                onValueChange = { irrigation = it },
                label = { Text("Bevattning") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = fence,
                onValueChange = { fence = it },
                label = { Text("Staket/Avgränsning") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Anteckningar") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            if (state.error != null) {
                Text(
                    text = state.error!!,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
} 