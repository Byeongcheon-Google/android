package com.bcgg.feature.planresult.compose

import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.constant.MapConstant.SEARCH_ZOOM
import com.bcgg.core.domain.constant.MapConstant.SEOUL_LAT
import com.bcgg.core.domain.constant.MapConstant.SEOUL_LNG
import com.bcgg.core.ui.component.BackButton
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.core.ui.util.rememberFusedLocationSource
import com.bcgg.core.ui.util.rememberNaverMapFitBoundsPadding
import com.bcgg.core.ui.util.statusBarPaddingValues
import com.bcgg.feature.planresult.R
import com.bcgg.feature.planresult.state.PlanResultItemUiState
import com.naver.maps.geometry.LatLng
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
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.MarkerIcons

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun PlanResultScreen(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useNaviType: Boolean = true,
    planName: String,
    planResultItemUiStates: List<PlanResultItemUiState>,
    selectedPlanResultItemUiState: PlanResultItemUiState?,
    onSelectedPlanResultItem: (PlanResultItemUiState) -> Unit
) {
    val localDensity = LocalDensity.current
    val context = LocalContext.current
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(SEOUL_LAT, SEOUL_LNG), SEARCH_ZOOM)
    }
    var selectedDate by rememberSaveable {
        mutableStateOf(planResultItemUiStates.firstOrNull()?.date)
    }
    var planResultListHeight by remember {
        mutableStateOf(0.dp)
    }
    val naverMapBoundsPadding = rememberNaverMapFitBoundsPadding()
    val naverMapBoundsPaddingRight = rememberNaverMapFitBoundsPadding(56.dp)

    EdgeToEdge()

    LaunchedEffect(selectedPlanResultItemUiState) {
        when (selectedPlanResultItemUiState) {
            is PlanResultItemUiState.Move -> cameraPositionState.animate(
                CameraUpdate.fitBounds(
                    selectedPlanResultItemUiState.bound,
                    naverMapBoundsPadding,
                    naverMapBoundsPadding,
                    naverMapBoundsPaddingRight,
                    naverMapBoundsPadding
                ),
                durationMs = 1000
            )

            is PlanResultItemUiState.Place -> cameraPositionState.animate(
                CameraUpdate.scrollAndZoomTo(selectedPlanResultItemUiState.point, 14.0),
                durationMs = 1000
            )

            null -> {}
        }
    }

    AppTheme {
        Scaffold { paddingValues ->
            Box {
                NaverMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f),
                    cameraPositionState = cameraPositionState,
                    contentPadding = PaddingValues(
                        top = statusBarPaddingValues.calculateTopPadding(),
                        bottom = planResultListHeight
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
                    planResultItemUiStates.map { item ->
                        val zIndex by animateIntAsState(
                            targetValue = if (selectedPlanResultItemUiState == item) {
                                100
                            } else {
                                0
                            }
                        )

                        if (item.date == selectedDate) {
                            when (item) {
                                is PlanResultItemUiState.Move -> {
                                    val lineColor by animateColorAsState(
                                        targetValue = if (selectedPlanResultItemUiState == item) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)
                                        }
                                    )
                                    val thickness by animateDpAsState(
                                        targetValue = if (selectedPlanResultItemUiState == item) {
                                            8.dp
                                        } else {
                                            5.dp
                                        }
                                    )
                                    PathOverlay(
                                        coords = item.points,
                                        outlineWidth = 0.dp,
                                        width = thickness,
                                        color = lineColor,
                                        onClick = {
                                            onSelectedPlanResultItem(item)
                                            false
                                        },
                                        zIndex = zIndex,
                                        patternImage = if (selectedPlanResultItemUiState == item) OverlayImage.fromResource(
                                            R.drawable.triangle
                                        ) else null,
                                        patternInterval = 10.dp
                                    )

                                    if (selectedPlanResultItemUiState != item) return@map

                                    Marker(
                                        state = MarkerState(position = item.startPlace),
                                        icon = OverlayImage.fromResource(R.drawable.marker_start),
                                        iconTintColor = lineColor,
                                        onClick = {
                                            onSelectedPlanResultItem(item)
                                            false
                                        },
                                        zIndex = zIndex,
                                        anchor = Offset(
                                            x = with(localDensity) { 18.dp.toPx() },
                                            y = with(localDensity) { 18.dp.toPx() }
                                        )
                                    )
                                    Marker(
                                        state = MarkerState(position = item.endPlace),
                                        icon = OverlayImage.fromResource(R.drawable.marker_end),
                                        iconTintColor = lineColor,
                                        onClick = {
                                            onSelectedPlanResultItem(item)
                                            false
                                        },
                                        zIndex = zIndex,
                                        anchor = Offset(
                                            x = with(localDensity) { 0.dp.toPx() },
                                            y = with(localDensity) { 48.dp.toPx() }
                                        )
                                    )
                                }

                                is PlanResultItemUiState.Place -> {
                                    val markerColor by animateColorAsState(
                                        targetValue = if (selectedPlanResultItemUiState == item) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            if (selectedPlanResultItemUiState is PlanResultItemUiState.Move)
                                                MaterialTheme.colorScheme.secondaryContainer
                                            else
                                                MaterialTheme.colorScheme.secondary
                                        }
                                    )
                                    Marker(
                                        state = MarkerState(position = item.point),
                                        captionText = item.name,
                                        icon = OverlayImage.fromResource(getMarkerIconId(item.number)),
                                        iconTintColor = item.circleColor,
                                        onClick = {
                                            onSelectedPlanResultItem(item)
                                            false
                                        },
                                        zIndex = zIndex,
                                        anchor = Offset(
                                            x = 18f,
                                            y = 18f
                                        ),
                                    )
                                }
                            }
                        }
                    }
                }

                if (selectedDate != null) {
                    PlanResultItems(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .onGloballyPositioned {
                                with(localDensity) {
                                    planResultListHeight = it.size.height.toDp()
                                }
                            }
                            .background(MaterialTheme.colorScheme.background)
                            .padding(bottom = paddingValues.calculateBottomPadding()),
                        planName = planName,
                        planResultItemUiStates = planResultItemUiStates,
                        onPlanResultItemSelect = onSelectedPlanResultItem,
                        selectedDate = selectedDate!!,
                        selectedItem = selectedPlanResultItemUiState,
                        onSelectedDateChange = {
                            selectedDate = it
                        }
                    )
                }

                BackButton(modifier = Modifier.padding(top = paddingValues.calculateTopPadding())) {

                }
            }
        }
    }
}

internal fun getMarkerIconId(number: Int) = when (number) {
    1 -> R.drawable.marker_1
    2 -> R.drawable.marker_2
    3 -> R.drawable.marker_3
    4 -> R.drawable.marker_4
    5 -> R.drawable.marker_5
    6 -> R.drawable.marker_6
    7 -> R.drawable.marker_7
    8 -> R.drawable.marker_8
    9 -> R.drawable.marker_9
    else -> R.drawable.marker_10_plus
}