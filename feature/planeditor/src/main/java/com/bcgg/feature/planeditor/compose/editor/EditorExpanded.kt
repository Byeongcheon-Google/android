package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import com.bcgg.feature.planeditor.compose.state.PlanEditorOptionsUiStatePerDate

fun LazyListScope.editorExpanded(
    planEditorOptionsUiStatePerDate: PlanEditorOptionsUiStatePerDate,
    onStayTimeChange: (PlaceSearchResultWithId, Int) -> Unit,
    onPlaceSearchResultRemoved: (PlaceSearchResultWithId) -> Unit,
    onSelectStartPosition: (PlaceSearchResultWithId) -> Unit,
    onSelectEndPosition: (PlaceSearchResultWithId) -> Unit,
) {
    val items = planEditorOptionsUiStatePerDate.searchResultMaps
    items(items.size) { position ->
        EditorDestinationItem(
            placeSearchResultWithId = items[position],
            stayTimeHour = items[position].stayTimeHour,
            classification = items[position].placeSearchResult.classification,
            showDivider = items.lastIndex != position,
            onStayTimeChange = {
                onStayTimeChange(items[position], it)
            },
            onRemove = onPlaceSearchResultRemoved,
            isStartPosition = items[position].placeSearchResult == planEditorOptionsUiStatePerDate.startPlaceSearchResult?.placeSearchResult,
            isEndPosition = items[position].placeSearchResult == planEditorOptionsUiStatePerDate.endPlaceSearchResult?.placeSearchResult,
            onSelectStartPosition = onSelectStartPosition,
            onSelectEndPosition = onSelectEndPosition
        )
    }

    if (items.isEmpty()) {
        item {
            Text(
                text = "지도 화면에서 여행지를 추가해 주세요",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
