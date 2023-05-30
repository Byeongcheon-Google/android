package com.bcgg.feature.planeditor.compose.editor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.bcgg.core.util.PlaceSearchResult
import com.bcgg.core.ui.theme.divider
import com.bcgg.feature.planeditor.compose.map.MapSearchResultItem
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.editorAiRecommendByAddress(
    isSearching: Boolean,
    selectedSearchResult: PlaceSearchResult?,
    placeSearchResult: List<PlaceSearchResult>,
    addedPlaceSearchResult: List<PlaceSearchResult>,
    onItemClick: (Int, PlaceSearchResult) -> Unit,
    onAddButtonClick: (PlaceSearchResult) -> Unit,
) {
    recommendItemSection(
        sectionName = "추천 여행지",
        description = "추가한 여행지의 주소를 기준으로 추천 여행지를 알려줍니다."
    )

    if (isSearching) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center)
                )
            }
        }

        return
    }

    if (placeSearchResult.isNotEmpty()) {
         items(count = placeSearchResult.size, key = {
            placeSearchResult[it].kakaoPlaceId
        }) { position ->
            val result = placeSearchResult[position]
            MapSearchResultItem(modifier = Modifier.animateItemPlacement(),
                placeSearchResult = result,
                isAdded = addedPlaceSearchResult.contains(result),
                onAddButtonClick = onAddButtonClick,
                selected = result == selectedSearchResult,
                onItemClick = {
                    onItemClick(position, it)
                }
            )
            if (position != placeSearchResult.size - 1) {
                Divider(color = MaterialTheme.colorScheme.divider)
            }
        }

        return
    }

    if (addedPlaceSearchResult.isEmpty()) {
        item {
            androidx.compose.material.Text(
                text = "지도 화면에서 여행지를 추가하면 추천이 시작됩니다",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                textAlign = TextAlign.Center
            )
        }

        return
    }

    if (placeSearchResult.isEmpty()) {
        item {
            androidx.compose.material.Text(
                text = "지도 화면에서 여행지를 추가하면 추천이 시작됩니다",
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

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.recommendItemSection(
    sectionName: String,
    description: String
) {
    stickyHeader {
        var isPopupShow by rememberSaveable { mutableStateOf(false) }
        val localDensity = LocalDensity.current
        val popupOffset by remember {
            with(localDensity) {
                mutableStateOf(
                    IntOffset(
                        x = (-30).dp.toPx().toInt(), y = 12.dp.toPx().toInt()
                    )
                )
            }
        }

        LaunchedEffect(isPopupShow) {
            delay(5000)
            isPopupShow = false
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sectionName,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(vertical = 16.dp),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(
                modifier = Modifier.offset(x = (-8).dp),
                onClick = { isPopupShow = true }
            ) {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            AnimatedVisibility(visible = isPopupShow) {
                Popup(
                    offset = popupOffset
                ) {
                    Box(
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .clip(MaterialTheme.shapes.small)
                            .background(
                                MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.9f)
                            )
                            .padding(8.dp)
                    ) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.surface
                        )
                    }
                }
            }
        }
    }
}