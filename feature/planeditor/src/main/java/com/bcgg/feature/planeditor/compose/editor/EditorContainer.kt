package com.bcgg.feature.planeditor.compose.editor

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.util.Classification
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.Schedule
import com.bcgg.core.util.PlaceSearchResult
import com.bcgg.core.util.PlaceSearchResultWithId
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.feature.planeditor.compose.mealtime.mealTimes
import com.bcgg.feature.planeditor.compose.picker.TimePicker
import com.bcgg.feature.planeditor.compose.state.PlanEditorOptionsUiStatePerDate
import com.bcgg.feature.planeditor.compose.state.initialPlanEditorOptionsUiStatePerDate
import com.bcgg.feature.planeditor.compose.wss.ActiveUsers
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorContainer(
    modifier: Modifier = Modifier,
    planName: String,
    activeUserCount: Int,
    planEditorOptionsUiStatePerDates: Map<LocalDate, PlanEditorOptionsUiStatePerDate>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    onStayTimeChange: (PlaceSearchResultWithId, Int) -> Unit,
    onPlaceSearchResultRemoved: (PlaceSearchResultWithId) -> Unit,
    onPlanNameChange: (String) -> Unit,
    onStartTimeChange: (LocalTime) -> Unit,
    onEndHopeTimeChange: (LocalTime) -> Unit,
    onMealTimeChange: (List<LocalTime>) -> Unit,
    onSelectStartPosition: (PlaceSearchResultWithId) -> Unit,
    onSelectEndPosition: (PlaceSearchResultWithId) -> Unit,
    onUserClick: () -> Unit,
    onBack: () -> Unit,
    onRecommendPlaceItemClicked: (Int, PlaceSearchResult) -> Unit,
    onRecommendPlaceAddButtonClicked: (PlaceSearchResult) -> Unit
) {
    val enabledDates = planEditorOptionsUiStatePerDates.keys
    val planEditorOptionsUiStatePerDate =
        planEditorOptionsUiStatePerDates[selectedDate] ?: initialPlanEditorOptionsUiStatePerDate

    val lazyListState = rememberLazyListState()

    val navigationBarPaddingValues = WindowInsets.navigationBars.asPaddingValues()

    val addedPlaceSearchResult by remember(
        planEditorOptionsUiStatePerDate.searchResultMaps,
        planEditorOptionsUiStatePerDate.aiAddressSearchResult
    ) {
        derivedStateOf {
            planEditorOptionsUiStatePerDate.aiAddressSearchResult.filter { aiSearchResult ->
                planEditorOptionsUiStatePerDate.searchResultMaps.find { it.placeSearchResult == aiSearchResult } != null
            }
        }
    }

    BackHandler(onBack = onBack)

    LaunchedEffect(selectedDate) {
        lazyListState.animateScrollBy(0f)
    }

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        TopAppBar(
            title = {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = planName,
                    onValueChange = {
                        onPlanNameChange(it)
                    },
                    singleLine = true,
                    textStyle = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.onSurface),
                )
            },
            navigationIcon = {
                IconButton(
                    enabled = true,
                    onClick = onBack
                ) {
                    Icon(
                        imageVector = Icons.Arrowleft,
                        contentDescription = ""
                    )
                }
            },
            actions = {
                ActiveUsers(
                    modifier = Modifier
                        .clickable(onClick = onUserClick)
                        .padding(8.dp),
                    activeUserCount = activeUserCount
                )
            }
        )

        EditorDate(
            selectedDate = selectedDate,
            dates = enabledDates,
            expanded = true,
            onDateClick = onDateClick
        )

        LazyColumn(
            state = lazyListState
        ) {
            item {
                TimePicker(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    label = "시작 시간",
                    value = planEditorOptionsUiStatePerDate.startTime,
                    onValueChange = onStartTimeChange
                )

                TimePicker(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth(),
                    label = "종료 예정 시간",
                    value = planEditorOptionsUiStatePerDate.endHopeTime,
                    onValueChange = onEndHopeTimeChange
                )
            }

            mealTimes(
                mealTimes = planEditorOptionsUiStatePerDate.mealTimes,
                onChange = onMealTimeChange
            )

            editorExpanded(
                planEditorOptionsUiStatePerDate = planEditorOptionsUiStatePerDate,
                onStayTimeChange = onStayTimeChange,
                onPlaceSearchResultRemoved = onPlaceSearchResultRemoved,
                onSelectStartPosition = onSelectStartPosition,
                onSelectEndPosition = onSelectEndPosition
            )

            editorAiRecommendByAddress(
                isSearching = planEditorOptionsUiStatePerDate.isAiSearching,
                placeSearchResult = planEditorOptionsUiStatePerDate.aiAddressSearchResult,
                addedPlaceSearchResult = addedPlaceSearchResult,
                onAddButtonClick = onRecommendPlaceAddButtonClicked,
                onItemClick = onRecommendPlaceItemClicked,
                selectedSearchResult = null
            )

            item {
                Spacer(
                    modifier = Modifier
                        .height(60.dp)
                        .padding(navigationBarPaddingValues)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.PIXEL_4)
@Composable
private fun EditorContainerPreview() {

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(LocalDate.now())
    }

    var sample = Schedule(
        name = "SAMPLE",
        destinations = listOf(
            Destination(
                name = "한기대",
                address = "충남 천안시 동남구 충절로 1600",
                stayTimeHour = 2,
                date = LocalDate.now(),
                classification = Classification.Travel
            ),
            Destination(
                name = "터미널",
                address = "몰루",
                stayTimeHour = 2,
                date = LocalDate.now(),
                classification = Classification.Travel
            ),
            Destination(
                name = "천안역",
                address = "몰루",
                stayTimeHour = 2,
                date = LocalDate.now(),
                classification = Classification.Travel
            )
        ),
        status = Schedule.Status.BeforeRun
    )

    AppTheme(
        useDarkTheme = false
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = {
                /*EditorContainer(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    planName = "test",
                    selectedDate = selectedDate,
                    onDateClick = {
                        selectedDate = it
                    },
                    onDestinationChanged = { _, _ -> },
                    onDestinationRemoved = {},
                    planEditorOptionsUiStatePerDates = mapOf()
                )*/
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                Button(onClick = { expanded = false }) {
                    Text(text = "Collapse")
                }
            }
        }
    }
}
