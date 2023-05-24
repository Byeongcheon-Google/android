package com.bcgg.feature.planeditor.compose.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bcgg.core.ui.component.ProgressDialog
import com.bcgg.core.ui.constant.UiConstant
import com.bcgg.feature.planeditor.compose.editor.EditorContainer
import com.bcgg.feature.planeditor.viewmodel.PlanEditorViewModel

@Composable
fun PlanEditorScreenOptions(
    scaffoldPaddingValues: PaddingValues,
    planEditorViewModel: PlanEditorViewModel = hiltViewModel()
) {
    val navigationBarPaddingValues = WindowInsets.navigationBars.asPaddingValues()
    val statusBarPaddingValues = WindowInsets.statusBars.asPaddingValues()
    val localDensity = LocalDensity.current

    val uiState by planEditorViewModel.optionsUiState.collectAsState()
    val uiStatePerDate by planEditorViewModel.optionsUiStatePerDate.collectAsState()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(scaffoldPaddingValues)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(navigationBarPaddingValues)
        ) {
            EditorContainer(
                planName = uiState.name,
                planEditorOptionsUiStatePerDates = uiStatePerDate,
                selectedDate = uiState.selectedDate,
                onDateClick = {
                    planEditorViewModel.changeSelectedDate(it)
                },
                onStayTimeChange = { placeSearchResultWithId, stayTime ->
                    planEditorViewModel.changeStayTime(placeSearchResultWithId, stayTime)
                },
                onClassificationChange = { placeSearchResultWithId, classification ->
                    planEditorViewModel.changeClassification(
                        placeSearchResultWithId,
                        classification
                    )
                },
                onPlaceSearchResultRemoved = {
                    planEditorViewModel.removeItem(it)
                },
                onPlanNameChange = {
                    planEditorViewModel.setName(it)
                },
                onStartTimeChange = {
                    planEditorViewModel.updateStartTime(uiState.selectedDate, it)
                },
                onEndHopeTimeChange = {
                    planEditorViewModel.updateEndHopeTime(uiState.selectedDate, it)
                },
                onMealTimeChange = {
                    planEditorViewModel.updateMealTimes(uiState.selectedDate, it)
                },
                onSelectStartPosition = {
                    planEditorViewModel.updateStartPlace(uiState.selectedDate, it)
                },
                onSelectEndPosition = {
                    planEditorViewModel.updateEndPlace(uiState.selectedDate, it)
                }
            )
        }

        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            AnimatedVisibility(visible = uiStatePerDate.any { it.value.searchResultMaps.isNotEmpty() }) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),
                    onClick = {
                        planEditorViewModel.showConfirmDialog()
                    }
                ) {
                    Icon(imageVector = Icons.Rounded.Done, contentDescription = "여행 계획표 생성")
                }
            }
        }
    }
}