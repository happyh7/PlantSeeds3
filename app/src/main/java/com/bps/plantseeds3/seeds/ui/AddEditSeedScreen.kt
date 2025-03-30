package com.bps.plantseeds3.seeds.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.common.ui.CommonTopAppBar
import com.bps.plantseeds3.common.ui.CommonInputField
import com.bps.plantseeds3.common.ui.LoadingScreen
import com.bps.plantseeds3.common.ui.ErrorScreen
import com.bps.plantseeds3.seeds.model.Seed
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSeedScreen(
    seedId: Long?,
    onNavigateBack: () -> Unit,
    onSaveComplete: () -> Unit,
    viewModel: SeedsViewModel = hiltViewModel()
) {
    val selectedSeed by viewModel.selectedSeed.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    var name by remember { mutableStateOf(selectedSeed?.name ?: "") }
    var species by remember { mutableStateOf(selectedSeed?.species ?: "") }
    var variety by remember { mutableStateOf(selectedSeed?.variety ?: "") }
    var description by remember { mutableStateOf(selectedSeed?.description ?: "") }
    var sowingInstructions by remember { mutableStateOf(selectedSeed?.sowingInstructions ?: "") }
    var germinationTime by remember { mutableStateOf(selectedSeed?.germinationTime?.toString() ?: "") }
    var harvestTime by remember { mutableStateOf(selectedSeed?.harvestTime?.toString() ?: "") }
    var plantingDepth by remember { mutableStateOf(selectedSeed?.plantingDepth?.toString() ?: "") }
    var spacing by remember { mutableStateOf(selectedSeed?.spacing?.toString() ?: "") }
    var lightNeeds by remember { mutableStateOf(selectedSeed?.lightNeeds ?: "") }
    var waterNeeds by remember { mutableStateOf(selectedSeed?.waterNeeds ?: "") }
    var temperatureNeeds by remember { mutableStateOf(selectedSeed?.temperatureNeeds ?: "") }
    var quantity by remember { mutableStateOf(selectedSeed?.quantity?.toString() ?: "") }
    var unit by remember { mutableStateOf(selectedSeed?.unit ?: "") }

    LaunchedEffect(seedId) {
        seedId?.let { viewModel.selectSeed(it) }
    }

    LaunchedEffect(selectedSeed) {
        selectedSeed?.let { seed ->
            name = seed.name
            species = seed.species
            variety = seed.variety
            description = seed.description
            sowingInstructions = seed.sowingInstructions
            germinationTime = seed.germinationTime.toString()
            harvestTime = seed.harvestTime.toString()
            plantingDepth = seed.plantingDepth.toString()
            spacing = seed.spacing.toString()
            lightNeeds = seed.lightNeeds
            waterNeeds = seed.waterNeeds
            temperatureNeeds = seed.temperatureNeeds
            quantity = seed.quantity.toString()
            unit = seed.unit
        }
    }

    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = if (seedId == null) "Lägg till frö" else "Redigera frö",
                onNavigateBack = onNavigateBack,
                actions = {
                    IconButton(
                        onClick = {
                            val seed = Seed().apply {
                                this.name = name
                                this.species = species
                                this.variety = variety
                                this.description = description
                                this.sowingInstructions = sowingInstructions
                                this.germinationTime = germinationTime.toIntOrNull() ?: 0
                                this.harvestTime = harvestTime.toIntOrNull() ?: 0
                                this.plantingDepth = plantingDepth.toFloatOrNull() ?: 0f
                                this.spacing = spacing.toFloatOrNull() ?: 0f
                                this.lightNeeds = lightNeeds
                                this.waterNeeds = waterNeeds
                                this.temperatureNeeds = temperatureNeeds
                                this.quantity = quantity.toIntOrNull() ?: 0
                                this.unit = unit
                                this.isFavorite = selectedSeed?.isFavorite ?: false
                                if (seedId != null) {
                                    this.id = seedId
                                }
                            }
                            if (seedId == null) {
                                viewModel.addSeed(seed)
                            } else {
                                viewModel.updateSeed(seed)
                            }
                            onSaveComplete()
                        }
                    ) {
                        Icon(Icons.Default.Save, "Spara")
                    }
                }
            )
        }
    ) { padding ->
        when {
            isLoading -> {
                LoadingScreen(modifier = Modifier.fillMaxSize())
            }
            error != null -> {
                ErrorScreen(
                    message = error!!,
                    onRetry = { viewModel.clearError() },
                    modifier = Modifier.fillMaxSize()
                )
            }
            else -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CommonInputField(
                        value = name,
                        onValueChange = { name = it },
                        label = "Namn",
                        modifier = Modifier.fillMaxWidth()
                    )

                    CommonInputField(
                        value = species,
                        onValueChange = { species = it },
                        label = "Art",
                        modifier = Modifier.fillMaxWidth()
                    )

                    CommonInputField(
                        value = variety,
                        onValueChange = { variety = it },
                        label = "Sort",
                        modifier = Modifier.fillMaxWidth()
                    )

                    CommonInputField(
                        value = description,
                        onValueChange = { description = it },
                        label = "Beskrivning",
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    CommonInputField(
                        value = sowingInstructions,
                        onValueChange = { sowingInstructions = it },
                        label = "Såinstruktioner",
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )

                    CommonInputField(
                        value = germinationTime,
                        onValueChange = { germinationTime = it },
                        label = "Groningstid (dagar)",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )

                    CommonInputField(
                        value = harvestTime,
                        onValueChange = { harvestTime = it },
                        label = "Skördetid (dagar)",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )

                    CommonInputField(
                        value = plantingDepth,
                        onValueChange = { plantingDepth = it },
                        label = "Planteringsdjup (cm)",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    )

                    CommonInputField(
                        value = spacing,
                        onValueChange = { spacing = it },
                        label = "Mellanrum (cm)",
                        modifier = Modifier.fillMaxWidth(),
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Decimal
                    )

                    CommonInputField(
                        value = lightNeeds,
                        onValueChange = { lightNeeds = it },
                        label = "Ljusbehov",
                        modifier = Modifier.fillMaxWidth()
                    )

                    CommonInputField(
                        value = waterNeeds,
                        onValueChange = { waterNeeds = it },
                        label = "Vattenbehov",
                        modifier = Modifier.fillMaxWidth()
                    )

                    CommonInputField(
                        value = temperatureNeeds,
                        onValueChange = { temperatureNeeds = it },
                        label = "Temperaturbehov",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CommonInputField(
                            value = quantity,
                            onValueChange = { quantity = it },
                            label = "Antal",
                            modifier = Modifier.weight(1f),
                            keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                        )

                        CommonInputField(
                            value = unit,
                            onValueChange = { unit = it },
                            label = "Enhet",
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
} 