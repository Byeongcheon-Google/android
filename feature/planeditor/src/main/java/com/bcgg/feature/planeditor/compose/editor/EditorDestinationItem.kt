package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Remove
import com.bcgg.core.ui.preview.PreviewContainer
import com.bcgg.core.ui.theme.divider
import com.bcgg.feature.planeditor.R
import com.bcgg.feature.planeditor.compose.clock.ClockIcon
import com.bcgg.feature.planeditor.compose.slider.StayTimeSlider
import com.bcgg.feature.planeditor.util.getDestinationTypeIcon
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorDestinationItem(
    destination: Destination,
    showDivider: Boolean,
    onChange: (Destination) -> Unit,
    onRemove: (Destination) -> Unit
) {
    var selectedChip: SelectedChip? by rememberSaveable {
        mutableStateOf(null)
    }

    val localDensity = LocalDensity.current

    var height by remember {
        mutableStateOf(0.dp)
    }
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .onGloballyPositioned {
                with(localDensity) { height = it.size.height.toDp() + 16.dp }
            }
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = destination.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Text(
            text = destination.address,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.padding(top = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ElevatedFilterChip(
                selected = selectedChip == SelectedChip.StayTime,
                onClick = {
                    selectedChip = if (selectedChip != SelectedChip.StayTime) {
                        SelectedChip.StayTime
                    } else {
                        null
                    }
                },
                label = {
                    Text(
                        text = stringResource(
                            id = R.string.editor_destination_item_stay_hour,
                            destination.stayTimeHour
                        )
                    )
                },
                leadingIcon = {
                    ClockIcon(
                        modifier = Modifier.size(20.dp),
                        time1 = LocalTime.MIN,
                        plusHour = destination.stayTimeHour
                    )
                }
            )
            ElevatedFilterChip(
                selected = selectedChip == SelectedChip.Type,
                onClick = {
                    selectedChip = if (selectedChip != SelectedChip.Type) {
                        SelectedChip.Type
                    } else {
                        null
                    }
                },
                label = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = getDestinationTypeIcon(destination = destination),
                        contentDescription = ""
                    )
                }
            )
            ElevatedAssistChip(
                onClick = {
                    onRemove(destination)
                },
                label = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        imageVector = Icons.Remove,
                        contentDescription = ""
                    )
                },
                colors = AssistChipDefaults.assistChipColors(containerColor = MaterialTheme.colorScheme.errorContainer)
            )
        }

        EditorDestinationItemStayTimeOption(
            show = selectedChip == SelectedChip.StayTime,
            stayTimeHour = destination.stayTimeHour,
            onStayTimeChange = {
                onChange(
                    destination.copy(
                        stayTimeHour = it
                    )
                )
            }
        )

        if (showDivider) {
            Divider(
                modifier = Modifier.padding(top = 8.dp),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.divider
            )
        }
    }
}

@Composable
fun EditorDestinationItemStayTimeOption(
    show: Boolean,
    stayTimeHour: Int,
    onStayTimeChange: (Int) -> Unit
) {
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically() + expandVertically() + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        StayTimeSlider(
            stayTimeHour = stayTimeHour,
            onStayTimeChange = onStayTimeChange
        )
    }
}

enum class SelectedChip {
    StayTime, Type
}

@Preview
@Composable
private fun EditorDestinationItemPreview() {
    var destinations by remember {
        mutableStateOf(
            listOf(
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
            )

        )
    }

    PreviewContainer {
        LazyColumn {
            items(destinations.size) { position ->
                EditorDestinationItem(
                    destination = destinations[position],
                    showDivider = position != destinations.lastIndex,
                    onChange = { newDestination ->
                        destinations = destinations.mapIndexed { index, oldDestination ->
                            if (index == position) newDestination else oldDestination
                        }
                    },
                    onRemove = {}
                )
            }
        }
    }
}
