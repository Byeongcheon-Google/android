package com.bcgg.feature.planeditor.compose.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.component.SearchAppBar
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.feature.planeditor.compose.editor.EditorContainer
import com.bcgg.feature.planeditor.compose.map.MapSearchResultContainer
import com.bcgg.feature.planeditor.viewmodel.PlanEditorViewModel
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap
import kotlinx.coroutines.launch

@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlanEditorScreen(
    viewModel: PlanEditorViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.snackbarState != null) {
        LaunchedEffect(uiState.snackbarState) {
            val message = when (uiState.snackbarState) {
                UiState.SnackbarState.NotAvailableTime -> "${uiState.selectedDate}의 마지막 일정이 23시 50분을 넘어 일정을 추가할 수 없습니다."
                else -> "알 수 없는 오류가 발생했습니다."
            }
            snackbarHostState.showSnackbar(message)
            viewModel.switchSnackbarState(null)
        }
    }

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
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
                        coroutineScope.launch {
                            viewModel.queryChannel.emit(it)
                        }
                    },
                    placeholderText = "지역, 장소 검색"
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                ) {
                    AnimatedVisibility(visible = uiState.isShowSearchContainer) {
                        MapSearchResultContainer(
                            mapSearchResults = uiState.mapSearchResult ?: listOf(),
                            onAddButtonClick = {
                                viewModel.addItem(it)
                            },
                            onRemoveButtonClick = {
                                viewModel.removeItem(it)
                            },
                            onItemClick = {},
                            expanded = uiState.expanded == UiState.Expanded.SearchResult,
                            onRequestExpandButtonClicked = {
                                viewModel.requestExpandSearchContainer()
                            },
                            destinations = uiState.schedule.destinations.filter {
                                it.comeTime.toLocalDate() == uiState.selectedDate
                            }
                        )
                    }

                    EditorContainer(
                        expanded = uiState.expanded == UiState.Expanded.ScheduleEdit,
                        schedule = uiState.schedule,
                        selectedDate = uiState.selectedDate,
                        onExpandButtonClicked = {
                            viewModel.requestExpandEditorContainer()
                        },
                        onDateClick = {
                            viewModel.changeSelectedDate(it)
                        },
                        onDestinationChanged = { oldDestination, newDestination ->
                            viewModel.updateDestination(oldDestination, newDestination)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditorScreenPreview() {
    PlanEditorScreen()
}
