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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedAssistChip
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.util.Classification
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.util.PlaceSearchResultWithId
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Remove
import com.bcgg.core.ui.preview.PreviewContainer
import com.bcgg.core.ui.theme.divider
import com.bcgg.feature.planeditor.R
import com.bcgg.core.ui.component.ClockIcon
import com.bcgg.feature.planeditor.compose.slider.StayTimeSlider
import com.bcgg.feature.planeditor.util.getClassificationIcon
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorDestinationItem(
    placeSearchResultWithId: PlaceSearchResultWithId,
    stayTimeHour: Int,
    classification: Classification,
    showDivider: Boolean,
    isStartPosition: Boolean,
    isEndPosition: Boolean,
    onStayTimeChange: (Int) -> Unit,
    onRemove: (PlaceSearchResultWithId) -> Unit,
    onSelectStartPosition: (PlaceSearchResultWithId) -> Unit,
    onSelectEndPosition: (PlaceSearchResultWithId) -> Unit,
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
                text = placeSearchResultWithId.placeSearchResult.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Text(
            text = placeSearchResultWithId.placeSearchResult.address,
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
                            stayTimeHour
                        )
                    )
                },
                leadingIcon = {
                    ClockIcon(
                        modifier = Modifier.size(20.dp),
                        timeRange = LocalTime.MIN..LocalTime.MIN.plusHours(
                            stayTimeHour.toLong()
                        )
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
                        imageVector = getClassificationIcon(classification),
                        contentDescription = ""
                    )
                }
            )


            ElevatedAssistChip(
                onClick = {
                    onSelectStartPosition(placeSearchResultWithId)
                },
                label = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.start),
                        contentDescription = "출발 여행지",
                        tint = if(isStartPosition) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if(isStartPosition) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            )
            ElevatedAssistChip(
                onClick = {
                    onSelectEndPosition(placeSearchResultWithId)
                },
                label = {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(id = R.drawable.end),
                        contentDescription = "마지막 여행지",
                        tint = if(isEndPosition) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                colors = AssistChipDefaults.assistChipColors(
                    containerColor = if(isEndPosition) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant
                    }
                )
            )

            ElevatedAssistChip(
                onClick = {
                    onRemove(placeSearchResultWithId)
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
            stayTimeHour = stayTimeHour,
            onStayTimeChange = {
                onStayTimeChange(
                    it
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
                    classification = Classification.Travel
                ),
                Destination(
                    name = "터미널",
                    address = "몰루",
                    stayTimeHour = 2,
                    date = LocalDate.now(),
                    classification = Classification.Travel
                ),
                Destination(
                    name = "천안역",
                    address = "몰루",
                    stayTimeHour = 2,
                    date = LocalDate.now(),
                    classification = Classification.Travel
                )
            )

        )
    }

    PreviewContainer {

    }
}
