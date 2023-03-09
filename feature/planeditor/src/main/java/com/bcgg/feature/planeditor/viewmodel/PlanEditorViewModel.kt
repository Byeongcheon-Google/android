package com.bcgg.feature.planeditor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.domain.repository.MapPlaceRepository
import com.bcgg.core.util.ext.removed
import com.bcgg.feature.planeditor.compose.screen.UiState
import com.bcgg.feature.planeditor.compose.screen.find
import com.bcgg.feature.planeditor.constant.Constant
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class PlanEditorViewModel @Inject constructor(
    private val mapPlaceRepository: MapPlaceRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    var mapLat: Double = Double.NaN
    var mapLng: Double = Double.NaN

    fun updateSearchText(text: String) {
        _uiState.value = uiState.value.copy(search = text, searchButtonEnabled = text.isNotBlank())
    }

    fun search(query: String, latLng: LatLng) = viewModelScope.launch {
        _uiState.value = uiState.value.copy(isSearching = true)
        val mapSearchResult = mapPlaceRepository.getPlace(query, latLng.longitude, latLng.latitude)
        _uiState.value = uiState.value.copy(
            mapSearchResult = mapSearchResult,
            isSearching = false,
            expanded = UiState.Expanded.SearchResult
        )
    }

    fun requestExpandSearchContainer() {
        _uiState.value = uiState.value.copy(expanded = UiState.Expanded.SearchResult)
    }

    fun requestExpandEditorContainer() {
        _uiState.value = uiState.value.copy(expanded = UiState.Expanded.ScheduleEdit)
    }

    fun requestShrinkContainer() {
        _uiState.value = uiState.value.copy(expanded = null)
    }

    fun changeSelectedDate(selectedDate: LocalDate) {
        _uiState.value = uiState.value.copy(selectedDate = selectedDate)
    }

    fun addItem(mapSearchResult: MapSearchResult) {
        val destination = Destination(
            name = mapSearchResult.name,
            address = mapSearchResult.address,
            lat = mapSearchResult.lat,
            lng = mapSearchResult.lng,
            stayTimeHour = Constant.MIN_STAY_TIME,
            type = Destination.Type.Travel,
            date = uiState.value.selectedDate
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
                it.date == uiState.value.selectedDate
            }

        val removeDestination = destinations.find(mapSearchResult)

        if (removeDestination != null) {
            removeItem(removeDestination)
        }
    }

    fun removeItem(destination: Destination) {
        _uiState.value = uiState.value.copy(
            schedule = uiState.value.schedule.copy(
                destinations = uiState.value.schedule.destinations.removed(destination)
            )
        )
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

    fun selectSearchResult(position: Int) {
        _uiState.value = uiState.value.copy(selectedSearchPosition = position)
    }

    fun setMapLatLng(lat: Double, lng: Double) {
        this.mapLat = lat
        this.mapLng = lng
    }
}
