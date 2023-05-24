package com.bcgg.core.ui.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

operator fun Dp.times(d: Double): Dp = (this.value * d).dp