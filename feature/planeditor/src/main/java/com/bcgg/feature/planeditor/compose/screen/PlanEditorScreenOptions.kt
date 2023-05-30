package com.bcgg.feature.planeditor.compose.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.bcgg.feature.planeditor.compose.editor.EditorContainer
import com.bcgg.feature.planeditor.compose.navigation.PlanEditorMapNavigation
import com.bcgg.feature.planeditor.compose.navigation.PlanEditorOptionsNavigation
import com.bcgg.feature.planeditor.compose.state.OptionsUiState
import com.bcgg.feature.planeditor.compose.state.PlanEditorOptionsUiStatePerDate
import com.bcgg.feature.planeditor.viewmodel.PlanEditorViewModel
import java.time.LocalDate

@Composable
fun PlanEditorScreenOptions(
    scaffoldPaddingValues: PaddingValues,
    navController: NavController,
    planEditorViewModel: PlanEditorViewModel = hiltViewModel(),
    optionsUiState: OptionsUiState,
    optionsUiStatePerDates: Map<LocalDate, PlanEditorOptionsUiStatePerDate>,
    onBack: () -> Unit
) {
    val statusBarPaddingValues = WindowInsets.statusBars.asPaddingValues()
    val localDensity = LocalDensity.current

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(scaffoldPaddingValues)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            EditorContainer(
                planName = optionsUiState.name,
                planEditorOptionsUiStatePerDates = optionsUiStatePerDates,
                selectedDate = optionsUiState.selectedDate,
                onDateClick = {
                    planEditorViewModel.changeSelectedDate(it)
                },
                onStayTimeChange = { placeSearchResultWithId, stayTime ->
                    planEditorViewModel.changeStayTime(placeSearchResultWithId, stayTime)
                },
                onPlaceSearchResultRemoved = {
                    planEditorViewModel.removeItem(it)
                },
                onPlanNameChange = {
                    planEditorViewModel.setName(it)
                },
                onStartTimeChange = {
                    planEditorViewModel.updateStartTime(optionsUiState.selectedDate, it)
                },
                onEndHopeTimeChange = {
                    planEditorViewModel.updateEndHopeTime(optionsUiState.selectedDate, it)
                },
                onMealTimeChange = {
                    planEditorViewModel.updateMealTimes(optionsUiState.selectedDate, it)
                },
                onSelectStartPosition = {
                    planEditorViewModel.updateStartPlace(optionsUiState.selectedDate, it)
                },
                onSelectEndPosition = {
                    planEditorViewModel.updateEndPlace(optionsUiState.selectedDate, it)
                },
                activeUserCount = optionsUiState.activeUserCount,
                onUserClick = {
                    planEditorViewModel.showInviteDialog()
                },
                onBack = onBack,
                onRecommendPlaceAddButtonClicked = {
                    planEditorViewModel.addItem(it)
                },
                onRecommendPlaceItemClicked = { _, it ->
                    navController.navigate(PlanEditorMapNavigation.id) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    planEditorViewModel.selectSearchResult(it)
                }
            )
        }

        Box(
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            AnimatedVisibility(visible = optionsUiStatePerDates.any { it.value.searchResultMaps.isNotEmpty() }) {
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