package com.bcgg.feature.planeditor.compose.map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.ui.theme.AppTheme

@Composable
fun MapSearchResultItem(
    mapSearchResult: MapSearchResult,
    isAdded: Boolean,
    onAddButtonClick: (MapSearchResult) -> Unit,
    onRemoveButtonClick: (MapSearchResult) -> Unit,
    onItemClick: (MapSearchResult) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onItemClick(mapSearchResult)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = mapSearchResult.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = mapSearchResult.distance,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.outline
            )
        }
        Text(
            text = mapSearchResult.address,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.padding(top = 6.dp)
        ) {
            if (isAdded) {
                OutlinedButton(
                    modifier = Modifier.heightIn(min = 32.dp),
                    onClick = { onRemoveButtonClick(mapSearchResult) },
                ) {
                    Text(text = "삭제", style = MaterialTheme.typography.bodySmall)
                }
            } else {
                FilledTonalButton(
                    modifier = Modifier.heightIn(min = 32.dp),
                    onClick = { onAddButtonClick(mapSearchResult) },
                ) {
                    Text(text = "추가", style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}

@Preview
@Composable
private fun MapSearchResultItemPreview() {
    Column {
        listOf(false to false, false to true, true to false, true to true).map {
            AppTheme(
                useDarkTheme = it.first
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    MapSearchResultItem(
                        mapSearchResult = MapSearchResult(
                            name = "성심당 DCC점",
                            distance = "50km",
                            address = "대전 유성구 엑스포로 107",
                            lat = 1.0,
                            lng = 1.0
                        ),
                        isAdded = it.second,
                        onAddButtonClick = {},
                        onRemoveButtonClick = {}
                    ) {
                    }
                }
            }
        }
    }
}
