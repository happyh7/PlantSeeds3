package com.bps.plantseeds3.presentation.components

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun NumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false
) {
    val TAG = "NumberTextField"

    Log.d(TAG, "Visar nummerfält: $label")
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            Log.d(TAG, "Uppdaterar värde för $label: $newValue")
            if (newValue.isEmpty() || newValue.matches(Regex("^\\d*\\.?\\d*$"))) {
                onValueChange(newValue)
            }
        },
        label = { 
            Text(
                text = if (isRequired) "$label *" else label,
                textAlign = TextAlign.Start
            )
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true,
        isError = isRequired && value.isBlank()
    )
} 