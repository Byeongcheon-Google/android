package com.bcgg.feature.planmanage.viewmodel

import androidx.lifecycle.viewModelScope
import com.bcgg.core.domain.repository.ScheduleRepository
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.collectLatestWithLoading
import com.bcgg.core.util.ext.removed
import com.bcgg.core.util.ext.schedulesDateTimeFormatter
import com.bcgg.core.util.onFailure
import com.bcgg.core.util.onSuccess
import com.bcgg.feature.planmanage.state.PlanItemUiState
import com.bcgg.feature.planmanage.state.PlanManageScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDateTime
import javax.inject.Inject

@HiltViewModel
class PlanManageViewModel @Inject constructor(
    private val scheduleRepository: ScheduleRepository
) : BaseViewModel() {
    private val _uiState = MutableStateFlow(PlanManageScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _planItemRemovedEvent = MutableSharedFlow<Pair<Int, PlanItemUiState>>()
    val planItemRemovedEvent = _planItemRemovedEvent.asSharedFlow()

    private val _planItemClickedEvent = MutableSharedFlow<Int>()
    val planItemClickedEvent = _planItemClickedEvent.asSharedFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    init {
        getSchedules()
    }

    fun onAddButtonClick() {
        viewModelScope.launch {
            scheduleRepository.newSchedule()
                .collectLatestWithLoading(_isLoading) {
                    onSuccess {
                        _planItemClickedEvent.emit(it)
                    }
                    onFailure {
                        _errorMessage.emit(it)
                    }
                }
        }
    }

    fun getSchedules() = viewModelScope.launch {
        scheduleRepository.getSchedules().collectLatestWithLoading(_isLoading) {
            onSuccess {
                _uiState.value = _uiState.value.copy(
                    it.map {
                        PlanItemUiState(
                            id = it.id,
                            title = it.title,
                            modifiedDateTimeFormatted = schedulesDateTimeFormatter.format(it.modifiedDateTime.toJavaLocalDateTime()),
                            dateCount = it.dateCount,
                            destinations = it.destinations
                        )
                    }
                )
            }
            onFailure {
                _errorMessage.emit(it)
            }
        }
    }

    fun onPlanItemClick(planItemUiState: PlanItemUiState) {
        viewModelScope.launchWithLoading {
            _planItemClickedEvent.emit(planItemUiState.id)
        }
    }

    fun onPlanItemRemove(position: Int, planItemUiState: PlanItemUiState) {
        viewModelScope.launch {
            //remove from repository

            _uiState.value = _uiState.value.copy(
                plans = _uiState.value.plans.removed(planItemUiState)
            )
        }

    }

    fun onPlanItemRemoveUndo(position: Int, planItemUiState: PlanItemUiState) {
        viewModelScope.launch {
            //remove from repository

            val newList = _uiState.value.plans.toMutableList()
            newList.add(position, planItemUiState)
            _uiState.value = _uiState.value.copy(
                plans = newList
            )
        }
    }
}