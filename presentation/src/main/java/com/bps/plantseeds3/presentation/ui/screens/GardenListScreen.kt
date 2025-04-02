package com.bps.plantseeds3.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GardenListScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mina trädgårdar") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Implementera skapande av ny trädgård */ }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Lägg till trädgård")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "Inga trädgårdar hittades",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
} 