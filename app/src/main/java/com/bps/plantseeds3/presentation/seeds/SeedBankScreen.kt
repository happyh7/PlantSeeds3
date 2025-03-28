package com.bps.plantseeds3.presentation.seeds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bps.plantseeds3.R
import com.bps.plantseeds3.presentation.components.CapitalizedTextField
import com.bps.plantseeds3.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedBankScreen(
    navController: NavController,
    viewModel: SeedBankViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Fröbank") },
                actions = {
                    IconButton(
                        onClick = { navController.navigate(Screen.AddEditSeed.route) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Lägg till frö"
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (state.seeds.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Inga frön tillagda än",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.seeds) { seed ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = seed.name,
                                style = MaterialTheme.typography.titleMedium
                            )
                            
                            seed.scientificName?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            
                            seed.species?.let {
                                Text(
                                    text = "Art: $it",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            
                            seed.variety?.let {
                                Text(
                                    text = "Sort: $it",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            
                            seed.category?.let {
                                Text(
                                    text = "Kategori: $it",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            
                            seed.description?.let {
                                Text(
                                    text = "Beskrivning: $it",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
} 