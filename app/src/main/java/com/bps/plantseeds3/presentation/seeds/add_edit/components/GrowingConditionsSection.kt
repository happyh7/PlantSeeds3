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
fun GrowingConditionsSection(
    state: AddEditSeedState,
    onUpdateSunRequirement: (String) -> Unit,
    onUpdateWaterRequirement: (String) -> Unit,
    onUpdateSoilType: (String) -> Unit,
    onUpdateHardinessZone: (String) -> Unit
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
                text = stringResource(R.string.growing_conditions),
                style = MaterialTheme.typography.titleLarge
            )
            
            CapitalizedTextField(
                value = state.sunRequirement,
                onValueChange = onUpdateSunRequirement,
                label = stringResource(R.string.sun_requirement)
            )
            
            CapitalizedTextField(
                value = state.waterRequirement,
                onValueChange = onUpdateWaterRequirement,
                label = stringResource(R.string.water_requirement)
            )
            
            CapitalizedTextField(
                value = state.soilType,
                onValueChange = onUpdateSoilType,
                label = stringResource(R.string.soil_type)
            )
            
            CapitalizedTextField(
                value = state.hardinessZone,
                onValueChange = onUpdateHardinessZone,
                label = stringResource(R.string.hardiness_zone)
            )
        }
    }
} 