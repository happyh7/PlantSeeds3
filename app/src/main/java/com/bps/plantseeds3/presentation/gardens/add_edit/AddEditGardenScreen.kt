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
import com.bps.plantseeds3.presentation.components.CapitalizedTextField

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
        state.error?.let { _ ->
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
            CapitalizedTextField(
                value = name,
                onValueChange = { name = it },
                label = "Namn *",
                modifier = Modifier.fillMaxWidth(),
                isError = state.error != null && name.isBlank()
            )

            CapitalizedTextField(
                value = location,
                onValueChange = { location = it },
                label = "Plats",
                modifier = Modifier.fillMaxWidth()
            )

            CapitalizedTextField(
                value = description,
                onValueChange = { description = it },
                label = "Beskrivning",
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            CapitalizedTextField(
                value = size,
                onValueChange = { size = it },
                label = "Storlek (m²)",
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CapitalizedTextField(
                    value = width,
                    onValueChange = { width = it },
                    label = "Bredd (m)",
                    modifier = Modifier.weight(1f)
                )
                CapitalizedTextField(
                    value = length,
                    onValueChange = { length = it },
                    label = "Längd (m)",
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                CapitalizedTextField(
                    value = elevation,
                    onValueChange = { elevation = it },
                    label = "Höjd (m)",
                    modifier = Modifier.weight(1f)
                )
                CapitalizedTextField(
                    value = slope,
                    onValueChange = { slope = it },
                    label = "Lutning (°)",
                    modifier = Modifier.weight(1f)
                )
            }

            CapitalizedTextField(
                value = soilType,
                onValueChange = { soilType = it },
                label = "Jordtyp",
                modifier = Modifier.fillMaxWidth()
            )

            CapitalizedTextField(
                value = sunExposure,
                onValueChange = { sunExposure = it },
                label = "Solsken",
                modifier = Modifier.fillMaxWidth()
            )

            CapitalizedTextField(
                value = irrigation,
                onValueChange = { irrigation = it },
                label = "Bevattning",
                modifier = Modifier.fillMaxWidth()
            )

            CapitalizedTextField(
                value = fence,
                onValueChange = { fence = it },
                label = "Staket",
                modifier = Modifier.fillMaxWidth()
            )

            CapitalizedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = "Anteckningar",
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