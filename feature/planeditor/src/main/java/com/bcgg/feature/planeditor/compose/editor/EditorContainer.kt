package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.Schedule
import com.bcgg.core.ui.constant.UiConstant
import com.bcgg.core.ui.theme.AppTheme
import java.time.LocalDate

@Composable
fun EditorContainer(
    modifier: Modifier = Modifier,
    expanded: Boolean,
    schedule: Schedule,
    selectedDate: LocalDate,
    onExpandButtonClicked: () -> Unit,
    onDateClick: (LocalDate) -> Unit,
    onDestinationChanged: (oldDestination: Destination, newDestination: Destination) -> Unit,
    onDestinationRemoved: (Destination) -> Unit
) {
    val localConfigutaion = LocalConfiguration.current

    val height by animateDpAsState(
        targetValue =
        if (expanded)
            (localConfigutaion.screenHeightDp * 0.45).dp
        else 68.dp
    )
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = UiConstant.SEMI_TRANSPARENT_AMOUNT))
            .height(height)
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
            .clickable(enabled = !expanded, onClick = onExpandButtonClicked),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.height(68.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            EditorDate(
                selectedDate = selectedDate,
                dates = schedule.destinations.map { it.date }.toSet(),
                expanded = expanded,
                onDateClick = onDateClick
            )
            AnimatedVisibility(
                visible = !expanded,
                enter = expandHorizontally() + fadeIn(),
                exit = shrinkHorizontally() + fadeOut()
            ) {
                EditorCompressed(destinations = schedule.getFilteredDestinations(selectedDate))
            }
        }

        AnimatedVisibility(visible = expanded) {
            EditorExpanded(
                destinations = schedule.getFilteredDestinations(selectedDate),
                onDestinationChange = onDestinationChanged,
                onDestinationRemove = onDestinationRemoved
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.PIXEL_4)
@Composable
private fun EditorContainerPreview() {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var sample = Schedule(
        name = "SAMPLE",
        destinations = listOf(
            Destination(
                name = "한기대",
                address = "충남 천안시 동남구 충절로 1600",
                stayTimeHour = 2,
                date = LocalDate.now(),
                type = Destination.Type.Travel
            ),
            Destination(
                name = "터미널",
                address = "몰루",
                stayTimeHour = 2,
                date = LocalDate.now(),
                type = Destination.Type.Travel
            ),
            Destination(
                name = "천안역",
                address = "몰루",
                stayTimeHour = 2,
                date = LocalDate.now(),
                type = Destination.Type.Travel
            )
        ),
        status = Schedule.Status.BeforeRun
    )

    AppTheme(
        useDarkTheme = false
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = {
                EditorContainer(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    expanded = expanded,
                    schedule = sample,
                    selectedDate = selectedDate,
                    onExpandButtonClicked = {
                        expanded = true
                    },
                    onDateClick = {
                        selectedDate = it
                    },
                    onDestinationChanged = { _, _ -> },
                    onDestinationRemoved = {}
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                Button(onClick = { expanded = false }) {
                    Text(text = "Collapse")
                }
            }
        }
    }
}
