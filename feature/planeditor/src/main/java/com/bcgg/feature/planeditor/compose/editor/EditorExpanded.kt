package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bcgg.core.domain.model.Destination

@Composable
fun EditorExpanded(
    destinations: List<Destination>,
    onDestinationChange: (oldDestination: Destination, newDestination: Destination) -> Unit,
    onDestinationRemove: (Destination) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(destinations.size) { position ->
            EditorDestinationItem(
                destination = destinations[position],
                showDivider = position != destinations.lastIndex,
                onChange = { newDestination ->
                    onDestinationChange(destinations[position], newDestination)
                },
                onRemove = onDestinationRemove
            )
        }
    }
}
