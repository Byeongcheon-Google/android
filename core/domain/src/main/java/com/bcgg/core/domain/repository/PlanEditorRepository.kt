package com.bcgg.core.domain.repository

import com.bcgg.core.domain.model.editor.PathFinderInput
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class PlanEditorRepository {
    var currentPlanEditor = PathFinderInput()

    private val _planEditor = MutableSharedFlow<PathFinderInput>()
    val planEditor = _planEditor.asSharedFlow()


}