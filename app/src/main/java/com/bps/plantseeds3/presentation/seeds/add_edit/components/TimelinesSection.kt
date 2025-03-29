package com.bps.plantseeds3.presentation.seeds.add_edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedEvent
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedState

@Composable
fun TimelinesSection(
    state: AddEditSeedState,
    onEvent: (AddEditSeedEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Tidslinjer",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = state.daysToGermination,
            onValueChange = { onEvent(AddEditSeedEvent.OnDaysToGerminationChange(it)) },
            label = { Text("Dagar till groning") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.daysToMaturity,
            onValueChange = { onEvent(AddEditSeedEvent.OnDaysToMaturityChange(it)) },
            label = { Text("Dagar till mognad") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = state.harvestPeriod,
            onValueChange = { onEvent(AddEditSeedEvent.OnHarvestPeriodChange(it)) },
            label = { Text("Skördeperiod") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
    }
} 