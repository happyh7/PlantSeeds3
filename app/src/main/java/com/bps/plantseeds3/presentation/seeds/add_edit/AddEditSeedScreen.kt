package com.bps.plantseeds3.presentation.seeds.add_edit

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.R
import com.bps.plantseeds3.domain.model.PlantCategory
import com.bps.plantseeds3.presentation.components.CapitalizedTextField
import com.bps.plantseeds3.presentation.components.DropdownMenuField
import com.bps.plantseeds3.presentation.components.NumberTextField
import com.bps.plantseeds3.presentation.components.TextAreaField
import com.bps.plantseeds3.presentation.util.UiEvent
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditSeedScreen(
    seedId: String?,
    navController: NavController,
    viewModel: AddEditSeedViewModel = hiltViewModel()
) {
    val TAG = "AddEditSeedScreen"
    val state by viewModel.state.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var snackbarMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = true) {
        Log.d(TAG, "AddEditSeedScreen initieras")
        viewModel.eventFlow.collectLatest { event: UiEvent ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarMessage = event.message
                }
                is UiEvent.NavigateUp -> navController.navigateUp()
                is UiEvent.SaveSeed -> {
                    snackbarMessage = "Frö sparat!"
                    navController.navigateUp()
                }
                else -> Unit
            }
        }
    }

    LaunchedEffect(key1 = seedId) {
        seedId?.let { viewModel.loadSeedById(it) }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lägg till/Redigera frö") },
                navigationIcon = {
                    IconButton(onClick = { 
                        Log.d(TAG, "Navigerar tillbaka")
                        navController.navigateUp() 
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { 
                            Log.d(TAG, "Sparar frö")
                            viewModel.onEvent(AddEditSeedEvent.OnSaveClick)
                        }
                    ) {
                        Icon(Icons.Default.Save, contentDescription = "Spara")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.error != null) {
                Log.e(TAG, "Visar felmeddelande: ${state.error}")
                Text(
                    text = state.error ?: "",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(16.dp)
                )
            } else {
                Log.d(TAG, "Visar formulär")
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    CapitalizedTextField(
                        value = state.name,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar namn: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnNameChange(it))
                        },
                        label = "Namn",
                        isRequired = true
                    )

                    CapitalizedTextField(
                        value = state.scientificName,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar vetenskapligt namn: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnScientificNameChange(it))
                        },
                        label = "Vetenskapligt namn"
                    )

                    TextAreaField(
                        value = state.description,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar beskrivning: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnDescriptionChange(it))
                        },
                        label = "Beskrivning"
                    )

                    DropdownMenuField(
                        value = state.category?.displayName ?: "",
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar kategori: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnCategoryChange(PlantCategory.fromDisplayName(it)))
                        },
                        label = "Kategori",
                        options = PlantCategory.values().also { categories ->
                            Log.d(TAG, "Tillgängliga kategorier: ${categories.joinToString { it.displayName }}")
                        }.map { it.displayName },
                        isRequired = true
                    )

                    NumberTextField(
                        value = state.plantingDepth,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar planteringsdjup: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnPlantingDepthChange(it))
                        },
                        label = "Planteringsdjup (cm)"
                    )

                    NumberTextField(
                        value = state.plantingDistance,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar planteringsavstånd: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnPlantingDistanceChange(it))
                        },
                        label = "Planteringsavstånd (cm)"
                    )

                    NumberTextField(
                        value = state.daysToGermination,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar dagar till grodd: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnDaysToGerminationChange(it))
                        },
                        label = "Dagar till grodd"
                    )

                    NumberTextField(
                        value = state.daysToMaturity,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar dagar till mognad: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnDaysToMaturityChange(it))
                        },
                        label = "Dagar till mognad"
                    )

                    CapitalizedTextField(
                        value = state.lifespan,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar livslängd: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnLifespanChange(it))
                        },
                        label = "Livslängd"
                    )

                    CapitalizedTextField(
                        value = state.hardinessZone,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar hårdhetszon: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnHardinessZoneChange(it))
                        },
                        label = "Hårdhetszon"
                    )

                    CapitalizedTextField(
                        value = state.sunRequirement,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar solkrav: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnSunRequirementChange(it))
                        },
                        label = "Solkrav"
                    )

                    CapitalizedTextField(
                        value = state.waterRequirement,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar vattenkrav: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnWaterRequirementChange(it))
                        },
                        label = "Vattenkrav"
                    )

                    CapitalizedTextField(
                        value = state.soilType,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar jordtyp: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnSoilTypeChange(it))
                        },
                        label = "Jordtyp"
                    )

                    NumberTextField(
                        value = state.soilPh,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar jord-pH: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnSoilPhChange(it))
                        },
                        label = "Jord-pH"
                    )

                    TextAreaField(
                        value = state.sowingInstructions,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar såinstruktioner: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnSowingInstructionsChange(it))
                        },
                        label = "Såinstruktioner"
                    )

                    TextAreaField(
                        value = state.growingInstructions,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar odlingsinstruktioner: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnGrowingInstructionsChange(it))
                        },
                        label = "Odlingsinstruktioner"
                    )

                    TextAreaField(
                        value = state.harvestingInstructions,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar skördeinstruktioner: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnHarvestingInstructionsChange(it))
                        },
                        label = "Skördeinstruktioner"
                    )

                    TextAreaField(
                        value = state.storageInstructions,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar lagringsinstruktioner: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnStorageInstructionsChange(it))
                        },
                        label = "Lagringsinstruktioner"
                    )

                    TextAreaField(
                        value = state.companionPlants,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar följgrödor: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnCompanionPlantsChange(it))
                        },
                        label = "Följgrödor"
                    )

                    TextAreaField(
                        value = state.avoidPlants,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar undvik grödor: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnAvoidPlantsChange(it))
                        },
                        label = "Undvik grödor"
                    )

                    TextAreaField(
                        value = state.tags,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar taggar: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnTagsChange(it))
                        },
                        label = "Taggar"
                    )

                    TextAreaField(
                        value = state.notes,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar anteckningar: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnNotesChange(it))
                        },
                        label = "Anteckningar"
                    )

                    CapitalizedTextField(
                        value = state.imageUrl,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar bild-URL: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnImageUrlChange(it))
                        },
                        label = "Bild-URL"
                    )

                    CapitalizedTextField(
                        value = state.source,
                        onValueChange = { 
                            Log.d(TAG, "Uppdaterar källa: $it")
                            viewModel.onEvent(AddEditSeedEvent.OnSourceChange(it))
                        },
                        label = "Källa"
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Favorit")
                        Switch(
                            checked = state.isFavorite,
                            onCheckedChange = { 
                                Log.d(TAG, "Uppdaterar favoritstatus: $it")
                                viewModel.onEvent(AddEditSeedEvent.OnFavoriteChange(it))
                            }
                        )
                    }
                }
            }
        }
    }
} 