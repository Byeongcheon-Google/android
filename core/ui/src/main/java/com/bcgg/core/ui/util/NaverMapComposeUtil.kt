package com.bcgg.core.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable fun rememberNaverMapFitBoundsPadding(defaultPadding: Dp = 24.dp) = with(LocalDensity.current) { defaultPadding.toPx() }.toInt()