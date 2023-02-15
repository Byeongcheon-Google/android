package com.bcgg.feature.planeditor.util

import androidx.compose.runtime.Composable
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Food
import com.bcgg.core.ui.icon.icons.Home
import com.bcgg.core.ui.icon.icons.Rocketfly

@Composable
fun getDestinationTypeIcon(destination: Destination) = when (destination.type) {
    Destination.Type.Travel -> Icons.Rocketfly
    Destination.Type.House -> Icons.Home
    Destination.Type.Food -> Icons.Food
}
