package com.bcgg.feature.planresult.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.Classification
import com.bcgg.core.ui.component.ClockIcon
import com.bcgg.core.ui.component.DateItem
import com.bcgg.core.ui.component.IndicatorType
import com.bcgg.core.ui.component.RowWithIndicator
import com.bcgg.core.ui.component.getIndicatorType
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowdown
import com.bcgg.core.ui.icon.icons.Arrowright
import com.bcgg.core.ui.icon.icons.Arrowup
import com.bcgg.core.ui.icon.icons.Food
import com.bcgg.core.ui.icon.icons.Home
import com.bcgg.core.ui.icon.icons.Rocketfly
import com.bcgg.core.ui.preview.PreviewContainer
import com.bcgg.core.ui.util.format
import com.bcgg.core.util.date.hourMinuteFormatter
import com.bcgg.core.util.ext.duration
import com.bcgg.feature.planresult.state.PlanResultItemUiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanResultItems(
    modifier: Modifier = Modifier,
    planName: String,
    planResultItemUiStates: List<PlanResultItemUiState>,
    selectedItem: PlanResultItemUiState?,
    onPlanResultItemSelect: (PlanResultItemUiState) -> Unit,
    selectedDate: LocalDate,
    onSelectedDateChange: (LocalDate) -> Unit
) {
    val pagerState = rememberPagerState()
    val dates = planResultItemUiStates.map { it.date }.distinct().sorted()
    var isPlanResultItemsFolded by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onSelectedDateChange(dates[it])
        }
    }

    LaunchedEffect(selectedDate) {
        pagerState.animateScrollToPage(dates.indexOf(selectedDate))
    }

    Column(
        modifier = modifier
    ) {
        Row {
            LazyRow(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = planName,
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
                items(dates.size, key = { dates[it] }) { position ->
                    DateItem(
                        date = dates[position],
                        isSelected = selectedDate == dates[position],
                        isValid = true,
                        onClick = {
                            isPlanResultItemsFolded = false
                            onSelectedDateChange(it)
                        }
                    )
                }
            }

            Icon(
                modifier = Modifier
                    .clickable {
                        isPlanResultItemsFolded = !isPlanResultItemsFolded
                    }
                    .padding(16.dp),
                imageVector = if (isPlanResultItemsFolded) Icons.Arrowup else Icons.Arrowdown,
                contentDescription = ""
            )
        }

        HorizontalPager(
            pageCount = dates.count(),
            state = pagerState,

            ) { page ->
            val content = planResultItemUiStates.filter { it.date == dates[page] }
            AnimatedVisibility(visible = !isPlanResultItemsFolded) {
                LazyColumn(modifier = Modifier.fillMaxHeight(0.5f)) {
                    items(content.size) { position ->
                        when (val item = content[position]) {
                            is PlanResultItemUiState.Move -> PlanResultMoveItem(
                                indicatorType = getIndicatorType(
                                    position,
                                    content.size
                                ),
                                move = item,
                                isSelected = planResultItemUiStates[position] == selectedItem,
                                onItemClick = onPlanResultItemSelect
                            )

                            is PlanResultItemUiState.Place -> PlanResultPlaceItem(
                                indicatorType = getIndicatorType(
                                    position,
                                    content.size
                                ),
                                place = item,
                                isSelected = planResultItemUiStates[position] == selectedItem,
                                onItemClick = onPlanResultItemSelect
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun PlanResultMoveItem(
    indicatorType: IndicatorType,
    move: PlanResultItemUiState.Move,
    isSelected: Boolean,
    onItemClick: (PlanResultItemUiState.Move) -> Unit
) {
    RowWithIndicator(
        modifier = Modifier
            .clickable { onItemClick(move) }
            .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
            ),
        indicatorType = indicatorType,
        lineColor = MaterialTheme.colorScheme.secondaryContainer,
        circleColor = move.circleColor,
        circleContent = {
            Icon(Icons.Arrowright, contentDescription = null, modifier = Modifier.size(16.dp))
        },
        circleSize = 24.dp

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = move.distanceDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = move.timeRange.duration.format(),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
internal fun PlanResultPlaceItem(
    indicatorType: IndicatorType,
    place: PlanResultItemUiState.Place,
    isSelected: Boolean,
    onItemClick: (PlanResultItemUiState.Place) -> Unit
) {
    RowWithIndicator(
        modifier = Modifier
            .clickable { onItemClick(place) }
            .background(
                if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface
            ),
        indicatorType = indicatorType,
        lineColor = MaterialTheme.colorScheme.secondaryContainer,
        circleColor = place.circleColor,
        circleContent = {
            Text(
                text = place.number.toString(), style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        },
        circleSize = 24.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(x = (-24).dp, y = (-8).dp),
                    imageVector = when (place.classification) {
                        Classification.Travel -> Icons.Rocketfly
                        Classification.House -> Icons.Home
                        Classification.Food -> Icons.Food
                    },
                    contentDescription = null,
                    tint = place.circleColor
                )
                ClockIcon(
                    modifier = Modifier
                        .size(16.dp)
                        .offset(x = (-26).dp),
                    timeRange = place.timeRange
                )
                Text(
                    modifier = Modifier
                        .offset(x = (-26).dp),
                    text = place.timeRange.start.format(hourMinuteFormatter) +
                            " ~ " +
                            place.timeRange.endInclusive.format(hourMinuteFormatter),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Composable
@Preview
@Suppress("MagicNumber")
internal fun PlanResultMoveItemPreview() {
    val item = PlanResultItemUiState.Move(
        distanceDescription = "620m 이동",
        date = LocalDate.now(),
        timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
        points = listOf(
            LatLng(36.7612467, 127.2817232),
            LatLng(36.7655739, 127.2823278)
        ),
        bound = LatLngBounds(
            LatLng(36.7612467, 127.2817232),
            LatLng(36.7655739, 127.2823278)
        ),
        startPlace = LatLng(36.7612467, 127.2817232),
        endPlace = LatLng(36.7655739, 127.2823278)
    )

    PreviewContainer {
        PlanResultMoveItem(indicatorType = IndicatorType.Header, move = item, isSelected = false) {

        }
    }
}


/*@Composable
@Preview
@Suppress("MagicNumber")
internal fun PlanResultPlaceItemPreview() {
    val item = PlanResultItemUiState.Place(
        name = "한국기술교육대학교 솔빛관",
        date = LocalDate.now(),
        timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
        point = LatLng(36.7612467, 127.2817232),
        number = 1
    )

    PreviewContainer {
        PlanResultPlaceItem(indicatorType = IndicatorType.Header, place = item, isSelected = true) {

        }
    }
}

@Composable
@Preview
@Suppress("MagicNumber")
internal fun PlanResultItemsPreview() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val items = listOf(
        PlanResultItemUiState.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now(),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232),
            number = 1
        ),
        PlanResultItemUiState.Move(
            distanceDescription = "620m 이동",
            date = LocalDate.now(),
            timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
            points = listOf(
                LatLng(36.7612467, 127.2817232),
                LatLng(36.7655739, 127.2823278)
            ),
            bound = LatLngBounds(
                LatLng(36.7612467, 127.2817232),
                LatLng(36.7655739, 127.2823278)
            ),
            startPlace = LatLng(36.7612467, 127.2817232),
            endPlace = LatLng(36.7655739, 127.2823278)
        ),
        PlanResultItemUiState.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now(),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232),
            number = 1
        ),
        PlanResultItemUiState.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now().plusDays(1),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232),
            number = 1
        ),
        PlanResultItemUiState.Move(
            distanceDescription = "620m 이동",
            date = LocalDate.now().plusDays(1),
            timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
            points = listOf(
                LatLng(36.7612467, 127.2817232),
                LatLng(36.7655739, 127.2823278)
            ),
            bound = LatLngBounds(
                LatLng(36.7612467, 127.2817232),
                LatLng(36.7655739, 127.2823278)
            ),
            startPlace = LatLng(36.7612467, 127.2817232),
            endPlace = LatLng(36.7655739, 127.2823278)
        ),
        PlanResultItemUiState.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now().plusDays(1),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232),
            number = 1
        ),
    )

    PreviewContainer {
        PlanResultItems(
            planName = "TEST",
            planResultItemUiStates = items,
            onPlanResultItemSelect = {},
            selectedDate = selectedDate,
            selectedItem = items[0],
            onSelectedDateChange = { selectedDate = it }
        )
    }
}*/