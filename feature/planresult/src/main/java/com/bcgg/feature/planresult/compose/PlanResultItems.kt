package com.bcgg.feature.planresult.compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.component.ClockIcon
import com.bcgg.core.ui.component.DateItem
import com.bcgg.core.ui.component.IndicatorType
import com.bcgg.core.ui.component.RowWithIndicator
import com.bcgg.core.ui.component.getIndicatorType
import com.bcgg.core.ui.preview.PreviewContainer
import com.bcgg.core.ui.util.format
import com.bcgg.core.util.date.hourMinuteFormatter
import com.bcgg.core.util.ext.duration
import com.bcgg.feature.planresult.state.PlanResultItem
import com.naver.maps.geometry.LatLng
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlanResultItems(
    planResultItems: List<PlanResultItem>,
    onPlanResultItemSelect: (PlanResultItem) -> Unit,
    selectedDate: LocalDate,
    onSelectedDateChange: (LocalDate) -> Unit
) {
    val pagerState = rememberPagerState()
    val dates = planResultItems.map { it.date }.distinct().sorted()

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onSelectedDateChange(dates[it])
        }
    }

    LaunchedEffect(selectedDate) {
        pagerState.animateScrollToPage(dates.indexOf(selectedDate))
    }

    Column {
        LazyRow(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dates.size, key = { dates[it] }) { position ->
                DateItem(
                    date = dates[position],
                    isSelected = selectedDate == dates[position],
                    isValid = true,
                    onClick = onSelectedDateChange
                )
            }
        }

        HorizontalPager(
            pageCount = dates.count(),
            state = pagerState,

        ) { page ->
            val content = planResultItems.filter { it.date == dates[page] }
            LazyColumn {
                items(content.size) { position ->
                    when (val item = content[position]) {
                        is PlanResultItem.Move -> PlanResultMoveItem(
                            indicatorType = getIndicatorType(
                                position,
                                content.size
                            ),
                            move = item,
                            onItemClick = onPlanResultItemSelect
                        )
                        is PlanResultItem.Place -> PlanResultPlaceItem(
                            indicatorType = getIndicatorType(
                                position,
                                content.size
                            ),
                            place = item,
                            onItemClick = onPlanResultItemSelect
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun PlanResultMoveItem(
    indicatorType: IndicatorType,
    move: PlanResultItem.Move,
    onItemClick: (PlanResultItem.Move) -> Unit
) {
    RowWithIndicator(
        modifier = Modifier.clickable { onItemClick(move) },
        indicatorType = indicatorType
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
    place: PlanResultItem.Place,
    onItemClick: (PlanResultItem.Place) -> Unit
) {
    RowWithIndicator(
        modifier = Modifier.clickable { onItemClick(place) },
        indicatorType = indicatorType
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
                ClockIcon(
                    modifier = Modifier.size(12.dp),
                    timeRange = place.timeRange
                )
                Text(
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
    val item = PlanResultItem.Move(
        distanceDescription = "620m 이동",
        date = LocalDate.now(),
        timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
        points = listOf(
            LatLng(36.7612467, 127.2817232),
            LatLng(36.7655739, 127.2823278)
        )
    )

    PreviewContainer {
        PlanResultMoveItem(indicatorType = IndicatorType.Header, move = item) {

        }
    }
}


@Composable
@Preview
@Suppress("MagicNumber")
internal fun PlanResultPlaceItemPreview() {
    val item = PlanResultItem.Place(
        name = "한국기술교육대학교 솔빛관",
        date = LocalDate.now(),
        timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
        point = LatLng(36.7612467, 127.2817232)
    )

    PreviewContainer {
        PlanResultPlaceItem(indicatorType = IndicatorType.Header, place = item) {

        }
    }
}

@Composable
@Preview
@Suppress("MagicNumber")
internal fun PlanResultItemsPreview() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val items = listOf(
        PlanResultItem.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now(),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232)
        ),
        PlanResultItem.Move(
            distanceDescription = "620m 이동",
            date = LocalDate.now(),
            timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
            points = listOf(
                LatLng(36.7612467, 127.2817232),
                LatLng(36.7655739, 127.2823278)
            )
        ),
        PlanResultItem.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now(),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232)
        ),
        PlanResultItem.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now().plusDays(1),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232)
        ),
        PlanResultItem.Move(
            distanceDescription = "620m 이동",
            date = LocalDate.now().plusDays(1),
            timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
            points = listOf(
                LatLng(36.7612467, 127.2817232),
                LatLng(36.7655739, 127.2823278)
            )
        ),
        PlanResultItem.Place(
            name = "한국기술교육대학교 솔빛관",
            date = LocalDate.now().plusDays(1),
            timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
            point = LatLng(36.7612467, 127.2817232)
        ),
    )

    PreviewContainer {
        PlanResultItems(
            planResultItems = items,
            onPlanResultItemSelect = {},
            selectedDate = selectedDate,
            onSelectedDateChange = { selectedDate = it }
        )
    }
}