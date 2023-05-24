package com.bcgg.feature.planeditor.util

import androidx.compose.runtime.Composable
import com.bcgg.core.domain.model.Classification
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Food
import com.bcgg.core.ui.icon.icons.Home
import com.bcgg.core.ui.icon.icons.Rocketfly

@Composable
fun getClassificationIcon(classification: Classification) = when (classification) {
    Classification.Travel -> Icons.Rocketfly
    Classification.House -> Icons.Home
    Classification.Food -> Icons.Food
}
