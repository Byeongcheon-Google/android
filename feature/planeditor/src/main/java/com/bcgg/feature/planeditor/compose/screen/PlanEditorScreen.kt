package com.bcgg.feature.planeditor.compose.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.domain.model.newSchedule
import com.bcgg.core.ui.component.SearchAppBar
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.feature.planeditor.compose.editor.EditorContainer
import com.bcgg.feature.planeditor.compose.map.MapSearchResultContainer
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlanEditorScreen() {
    var uiState by remember {
        mutableStateOf(
            UiState().copy(
                mapSearchResult = listOf(
                    MapSearchResult(
                        name = "성심당 DCC점",
                        distance = "50km",
                        address = "대전 유성구 엑스포로 107",
                        lat = 1.0,
                        lng = 1.0
                    ),
                    MapSearchResult(
                        name = "성심당 롯데백화점대전점",
                        distance = "53km",
                        address = "대전 서구 계롱로 598",
                        lat = 1.0,
                        lng = 1.0
                    ),
                    MapSearchResult(
                        name = "성심당 대전역점",
                        distance = "56km",
                        address = "대전 동구 중앙로 215",
                        lat = 1.0,
                        lng = 1.0
                    )
                ),
                schedule = newSchedule().copy(
                    destinations = listOf(
                        Destination(
                            name = "한기대",
                            address = "충남 천안시 동남구 충절로 1600",
                            lat = 1.0,
                            lng = 1.0,
                            stayTimeHour = 2,
                            comeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)),
                            type = Destination.Type.Travel
                        ),
                        Destination(
                            name = "터미널",
                            address = "몰루",
                            lat = 1.0,
                            lng = 1.0,
                            stayTimeHour = 2,
                            comeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0)),
                            type = Destination.Type.Travel
                        ),
                        Destination(
                            name = "천안역",
                            address = "몰루",
                            lat = 1.0,
                            lng = 1.0,
                            stayTimeHour = 2,
                            comeTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 0)),
                            type = Destination.Type.Travel
                        )
                    )
                )
            )
        )
    }

    AppTheme {
        Box {
            NaverMap(modifier = Modifier.fillMaxSize())
            SearchAppBar(
                navigationIcon = {
                    Icon(
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable {
                            },
                        imageVector = Icons.Arrowleft,
                        contentDescription = ""
                    )
                },
                search = uiState.search,
                onSearchTextChanged = {
                    uiState = uiState.copy(search = it, expanded = UiState.Expanded.SearchResult)
                },
                placeholderText = "지역, 장소 검색"
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            ) {
                if (uiState.isShowSearchContainer) {
                    MapSearchResultContainer(
                        mapSearchResults = uiState.mapSearchResult!!,
                        onAddButtonClick = {},
                        onRemoveButtonClick = {},
                        onItemClick = {},
                        expanded = uiState.expanded == UiState.Expanded.SearchResult,
                        onRequestExpandButtonClicked = {
                            uiState = uiState.copy(expanded = UiState.Expanded.SearchResult)
                        }
                    )
                }

                EditorContainer(
                    expanded = uiState.expanded == UiState.Expanded.ScheduleEdit,
                    schedule = uiState.schedule,
                    selectedDate = uiState.selectedDate,
                    onExpandButtonClicked = {
                        uiState = uiState.copy(expanded = UiState.Expanded.ScheduleEdit)
                    },
                    onDateClick = {
                        uiState = uiState.copy(selectedDate = it)
                    },
                    onDestinationChanged = {
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun EditorScreenPreview() {
    PlanEditorScreen()
}
