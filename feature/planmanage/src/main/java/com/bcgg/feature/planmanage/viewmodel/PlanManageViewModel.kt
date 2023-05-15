package com.bcgg.feature.planmanage.viewmodel

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bcgg.core.ui.util.stateflow.stateFlowOf
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.removed
import com.bcgg.feature.planmanage.state.PlanItemUiState
import com.bcgg.feature.planmanage.state.PlanManageScreenUiState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlanManageViewModel : BaseViewModel() {
    private val _uiState = MutableStateFlow(PlanManageScreenUiState())
    val uiState = _uiState.asStateFlow()

    private val _planItemRemovedEvent = MutableSharedFlow<Pair<Int, PlanItemUiState>>()
    val planItemRemovedEvent = _planItemRemovedEvent.asSharedFlow()

    private val _planItemClickedEvent = MutableSharedFlow<Pair<Int, PlanItemUiState>>()
    val planItemClickedEvent = _planItemClickedEvent.asSharedFlow()

    private val _addButtonClickedEvent = MutableSharedFlow<Unit>()
    val addButtonClickedEvent = _addButtonClickedEvent.asSharedFlow()

    fun onAddButtonClick() {
        viewModelScope.launch {
            _addButtonClickedEvent.emit(Unit)
        }
    }

    fun onPlanItemClick(position: Int, planItemUiState: PlanItemUiState) {
        viewModelScope.launchWithLoading {
            _planItemClickedEvent.emit(position to planItemUiState)
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