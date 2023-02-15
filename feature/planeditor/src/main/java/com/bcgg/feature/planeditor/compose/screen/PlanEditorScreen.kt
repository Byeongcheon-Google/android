package com.bcgg.feature.planeditor.compose.screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.utils.EdgeToEdge
import com.bcgg.feature.planeditor.compose.editor.EditorContainer
import com.bcgg.feature.planeditor.compose.map.MapSearchResultContainer
import com.bcgg.feature.planeditor.compose.search.SearchBar
import com.bcgg.feature.planeditor.constant.Constant.DEFAULT_ZOOM
import com.bcgg.feature.planeditor.constant.Constant.MAP_CAMERA_ANIMATION_DURATION
import com.bcgg.feature.planeditor.constant.Constant.MAP_MARKER_Z_INDEX
import com.bcgg.feature.planeditor.constant.Constant.MAP_SELECTED_MARKER_Z_INDEX
import com.bcgg.feature.planeditor.constant.Constant.SEARCH_ZOOM
import com.bcgg.feature.planeditor.constant.Constant.SEOUL_LAT
import com.bcgg.feature.planeditor.constant.Constant.SEOUL_LNG
import com.bcgg.feature.planeditor.viewmodel.PlanEditorViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.util.MarkerIcons

@SuppressLint("RememberReturnType")
@OptIn(ExperimentalNaverMapApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PlanEditorScreen(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useNaviType: Boolean = true,
    viewModel: PlanEditorViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onBack: () -> Unit = {}
) {
    val localDensity = LocalDensity.current
    val snackbarHostState = remember { SnackbarHostState() }
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(SEOUL_LAT, SEOUL_LNG), DEFAULT_ZOOM)
    }
    val uiState by viewModel.uiState.collectAsState()
    val statusBarPaddingValues = WindowInsets.systemBars.asPaddingValues()
    val navigationBarPaddingValues = WindowInsets.navigationBars.asPaddingValues()
    var mapTopPadding by remember { mutableStateOf(0.dp) }
    var mapBottomPadding by remember { mutableStateOf(0.dp) }
    val lazyListState = rememberLazyListState()

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

    if (uiState.selectedSearchResult != null) {
        LaunchedEffect(uiState.selectedSearchResult) {
            viewModel.requestExpandSearchContainer()
            with(uiState.selectedSearchResult!!) {
                cameraPositionState.move(
                    CameraUpdate
                        .scrollAndZoomTo(
                            LatLng(lat, lng),
                            SEARCH_ZOOM
                        )
                        .animate(CameraAnimation.Easing, MAP_CAMERA_ANIMATION_DURATION)
                )
                if (!uiState.mapSearchResult.isNullOrEmpty()) {
                    val position = uiState.mapSearchResult!!.indexOf(uiState.selectedSearchResult)
                    if (position >= 0) {
                        lazyListState.animateScrollToItem(
                            position,
                            with(localDensity) { -60.dp.toPx() }.toInt()
                        )
                    }
                }
            }
        }
    }

    fun handleBackPress() {
        when {
            uiState.expanded != null -> viewModel.requestShrinkContainer()
            else -> onBack()
        }
    }

    BackHandler {
        handleBackPress()
    }

    EdgeToEdge()

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            contentWindowInsets = WindowInsets(0.dp)
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                NaverMap(
                    modifier = Modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    onLocationChange = {
                        viewModel.setMapLatLng(
                            lat = it.latitude,
                            lng = it.longitude
                        )
                    },
                    contentPadding = PaddingValues(
                        top = mapTopPadding +
                            statusBarPaddingValues.calculateTopPadding(),
                        bottom = mapBottomPadding +
                            navigationBarPaddingValues.calculateBottomPadding()
                    ),
                    properties = MapProperties(
                        mapType = if (useNaviType) MapType.Navi else MapType.Basic,
                        isNightModeEnabled = useDarkTheme,
                        isIndoorEnabled = true
                    )
                ) {
                    uiState.mapSearchResult?.map { result ->
                        Marker(
                            state = MarkerState(position = LatLng(result.lat, result.lng)),
                            captionText = result.name,
                            onClick = {
                                viewModel.selectSearchResult(result)
                                false
                            },
                            icon = MarkerIcons.BLACK,
                            iconTintColor = if (uiState.selectedSearchResult == result) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.secondary
                            },
                            zIndex = if (uiState.selectedSearchResult == result) {
                                MAP_SELECTED_MARKER_Z_INDEX
                            } else {
                                MAP_MARKER_Z_INDEX
                            }
                        )
                    }
                }

                SearchBar(
                    modifier = Modifier
                        .padding(statusBarPaddingValues)
                        .onGloballyPositioned {
                            with(localDensity) {
                                mapTopPadding = it.size.height.toDp()
                            }
                        },
                    search = uiState.search,
                    onSearchTextChanged = { viewModel.updateSearchText(it) },
                    onSearch = {
                        viewModel.search(it)
                    },
                    isSearching = uiState.isSearching,
                    onBack = {
                        handleBackPress()
                    }
                )

                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .padding(navigationBarPaddingValues)
                        .onGloballyPositioned {
                            with(localDensity) {
                                mapBottomPadding = it.size.height.toDp()
                            }
                        },
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
                            onItemClick = {
                                viewModel.selectSearchResult(it)
                            },
                            expanded = uiState.expanded == UiState.Expanded.SearchResult,
                            onRequestExpandButtonClicked = {
                                viewModel.requestExpandSearchContainer()
                            },
                            destinations = uiState.schedule.destinations.filter {
                                it.comeTime.toLocalDate() == uiState.selectedDate
                            },
                            selectedSearchResult = uiState.selectedSearchResult,
                            lazyListState = lazyListState
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
