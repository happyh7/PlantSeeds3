package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.presentation.ui.state.SeedDetailUiState
import com.bps.plantseeds3.presentation.ui.viewmodel.SeedDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedDetailScreen(
    onNavigateBack: () -> Unit,
    onEditSeed: () -> Unit,
    onDeleteSeed: () -> Unit,
    viewModel: SeedDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Frödetaljer") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Tillbaka")
                    }
                },
                actions = {
                    IconButton(onClick = onEditSeed) {
                        Icon(Icons.Default.Edit, contentDescription = "Redigera")
                    }
                    IconButton(onClick = { showDeleteDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Ta bort")
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
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Text(
                        text = uiState.error!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                uiState.seed == null -> {
                    Text(
                        text = "Inget frö hittades",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                else -> {
                    SeedDetailContent(
                        seed = uiState.seed!!,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    )
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Ta bort frö") },
            text = { Text("Är du säker på att du vill ta bort detta frö?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        viewModel.onDeleteSeed(onDeleteSeed)
                    }
                ) {
                    Text("Ta bort")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Avbryt")
                }
            }
        )
    }
}

@Composable
private fun SeedDetailContent(
    seed: Seed,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = seed.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.fillMaxWidth()
        )

        seed.description?.let { description ->
            if (description.isNotBlank()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Här kan vi lägga till mer information om fröet senare
        // Till exempel:
        // - Art
        // - Sådningsdatum
        // - Skördedatum
        // - Växtinstruktioner
        // - etc.
    }
} 