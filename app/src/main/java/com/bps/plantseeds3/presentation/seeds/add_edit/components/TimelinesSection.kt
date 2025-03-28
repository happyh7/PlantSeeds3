package com.bps.plantseeds3.presentation.seeds.add_edit.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bps.plantseeds3.R
import com.bps.plantseeds3.presentation.components.CapitalizedTextField
import com.bps.plantseeds3.presentation.seeds.add_edit.AddEditSeedState

@Composable
fun TimelinesSection(
    state: AddEditSeedState,
    onUpdateDaysToGermination: (String) -> Unit,
    onUpdateDaysToHarvest: (String) -> Unit,
    onUpdateHarvestPeriod: (String) -> Unit
) {
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
                text = stringResource(R.string.timelines),
                style = MaterialTheme.typography.titleLarge
            )
            
            CapitalizedTextField(
                value = state.daysToGermination,
                onValueChange = onUpdateDaysToGermination,
                label = stringResource(R.string.days_to_germination)
            )
            
            CapitalizedTextField(
                value = state.daysToHarvest,
                onValueChange = onUpdateDaysToHarvest,
                label = stringResource(R.string.days_to_harvest)
            )
            
            CapitalizedTextField(
                value = state.harvestPeriod,
                onValueChange = onUpdateHarvestPeriod,
                label = stringResource(R.string.harvest_period)
            )
        }
    }
} 