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
fun PlantingDetailsSection(
    state: AddEditSeedState,
    onUpdatePlantingDepth: (String) -> Unit,
    onUpdatePlantingDistance: (String) -> Unit,
    onUpdatePlantingDates: (String) -> Unit
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
                text = stringResource(R.string.planting_details),
                style = MaterialTheme.typography.titleLarge
            )
            
            CapitalizedTextField(
                value = state.plantingDepth,
                onValueChange = onUpdatePlantingDepth,
                label = stringResource(R.string.planting_depth)
            )
            
            CapitalizedTextField(
                value = state.plantingDistance,
                onValueChange = onUpdatePlantingDistance,
                label = stringResource(R.string.planting_distance)
            )
            
            CapitalizedTextField(
                value = state.plantingDates,
                onValueChange = onUpdatePlantingDates,
                label = stringResource(R.string.planting_dates)
            )
        }
    }
} 