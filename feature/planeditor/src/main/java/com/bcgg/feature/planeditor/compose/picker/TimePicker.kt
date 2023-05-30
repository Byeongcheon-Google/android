package com.bcgg.feature.planeditor.compose.picker

import android.app.TimePickerDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.bcgg.core.ui.provider.LocalFragmentManager
import com.bcgg.feature.planeditor.R
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
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
) {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    val dialog = MaterialTimePicker.Builder()
        .setTimeFormat(TimeFormat.CLOCK_12H)
        .setHour(value.hour)
        .setMinute(value.minute)
        .setTitleText(label)
        .build()

    dialog.addOnPositiveButtonClickListener {
        onValueChange(LocalTime.of(dialog.hour, dialog.minute))
        dialog.dismiss()
    }

    val fragmentManager = LocalFragmentManager.current

    OutlinedTextField(
        value = value.format(formatter),
        onValueChange = {},
        enabled = false,
        label = {
            Text(text = label)
        },
        modifier = modifier.clickable { dialog.show(fragmentManager, "timepicker") },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            disabledBorderColor = MaterialTheme.colorScheme.primary,
            disabledLabelColor = MaterialTheme.colorScheme.primary,
            disabledTextColor = MaterialTheme.colorScheme.onSurface
        )
    )
}