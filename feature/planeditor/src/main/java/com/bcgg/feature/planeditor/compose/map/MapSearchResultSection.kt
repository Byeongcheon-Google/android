package com.bcgg.feature.planeditor.compose.map

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bcgg.core.util.PlaceSearchResult
import com.bcgg.core.ui.theme.divider
import com.bcgg.feature.planeditor.compose.editor.recommendItemSection

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.aiMapSearchResult(
    isSearching: Boolean,
    aiPlaceSearchResult: List<PlaceSearchResult>,
    addedPlaceSearchResult: List<PlaceSearchResult>,
    selectedSearchResult: PlaceSearchResult?,
    onItemClick: (Int, PlaceSearchResult) -> Unit,
    onAddButtonClick: (PlaceSearchResult) -> Unit
) {
    if (aiPlaceSearchResult.isEmpty()) return

    recommendItemSection(
        sectionName = "AI 추천 장소",
        description = "검색어를 기반으로 최대 5개의 여행지를 추천합니다."
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

    items(
        count = aiPlaceSearchResult.count(),
        key = {
            aiPlaceSearchResult[it].kakaoPlaceId
        }
    ) { position ->
        val result = aiPlaceSearchResult[position]
        MapSearchResultItem(
            modifier = Modifier.animateItemPlacement(),
            placeSearchResult = result,
            isAdded = addedPlaceSearchResult.contains(result),
            onAddButtonClick = onAddButtonClick,
            selected = result == selectedSearchResult,
            onItemClick = {
                onItemClick(position, it)
            }
        )
        if (position != aiPlaceSearchResult.size - 1) {
            Divider(color = MaterialTheme.colorScheme.divider)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
fun LazyListScope.mapSearchResult(
    isSearching: Boolean,
    placeSearchResult: LazyPagingItems<PlaceSearchResult>?,
    addedPlaceSearchResult: List<PlaceSearchResult>,
    selectedSearchResult: PlaceSearchResult?,
    onItemClick: (Int, PlaceSearchResult) -> Unit,
    onAddButtonClick: (PlaceSearchResult) -> Unit
) {
    if(placeSearchResult == null) return

    stickyHeader {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
            text = "장소 검색 결과",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelMedium
        )
    }

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

    if (placeSearchResult.itemSnapshotList.isEmpty()) {
        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "장소 검색 결과가 없습니다.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }

    items(
        count = placeSearchResult.itemSnapshotList.count(),
        key = {
            placeSearchResult[it]!!.kakaoPlaceId
        }
    ) { position ->
        val result = placeSearchResult.itemSnapshotList[position]

        if (result != null) {
            MapSearchResultItem(
                modifier = Modifier.animateItemPlacement(),
                placeSearchResult = result,
                isAdded = addedPlaceSearchResult.contains(result),
                onAddButtonClick = onAddButtonClick,
                selected = result == selectedSearchResult,
                onItemClick = {
                    onItemClick(position, it)
                }
            )
            if (position != placeSearchResult.itemCount - 1) {
                Divider(color = MaterialTheme.colorScheme.divider)
            }
        }
    }
}