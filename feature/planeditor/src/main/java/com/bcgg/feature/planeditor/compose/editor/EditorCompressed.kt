package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowright
import com.bcgg.core.ui.preview.PreviewContainer
import java.time.LocalDateTime

@Composable
fun EditorCompressed(
    destinations: List<Destination>
) {
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        if (destinations.isEmpty()) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "가고 싶은 여행지를 선택하세요.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        } else {
            LazyRow(
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(destinations.size) {
                    Box(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                shape = MaterialTheme.shapes.large,
                                color = MaterialTheme.colorScheme.primaryContainer
                            )
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = destinations[it].name,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    if (it != destinations.lastIndex) {
                        Icon(
                            modifier = Modifier.padding(start = 8.dp),
                            imageVector = Icons.Arrowright,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primaryContainer
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun EditorCompressedPreview() {
    val sample = listOf(
        Destination(
            name = "한기대",
            address = "한기대",
            katechMapX = "",
            katechMapY = "",
            stayTimeHour = 2,
            comeTime = LocalDateTime.now(),
            type = Destination.Type.Travel
        ),
        Destination(
            name = "터미널",
            address = "한기대",
            katechMapX = "",
            katechMapY = "",
            stayTimeHour = 2,
            comeTime = LocalDateTime.now(),
            type = Destination.Type.Travel
        ),
        Destination(
            name = "천안역",
            address = "한기대",
            katechMapX = "",
            katechMapY = "",
            stayTimeHour = 2,
            comeTime = LocalDateTime.now(),
            type = Destination.Type.Travel
        )
    )
    PreviewContainer {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            EditorCompressed(destinations = sample)
            EditorCompressed(destinations = listOf())
        }
    }
}
