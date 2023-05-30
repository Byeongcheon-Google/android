package com.bcgg.feature.planeditor.compose.screen

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bcgg.core.domain.constant.MapConstant
import com.bcgg.core.domain.constant.MapConstant.SEARCH_ZOOM
import com.bcgg.core.util.PlaceSearchResult
import com.bcgg.core.ui.provider.LocalMarkerColors
import com.bcgg.core.ui.util.rememberFusedLocationSource
import com.bcgg.feature.planeditor.R
import com.bcgg.feature.planeditor.compose.map.MapSearchResultContainer
import com.bcgg.feature.planeditor.compose.search.SearchBar
import com.bcgg.feature.planeditor.compose.state.OptionsUiState
import com.bcgg.feature.planeditor.compose.state.PlanEditorMapUiState
import com.bcgg.feature.planeditor.compose.state.PlanEditorOptionsUiStatePerDate
import com.bcgg.feature.planeditor.constant.Constant
import com.bcgg.feature.planeditor.constant.Constant.MAP_CAMERA_ANIMATION_DURATION
import com.bcgg.feature.planeditor.util.toLatLng
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
import com.naver.maps.map.compose.NaverMapComposable
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons
import kotlinx.coroutines.flow.count

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun PlanEditorMapScreen(
    snackbarHostState: SnackbarHostState,
    mapPadding: PaddingValues,
    onBack: () -> Unit,
    planEditorViewModel: PlanEditorViewModel = hiltViewModel(),
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useNaviType: Boolean = true,
    mapUiState: PlanEditorMapUiState,
    optionsUiState: OptionsUiState,
    optionsUiStatePerDate: PlanEditorOptionsUiStatePerDate
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
    var isKakaoSearching by rememberSaveable { mutableStateOf(false) }

    val lazyListState = rememberLazyListState()

    val isLoading by planEditorViewModel.isLoading.collectAsState()
    val placeSearchResult = mapUiState.placeSearchResult?.collectAsLazyPagingItems()
    val markerColors = LocalMarkerColors.current
    val mapSearchResultPositionOffset = remember { with(localDensity) { (-40).dp.toPx().toInt() } }

    BackHandler {
        if (mapUiState.expanded) planEditorViewModel.shrinkSearchResult()
        else onBack()
    }

    LaunchedEffect(mapUiState.selectedSearchResult) {
        val selected = mapUiState.selectedSearchResult

        val addressSearchResultCount = if (optionsUiStatePerDate.aiAddressSearchResult.isEmpty()) 0 else optionsUiStatePerDate.aiAddressSearchResult.size + 1
        val aiSearchResultCount = if (mapUiState.placeSearchResultAi.isEmpty()) 0 else mapUiState.placeSearchResultAi.size + 1

        if (selected == null) {
            lazyListState.animateScrollToItem(
                0,
                mapSearchResultPositionOffset
            )
            return@LaunchedEffect
        }

        val indexInPlaceSearchResult =
            (placeSearchResult?.itemSnapshotList?.indexOf(selected) ?: -1)
        if (indexInPlaceSearchResult >= 0) {
            lazyListState.animateScrollToItem(
                addressSearchResultCount + aiSearchResultCount + indexInPlaceSearchResult,
                mapSearchResultPositionOffset
            )
            return@LaunchedEffect
        }

        val indexInAiSearchResult = (mapUiState.placeSearchResultAi.indexOf(selected))
        if (indexInAiSearchResult >= 0) {
            lazyListState.animateScrollToItem(
                1 + indexInAiSearchResult + addressSearchResultCount,
                mapSearchResultPositionOffset
            )
            return@LaunchedEffect
        }

        val indexInAiAddressSearchResult = (mapUiState.addedSearchResults.indexOf(selected))
        if (indexInAiAddressSearchResult >= 0) {
            lazyListState.animateScrollToItem(
                1 + indexInAiAddressSearchResult,
                mapSearchResultPositionOffset
            )
            return@LaunchedEffect
        }
    }

    LaunchedEffect(mapUiState.placeSearchResultAi, placeSearchResult) {
        planEditorViewModel.selectSearchResult(null)

        val addressSearchResultCount = if (optionsUiStatePerDate.aiAddressSearchResult.isEmpty()) 0 else optionsUiStatePerDate.aiAddressSearchResult.size + 1
        val aiSearchResultCount = if (mapUiState.placeSearchResultAi.isEmpty()) 0 else mapUiState.placeSearchResultAi.size + 1

        if (mapUiState.placeSearchResultAi.isNotEmpty()) {
            lazyListState.animateScrollToItem(
                addressSearchResultCount
            )
            return@LaunchedEffect
        }

        if(placeSearchResult?.itemSnapshotList?.isNotEmpty() == true) {
            lazyListState.animateScrollToItem(
                addressSearchResultCount + aiSearchResultCount
            )
            return@LaunchedEffect
        }
    }

    LaunchedEffect(mapUiState.selectedSearchResult) {
        val selected = mapUiState.selectedSearchResult ?: return@LaunchedEffect

        cameraPositionState.move(
            CameraUpdate
                .scrollAndZoomTo(
                    LatLng(selected.lat, selected.lng),
                    SEARCH_ZOOM
                )
                .animate(CameraAnimation.Easing, MAP_CAMERA_ANIMATION_DURATION)
        )
    }

    LaunchedEffect(cameraPositionState.position) {
        val location = cameraPositionState.position.target
        planEditorViewModel.sendViewingPosition(location.latitude, location.longitude)
    }

    LaunchedEffect(placeSearchResult?.loadState?.refresh) {
        isKakaoSearching = when (val state = placeSearchResult?.loadState?.refresh) {
            is LoadState.Error -> {
                snackbarHostState.showSnackbar(state.error.message.toString())
                false
            }

            is LoadState.Loading -> {
                true
            }

            else -> {
                false
            }
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
            mapUiState.otherMapPositions.toList().mapIndexed { index, (userId, location) ->
                Marker(
                    state = MarkerState(position = location.toLatLng()),
                    captionText = userId,
                    icon = OverlayImage.fromResource(R.drawable.marker_user_position),
                    iconTintColor = markerColors[index]
                )
            }

            mapUiState.placeSearchResultAi.map { result ->
                SearchResultMarker(
                    result = result,
                    isSelected = result == mapUiState.selectedSearchResult,
                    onClick = {
                        planEditorViewModel.selectSearchResult(result)
                    },
                    markerSelectedColor = MaterialTheme.colorScheme.tertiary,
                    markerNotSelectedColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }

            optionsUiStatePerDate.aiAddressSearchResult.map { result ->
                SearchResultMarker(
                    result = result,
                    isSelected = result == mapUiState.selectedSearchResult,
                    onClick = {
                        planEditorViewModel.selectSearchResult(result)
                    },
                    markerSelectedColor = MaterialTheme.colorScheme.tertiary,
                    markerNotSelectedColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            }

            placeSearchResult?.itemSnapshotList?.map { result ->
                if (result == null) return@map

                SearchResultMarker(
                    result = result,
                    isSelected = result == mapUiState.selectedSearchResult,
                    onClick = {
                        planEditorViewModel.selectSearchResult(result)
                    }
                )
            }
        }

        SearchBar(
            modifier = Modifier
                .padding(statusBarPaddingValues),
            search = mapUiState.search,
            onSearchTextChanged = planEditorViewModel::updateSearchText,
            onSearch = {
                planEditorViewModel.search(mapUiState.search, cameraPositionState.position.target)
            },
            isSearching = isKakaoSearching || mapUiState.isAiSearching,
            onBack = {
                if (mapUiState.expanded) planEditorViewModel.shrinkSearchResult()
                else onBack()
            },
            selectedDate = mapUiState.selectedDate
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = mapPadding.calculateBottomPadding())
                .onGloballyPositioned {
                    with(localDensity) {
                        mapBottomPadding = it.size.height.toDp()
                    }
                },
        ) {
            AnimatedVisibility(
                visible = optionsUiStatePerDate.aiAddressSearchResult.isNotEmpty()
                        || mapUiState.placeSearchResultAi.isNotEmpty()
                        || placeSearchResult?.itemSnapshotList?.isNotEmpty() == true
            ) {
                MapSearchResultContainer(
                    placeSearchResults = placeSearchResult,
                    onAddButtonClick = {
                        planEditorViewModel.addItem(it)
                    },
                    onItemClick = { _, result ->
                        planEditorViewModel.selectSearchResult(result)
                    },
                    expanded = mapUiState.expanded,
                    onRequestExpandButtonClicked = {
                        planEditorViewModel.expandSearchResult()
                    },
                    addedPlaceSearchResult = mapUiState.addedSearchResults,
                    selectedSearchResult = mapUiState.selectedSearchResult,
                    lazyListState = lazyListState,
                    aiPlaceSearchResult = mapUiState.placeSearchResultAi,
                    isAiSearching = mapUiState.isAiSearching,
                    isMapSearching = isKakaoSearching,
                    aiAddressSearchResult = optionsUiStatePerDate.aiAddressSearchResult
                )
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
@NaverMapComposable
fun SearchResultMarker(
    result: PlaceSearchResult,
    isSelected: Boolean,
    markerSelectedColor: Color = MaterialTheme.colorScheme.primary,
    markerNotSelectedColor: Color = MaterialTheme.colorScheme.secondary,
    onClick: (PlaceSearchResult) -> Unit
) {
    Marker(
        state = MarkerState(position = LatLng(result.lat, result.lng)),
        captionText = result.name,
        onClick = {
            onClick(result)
            false
        },
        icon = MarkerIcons.BLACK,
        iconTintColor = if (isSelected) {
            markerSelectedColor
        } else {
            markerNotSelectedColor
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