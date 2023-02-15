package com.bcgg.feature.planeditor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.domain.repository.MapPlaceRepository
import com.bcgg.core.util.ext.removed
import com.bcgg.core.util.localdatetime.floorTenMinutes
import com.bcgg.feature.planeditor.compose.screen.UiState
import com.bcgg.feature.planeditor.compose.screen.find
import com.bcgg.feature.planeditor.constant.Constant
import com.bcgg.feature.planeditor.constant.Constant.SEARCH_DEBOUNCE
import com.bcgg.feature.planeditor.util.calculateDestinationAvailableTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class PlanEditorViewModel @Inject constructor(
    private val mapPlaceRepository: MapPlaceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    val queryChannel = MutableStateFlow(uiState.value.search)

    init {
        viewModelScope.launch {
            queryChannel.collectLatest {
                _uiState.value =
                    uiState.value.copy(search = it, expanded = UiState.Expanded.SearchResult)
            }
        }

        viewModelScope.launch {
            queryChannel
                .debounce(SEARCH_DEBOUNCE)
                .collectLatest {
                    search(it)
                }
        }
    }

    suspend fun search(query: String) {
        _uiState.value = uiState.value.copy(isSearching = true)
        _uiState.value = uiState.value.copy(mapSearchResult = mapPlaceRepository.getPlace(query))
        _uiState.value = uiState.value.copy(isSearching = false)
    }

    fun requestExpandSearchContainer() {
        _uiState.value = uiState.value.copy(expanded = UiState.Expanded.SearchResult)
    }

    fun requestExpandEditorContainer() {
        _uiState.value = uiState.value.copy(expanded = UiState.Expanded.ScheduleEdit)
    }

    fun changeSelectedDate(selectedDate: LocalDate) {
        _uiState.value = uiState.value.copy(selectedDate = selectedDate)
    }

    fun addItem(mapSearchResult: MapSearchResult) {
        val destinations =
            uiState.value.schedule.destinations.filter {
                it.comeTime.toLocalDate() == uiState.value.selectedDate
            }

        val availableStartTime =
            calculateDestinationAvailableTime(destinations.lastOrNull(), null)?.start

        if (availableStartTime == null) {
            switchSnackbarState(UiState.SnackbarState.NotAvailableTime)
            return
        }

        val nowTime = LocalTime.of(LocalTime.now().hour, LocalTime.now().floorTenMinutes)

        val startTime = if (destinations.isEmpty()) nowTime else availableStartTime

        val destination = Destination(
            name = mapSearchResult.name,
            address = mapSearchResult.address,
            katechMapX = mapSearchResult.katechMapX,
            katechMapY = mapSearchResult.katechMapY,
            stayTimeHour = Constant.MIN_STAY_TIME,
            comeTime = LocalDateTime.of(uiState.value.selectedDate, startTime),
            type = Destination.Type.Travel
        )

        _uiState.value = uiState.value.copy(
            schedule = uiState.value.schedule.copy(
                destinations = uiState.value.schedule.destinations + destination
            )
        )
    }

    fun removeItem(mapSearchResult: MapSearchResult) {
        val destinations =
            uiState.value.schedule.destinations.filter {
                it.comeTime.toLocalDate() == uiState.value.selectedDate
            }

        val removeDestination = destinations.find(mapSearchResult)

        if (removeDestination != null) {
            _uiState.value = uiState.value.copy(
                schedule = uiState.value.schedule.copy(
                    destinations = uiState.value.schedule.destinations.removed(removeDestination)
                )
            )
        }
    }

    fun updateDestination(oldDestination: Destination, newDestination: Destination) {
        _uiState.value = uiState.value.copy(
            schedule = uiState.value.schedule.copy(
                destinations = uiState.value.schedule.destinations.map {
                    if (it == oldDestination) newDestination else it
                }
            )
        )
    }

    fun switchSnackbarState(snackbarState: UiState.SnackbarState?) {
        _uiState.value = uiState.value.copy(snackbarState = snackbarState)
    }
}
