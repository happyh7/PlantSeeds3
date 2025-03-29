package com.bps.plantseeds3.presentation.components

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun CapitalizedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    minLines: Int = 1,
    isError: Boolean = false
) {
    val TAG = "CapitalizedTextField"

    Log.d(TAG, "Visar textfält: $label")
    OutlinedTextField(
        value = value,
        onValueChange = { 
            Log.d(TAG, "Uppdaterar värde för $label: $it")
            onValueChange(it)
        },
        label = { 
            Text(
                text = if (isRequired) "$label *" else label,
                textAlign = TextAlign.Start
            )
        },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        isError = isError || (isRequired && value.isBlank())
    )
} 