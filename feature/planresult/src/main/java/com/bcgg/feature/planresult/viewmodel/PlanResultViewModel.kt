package com.bcgg.feature.planresult.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.repository.PathFinderRepository
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.PathFinderResult
import com.bcgg.core.util.ext.collectLatestWithLoading
import com.bcgg.core.util.onFailure
import com.bcgg.core.util.onSuccess
import com.bcgg.feature.planresult.state.PlanResultItemUiState
import com.bcgg.feature.planresult.state.PlanResultScreenUiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import java.time.LocalDate

class PlanResultViewModel @AssistedInject constructor(
    private val pathFinderRepository: PathFinderRepository,
    @Assisted private val scheduleId: Int
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(scheduleId: Int): PlanResultViewModel
    }

    private val _planResultScreenUiState = MutableStateFlow(PlanResultScreenUiState())
    val planResultScreenUiState = _planResultScreenUiState.asStateFlow()

    private val planResultItemUiStateMap: MutableMap<LocalDate, List<PlanResultItemUiState>> =
        mutableMapOf()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    fun getPathFindResult() = viewModelScope.launch {
        pathFinderRepository.findPath(scheduleId)
            .collectLatestWithLoading(_isLoading) {
                onSuccess {
                    val map = it.pathFinderResults.map { pathFinderResult ->
                        var count = 1

                        pathFinderResult.date.toJavaLocalDate() to pathFinderResult.result.map { pathFinderItem ->
                            when (pathFinderItem) {
                                is PathFinderResult.PathFinderItem.Move -> PlanResultItemUiState.Move(
                                    distanceDescription = "${pathFinderItem.distance}${pathFinderItem.distanceUnit} 이동",
                                    date = pathFinderResult.date.toJavaLocalDate(),
                                    timeRange = pathFinderItem.startTime.toJavaLocalTime()..pathFinderItem.startTime.toJavaLocalTime()
                                        .plusMinutes(pathFinderItem.durationMinute),
                                    points = pathFinderItem.points.map {
                                        LatLng(it.lat, it.lng)
                                    },
                                    bound = LatLngBounds(
                                        LatLng(
                                            pathFinderItem.boundSouthWest.lat,
                                            pathFinderItem.boundSouthWest.lng
                                        ),
                                        LatLng(
                                            pathFinderItem.boundNorthEast.lat,
                                            pathFinderItem.boundNorthEast.lng
                                        )
                                    )
                                )

                                is PathFinderResult.PathFinderItem.Place -> PlanResultItemUiState.Place(
                                    number = count++,
                                    name = pathFinderItem.name,
                                    date = pathFinderResult.date.toJavaLocalDate(),
                                    timeRange = pathFinderItem.startTime.toJavaLocalTime()..pathFinderItem.startTime.toJavaLocalTime()
                                        .plusMinutes(pathFinderItem.stayTimeMinute),
                                    point = pathFinderItem.position.let {
                                        LatLng(it.lat, it.lng)
                                    },
                                    classification = pathFinderItem.classification
                                )
                            }
                        }
                    }.toMap()

                    planResultItemUiStateMap.putAll(map)

                    val keys = planResultItemUiStateMap.keys.toList().sorted()

                    if (keys.isNotEmpty()) {
                        _planResultScreenUiState.value = _planResultScreenUiState.value.copy(
                            planName = it.name,
                            dates = keys,
                            planResultItemUiState = planResultItemUiStateMap[keys.first()]!!,
                        )
                        changeDate(keys.first())
                    }

                }
                onFailure {
                    _errorMessage.emit(it)
                }
            }
    }

    fun changeDate(localDate: LocalDate) {
        if(_isLoading.value) return
        _isLoading.value = true
        viewModelScope.launch {
            if (!planResultItemUiStateMap.containsKey(localDate)) return@launch

            var minLat = 90.0
            var maxLat = -90.0
            var minLng = 180.0
            var maxLng = -180.0

            val items = planResultItemUiStateMap[localDate]!!

            items.forEach {
                when(it) {
                    is PlanResultItemUiState.Move -> {
                        it.points.forEach {
                            if (it.latitude < minLat) minLat = it.latitude
                            if (it.latitude > maxLat) maxLat = it.latitude
                            if (it.longitude < minLng) minLng = it.longitude
                            if (it.longitude > maxLng) maxLng = it.longitude
                        }
                    }
                    is PlanResultItemUiState.Place -> {
                        if (it.point.latitude < minLat) minLat = it.point.latitude
                        if (it.point.latitude > maxLat) maxLat = it.point.latitude
                        if (it.point.longitude < minLng) minLng = it.point.longitude
                        if (it.point.longitude > maxLng) maxLng = it.point.longitude
                    }
                }
            }

            _planResultScreenUiState.value = _planResultScreenUiState.value.copy(
                selectedDate = localDate,
                planResultItemUiState = items,
                selectedPlanResultItemUiState = null,
                entireBounds = LatLngBounds(
                    LatLng(minLat, minLng),
                    LatLng(maxLat, maxLng)
                )
            )

            _isLoading.value = false
        }
    }

    fun selectItem(planResultItemUiState: PlanResultItemUiState?) {
        _planResultScreenUiState.value = _planResultScreenUiState.value.copy(
            selectedPlanResultItemUiState = planResultItemUiState
        )
    }

    companion object {
        fun provideFactory(
            assistedFactory: PlanResultViewModel.Factory,
            scheduleId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(scheduleId) as T
            }
        }
    }
}