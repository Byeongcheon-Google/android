package com.bcgg.feature.planresult.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.constant.MapConstant.DEFAULT_ZOOM
import com.bcgg.core.domain.constant.MapConstant.SEARCH_ZOOM
import com.bcgg.core.domain.constant.MapConstant.SEOUL_LAT
import com.bcgg.core.domain.constant.MapConstant.SEOUL_LNG
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.rememberFusedLocationSource
import com.bcgg.feature.planresult.state.PlanResultItem
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.CircleOverlay
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
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class, ExperimentalNaverMapApi::class)
@Composable
fun PlanResultScreen(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    useNaviType: Boolean = true,
    planName: String,
    planResultItems: List<PlanResultItem>,
    selectedPlanResultItem: PlanResultItem?,
    onSelectedPlanResultItem: (PlanResultItem) -> Unit
) {
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(LatLng(SEOUL_LAT, SEOUL_LNG), SEARCH_ZOOM)
    }
    var selectedDate by rememberSaveable {
        mutableStateOf(planResultItems.firstOrNull()?.date)
    }
    val statusBarPaddingValues = WindowInsets.systemBars.asPaddingValues()

    LaunchedEffect(selectedPlanResultItem) {

    }

    AppTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(planName)
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                NaverMap(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.4f),
                    cameraPositionState = cameraPositionState,
                    contentPadding = PaddingValues(
                        top = statusBarPaddingValues.calculateTopPadding()
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
                    planResultItems.map { item ->
                        if (item.date == selectedDate) {
                            when (item) {
                                is PlanResultItem.Move -> {
                                    PathOverlay(
                                        coords = item.points,
                                        outlineWidth = 0.dp,
                                        color = if (selectedPlanResultItem == item) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.secondary
                                        }
                                    )
                                }
                                is PlanResultItem.Place -> {
                                    Marker(
                                        state = MarkerState(position = item.point),
                                        captionText = item.name,
                                        icon = MarkerIcons.BLACK,
                                        iconTintColor = if (selectedPlanResultItem == item) {
                                            MaterialTheme.colorScheme.primary
                                        } else {
                                            MaterialTheme.colorScheme.secondary
                                        }
                                    )
                                }
                            }
                        }
                    }
                }

                if (selectedDate != null) {
                    PlanResultItems(
                        planResultItems = planResultItems,
                        onPlanResultItemSelect = onSelectedPlanResultItem,
                        selectedDate = selectedDate!!,
                        onSelectedDateChange = {
                            selectedDate = it
                        }
                    )
                }

            }
        }
    }
}