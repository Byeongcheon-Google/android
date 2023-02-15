package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.ui.preview.PreviewContainer
import com.bcgg.core.ui.theme.divider
import com.bcgg.core.util.date.hourMinuteFormatter
import com.bcgg.feature.planeditor.R
import com.bcgg.feature.planeditor.compose.clock.ClockIcon
import com.bcgg.feature.planeditor.compose.indicator.EditorDestinationItemIndicator
import com.bcgg.feature.planeditor.compose.indicator.IndicatorType
import com.bcgg.feature.planeditor.compose.slider.ComeTimeSlider
import com.bcgg.feature.planeditor.compose.slider.StayTimeSlider
import com.bcgg.feature.planeditor.util.calculateDestinationAvailableTime
import com.bcgg.feature.planeditor.util.getDestinationTypeIcon
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorDestinationItem(
    indicatorType: IndicatorType,
    destination: Destination,
    showDivider: Boolean,
    availableTime: ClosedRange<LocalTime>?,
    onChange: (Destination) -> Unit
) {
    if (availableTime == null) {
        Column {
            Text(
                text = destination.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "Not available",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        return
    }

    var selectedChip: SelectedChip? by rememberSaveable {
        mutableStateOf(null)
    }

    val localDensity = LocalDensity.current

    var height by remember {
        mutableStateOf(0.dp)
    }

    Row {
        EditorDestinationItemIndicator(
            modifier = Modifier.height(height),
            indicatorType = indicatorType
        )
        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .padding(vertical = 8.dp)
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
                    selected = selectedChip == SelectedChip.ComeTime,
                    onClick = {
                        selectedChip = if (selectedChip != SelectedChip.ComeTime) {
                            SelectedChip.ComeTime
                        } else {
                            null
                        }
                    },
                    label = {
                        Text(text = destination.comeTime.format(hourMinuteFormatter))
                    },
                    leadingIcon = {
                        ClockIcon(
                            modifier = Modifier.size(24.dp),
                            time1 = destination.comeTime.toLocalTime()
                        )
                    }
                )
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
                            time1 = destination.comeTime.toLocalTime(),
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
            }

            EditorDestinationItemComeTimeOption(
                show = selectedChip == SelectedChip.ComeTime,
                comeTime = destination.comeTime.toLocalTime(),
                availableTime = availableTime,
                onComeTimeChange = { newComeTime, maxStayTimeHour ->
                    onChange(
                        destination.copy(
                            comeTime = LocalDateTime.of(
                                destination.comeTime.toLocalDate(),
                                newComeTime
                            ),
                            stayTimeHour = if (indicatorType != IndicatorType.Footer &&
                                maxStayTimeHour < destination.stayTimeHour
                            ) {
                                maxStayTimeHour
                            } else {
                                destination.stayTimeHour
                            }
                        )
                    )
                }
            )

            EditorDestinationItemStayTimeOption(
                show = selectedChip == SelectedChip.StayTime,
                stayTimeHour = destination.stayTimeHour,
                onStayTimeChange = {
                    onChange(
                        destination.copy(
                            stayTimeHour = it
                        )
                    )
                },
                availableTime = availableTime,
                comeTime = destination.comeTime.toLocalTime(),
                unlimited = indicatorType == IndicatorType.Footer || indicatorType == IndicatorType.One
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
}

@Composable
fun EditorDestinationItemComeTimeOption(
    show: Boolean,
    comeTime: LocalTime,
    availableTime: ClosedRange<LocalTime>,
    onComeTimeChange: (LocalTime, Int) -> Unit
) {
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically() + expandVertically() + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        ComeTimeSlider(
            comeTime = comeTime,
            availableTime = availableTime,
            onComeTimeChange = onComeTimeChange
        )
    }
}

@Composable
fun EditorDestinationItemStayTimeOption(
    show: Boolean,
    comeTime: LocalTime,
    stayTimeHour: Int,
    availableTime: ClosedRange<LocalTime>,
    unlimited: Boolean,
    onStayTimeChange: (Int) -> Unit
) {
    AnimatedVisibility(
        visible = show,
        enter = slideInVertically() + expandVertically() + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        StayTimeSlider(
            comeTime = comeTime,
            stayTimeHour = stayTimeHour,
            availableTime = availableTime,
            unlimited = unlimited,
            onStayTimeChange = onStayTimeChange
        )
    }
}

enum class SelectedChip {
    ComeTime, StayTime, Type
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
                    comeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                    type = Destination.Type.Travel
                ),
                Destination(
                    name = "터미널",
                    address = "몰루",
                    stayTimeHour = 2,
                    comeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0)),
                    type = Destination.Type.Travel
                ),
                Destination(
                    name = "천안역",
                    address = "몰루",
                    stayTimeHour = 2,
                    comeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)),
                    type = Destination.Type.Travel
                )
            )

        )
    }

    PreviewContainer {
        LazyColumn {
            items(destinations.size) { position ->
                EditorDestinationItem(
                    indicatorType = when (position) {
                        0 -> IndicatorType.Header
                        destinations.lastIndex -> IndicatorType.Footer
                        else -> if (destinations.size > 1)IndicatorType.Normal else IndicatorType.One
                    },
                    availableTime = calculateDestinationAvailableTime(
                        beforeDestination = if (position > 0) destinations[position - 1] else null,
                        afterDestination = if (position < destinations.lastIndex) destinations[position + 1] else null
                    ),
                    destination = destinations[position],
                    showDivider = position != destinations.lastIndex,
                    onChange = { newDestination ->
                        destinations = destinations.mapIndexed { index, oldDestination ->
                            if (index == position) newDestination else oldDestination
                        }
                    }
                )
            }
        }
    }
}
