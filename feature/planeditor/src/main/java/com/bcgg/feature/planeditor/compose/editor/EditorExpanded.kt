package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bcgg.core.domain.model.Destination
import com.bcgg.feature.planeditor.util.calculateDestinationAvailableTime
import com.bcgg.feature.planeditor.util.positionToIndicatorType

@Composable
fun EditorExpanded(
    destinations: List<Destination>,
    onDestinationsChange: (oldDestination: Destination, newDestination: Destination) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(destinations.size) { position ->
            EditorDestinationItem(
                indicatorType = positionToIndicatorType(position, destinations.size),
                availableTime = calculateDestinationAvailableTime(
                    beforeDestination = if (position > 0) destinations[position - 1] else null,
                    afterDestination = if (position < destinations.lastIndex) destinations[position + 1] else null
                ),
                destination = destinations[position],
                showDivider = position != destinations.lastIndex,
                onChange = { newDestination ->
                    onDestinationsChange(destinations[position], newDestination)
                }
            )
        }
    }
}
