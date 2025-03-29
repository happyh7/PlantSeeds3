package com.bps.plantseeds3.presentation.components

import android.util.Log
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TextAreaField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isRequired: Boolean = false,
    minLines: Int = 3,
    maxLines: Int = 5
) {
    val TAG = "TextAreaField"

    Log.d(TAG, "Visar textområde: $label")
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        singleLine = false,
        minLines = minLines,
        maxLines = maxLines,
        isError = isRequired && value.isBlank()
    )
} 