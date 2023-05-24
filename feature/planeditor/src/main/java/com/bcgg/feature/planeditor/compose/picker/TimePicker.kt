package com.bcgg.feature.planeditor.compose.picker

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    modifier: Modifier = Modifier,
    label: String,
    value: LocalTime = LocalTime.now(),
    onValueChange: (LocalTime) -> Unit,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    pattern: String = "HH:mm",
    is24HourView: Boolean = true,
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val dialog = TimePickerDialog(
        LocalContext.current,
        { _, hour, minute -> onValueChange(LocalTime.of(hour, minute)) },
        value.hour,
        value.minute,
        is24HourView,
    )

    OutlinedTextField(
        value = value.format(formatter),
        onValueChange = {},
        enabled = false,
        label = {
            Text(text = label)
        },
        modifier = modifier.clickable { dialog.show() },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}