package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.domain.model.Seed
import com.bps.plantseeds3.presentation.ui.state.SeedListUiState
import com.bps.plantseeds3.presentation.ui.viewmodel.SeedListViewModel

@Composable
fun SeedListScreen(
    viewModel: SeedListViewModel = hiltViewModel(),
    onSeedClick: (Seed) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    when {
        uiState.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.error ?: "Unknown error")
            }
        }
        else -> {
            SeedList(
                seeds = uiState.seeds,
                onSeedClick = onSeedClick
            )
        }
    }
}

@Composable
private fun SeedList(
    seeds: List<Seed>,
    onSeedClick: (Seed) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(seeds) { seed ->
            SeedItem(
                seed = seed,
                onClick = { onSeedClick(seed) }
            )
        }
    }
}

@Composable
private fun SeedItem(
    seed: Seed,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = seed.name,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = seed.description,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
} 