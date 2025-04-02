package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.R
import com.bps.plantseeds3.presentation.ui.viewmodel.AddSeedViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSeedScreen(
    onNavigateBack: () -> Unit,
    onSaveSeed: (String, String, String?) -> Unit,
    viewModel: AddSeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_seed)) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (uiState.name.isNotBlank()) {
                        viewModel.saveSeed {
                            onSaveSeed(uiState.name, uiState.species, uiState.description.ifBlank { null })
                        }
                    }
                }
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(R.string.save))
                }
            }
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
                    value = uiState.name,
                    onValueChange = viewModel::onNameChange,
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.species,
                    onValueChange = viewModel::onSpeciesChange,
                    label = { Text(stringResource(R.string.species)) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !uiState.isLoading
                )

                OutlinedTextField(
                    value = uiState.description,
                    onValueChange = viewModel::onDescriptionChange,
                    label = { Text(stringResource(R.string.description)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    enabled = !uiState.isLoading
                )

                uiState.error?.let { error ->
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
} 