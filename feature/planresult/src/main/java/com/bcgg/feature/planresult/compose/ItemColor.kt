package com.bcgg.feature.planresult.compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.bcgg.core.domain.model.Classification
import com.bcgg.feature.planresult.state.PlanResultItemUiState

val PlanResultItemUiState.circleColor: Color @Composable get() =
    when(this) {
        is PlanResultItemUiState.Move -> MaterialTheme.colorScheme.secondary
        is PlanResultItemUiState.Place -> when(this.classification) {
            Classification.Travel -> MaterialTheme.colorScheme.primary
            Classification.House -> MaterialTheme.colorScheme.tertiary
            Classification.Food -> MaterialTheme.colorScheme.error
        }
    }