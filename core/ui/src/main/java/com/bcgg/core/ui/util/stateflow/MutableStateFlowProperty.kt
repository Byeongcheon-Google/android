package com.bcgg.core.ui.util.stateflow

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.properties.ReadOnlyProperty

fun <T> stateFlowOf(value: MutableStateFlow<T>): ReadOnlyProperty<ViewModel, StateFlow<T>> =
    ReadOnlyProperty<ViewModel, StateFlow<T>> { _, _ -> value }
