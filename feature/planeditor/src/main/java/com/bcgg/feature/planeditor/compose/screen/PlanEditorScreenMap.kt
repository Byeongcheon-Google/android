package com.bcgg.feature.planeditor.compose.screen

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
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.bcgg.core.domain.constant.MapConstant
import com.bcgg.core.domain.constant.MapConstant.SEARCH_ZOOM
import com.bcgg.core.ui.provider.LocalMarkerColors
import com.bcgg.core.ui.util.rememberFusedLocationSource
import com.bcgg.feature.planeditor.R
import com.bcgg.feature.planeditor.compose.map.MapSearchResultContainer
import com.bcgg.feature.planeditor.compose.search.SearchBar
import com.bcgg.feature.planeditor.constant.Constant
import com.bcgg.feature.planeditor.constant.Constant.MAP_CAMERA_ANIMATION_DURATION
import com.bcgg.feature.planeditor.viewmodel.PlanEditorViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapType
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.flow.count

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun PlanEditorMapScreen(
    mapPadding: PaddingValues,
    onBack: () -> Unit,
    planEditorViewModel: PlanEditorViewModel = hiltViewModel(),
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useNaviType: Boolean = true
) {
    val localDensity = LocalDensity.current
    var mapBottomPadding by remember { mutableStateOf(0.dp) }
    val statusBarPaddingValues = WindowInsets.systemBars.asPaddingValues()
    val navigationBarPaddingValues = WindowInsets.navigationBars.asPaddingValues()
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            LatLng(MapConstant.SEOUL_LAT, MapConstant.SEOUL_LNG),
            MapConstant.DEFAULT_ZOOM
        )
    }

    val lazyListState = rememberLazyListState()

    val uiState by planEditorViewModel.mapUiState.collectAsState()
    val isLoading by planEditorViewModel.isLoading.collectAsState()
    val placeSearchResult = uiState.placeSearchResult?.collectAsLazyPagingItems()
    val markerColors = LocalMarkerColors.current

    //if (placeSearchResult != null && placeSearchResult.itemCount > 0) {
        LaunchedEffect(uiState.placeSearchResult) {
            if ((uiState.placeSearchResult?.count() ?: 0) <= 0) return@LaunchedEffect
            planEditorViewModel.selectSearchResult(0)
        }
    //}

    LaunchedEffect(uiState.selectedSearchPosition) {
        if (uiState.selectedSearchPosition < 0) return@LaunchedEffect
        if (uiState.selectedSearchPosition >= (placeSearchResult?.itemCount ?: 0)) return@LaunchedEffect
        placeSearchResult?.get(uiState.selectedSearchPosition)?.let {
            cameraPositionState.move(
                CameraUpdate
                    .scrollAndZoomTo(
                        LatLng(it.lat, it.lng),
                        SEARCH_ZOOM
                    )
                    .animate(CameraAnimation.Easing, MAP_CAMERA_ANIMATION_DURATION)
            )
            lazyListState.animateScrollToItem(
                uiState.selectedSearchPosition,
                with(localDensity) { -100.dp.toPx() }.toInt()
            )
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        NaverMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            contentPadding = PaddingValues(
                top = mapPadding.calculateTopPadding() +
                        statusBarPaddingValues.calculateTopPadding() + 72.dp,
                bottom = mapPadding.calculateBottomPadding() +
                        navigationBarPaddingValues.calculateBottomPadding() +
                        mapBottomPadding
            ),
            properties = MapProperties(
                mapType = if (useNaviType) MapType.Navi else MapType.Basic,
                isNightModeEnabled = useDarkTheme,
                isIndoorEnabled = true,
                locationTrackingMode = LocationTrackingMode.Follow,
            ),
            locationSource = rememberFusedLocationSource(),
            uiSettings = MapUiSettings(
                isLocationButtonEnabled = true,
            )
        ) {
            uiState.otherMapPositions.mapIndexed { index, (id, lat, lng) ->
                Marker(
                    state = MarkerState(position = LatLng(lat, lng)),
                    captionText = id,
                    icon = OverlayImage.fromResource(R.drawable.marker_user_position),
                    iconTintColor = markerColors[index]
                )
            }

            placeSearchResult?.itemSnapshotList?.mapIndexed { index, result ->
                if (result != null) {
                    val isSelected = index == uiState.selectedSearchPosition

                    Marker(
                        state = MarkerState(position = LatLng(result.lat, result.lng)),
                        captionText = result.name,
                        onClick = {
                            planEditorViewModel.selectSearchResult(index)
                            false
                        },
                        icon = MarkerIcons.BLACK,
                        iconTintColor = if (isSelected) {
                            androidx.compose.material3.MaterialTheme.colorScheme.primary
                        } else {
                            androidx.compose.material3.MaterialTheme.colorScheme.secondary
                        },
                        zIndex = if (isSelected) {
                            Constant.MAP_SELECTED_MARKER_Z_INDEX
                        } else {
                            Constant.MAP_MARKER_Z_INDEX
                        },
                        isHideCollidedCaptions = true,
                        isHideCollidedMarkers = true,
                        isHideCollidedSymbols = true
                    )
                }
            }
        }

        SearchBar(
            modifier = Modifier
                .padding(statusBarPaddingValues),
            search = uiState.search,
            onSearchTextChanged = planEditorViewModel::updateSearchText,
            onSearch = {
                planEditorViewModel.search(uiState.search, cameraPositionState.position.target)
            },
            isSearching = uiState.isSearching,
            onBack = onBack,
            selectedDate = uiState.selectedDate
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp)
                .padding(bottom = navigationBarPaddingValues.calculateBottomPadding() + mapPadding.calculateBottomPadding())
                .onGloballyPositioned {
                    with(localDensity) {
                        mapBottomPadding = it.size.height.toDp()
                    }
                },
        ) {
            AnimatedVisibility(visible = placeSearchResult != null) {
                MapSearchResultContainer(
                    placeSearchResults = placeSearchResult!!,
                    onAddButtonClick = {
                        planEditorViewModel.addItem(it)
                    },
                    onItemClick = { position, _ ->
                        planEditorViewModel.selectSearchResult(position)
                    },
                    expanded = uiState.expanded,
                    onRequestExpandButtonClicked = {
                        planEditorViewModel.expandSearchResult()
                    },
                    addedPlaceSearchResult = uiState.addedSearchResults,
                    selectedSearchResultPosition = uiState.selectedSearchPosition,
                    lazyListState = lazyListState
                )
            }
        }
    }
}