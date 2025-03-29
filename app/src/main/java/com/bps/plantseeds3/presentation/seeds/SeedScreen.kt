package com.bps.plantseeds3.presentation.seeds

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bps.plantseeds3.presentation.navigation.Screen
import com.bps.plantseeds3.presentation.seeds.components.SeedItem
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeedScreen(
    onNavigate: (String) -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: SeedViewModel = hiltViewModel(),
    navController: NavController
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Frön") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Navigera tillbaka"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.onEvent(SeedEvent.SearchSeeds(searchQuery))
                    }) {
                        Icon(Icons.Default.Search, contentDescription = "Sök")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditSeed.createRoute())
                }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it
                    viewModel.onEvent(SeedEvent.SearchSeeds(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                placeholder = { Text("Sök frön...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
            )

            Box(modifier = Modifier.fillMaxSize()) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(state.seeds) { seed ->
                            SeedItem(
                                seed = seed,
                                onDeleteClick = {
                                    viewModel.onEvent(SeedEvent.DeleteSeed(seed))
                                },
                                onEditClick = {
                                    navController.navigate(Screen.AddEditSeed.createRoute(seed.id))
                                }
                            )
                        }
                    }
                    
                    if (state.seeds.isEmpty()) {
                        Text(
                            text = if (searchQuery.isBlank()) 
                                "Inga frön tillagda än" 
                            else 
                                "Inga frön matchar sökningen",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(16.dp)
                        )
                    }
                }
            }
        }
    }
} 