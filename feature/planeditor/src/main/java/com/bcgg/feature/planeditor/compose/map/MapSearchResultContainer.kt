package com.bcgg.feature.planeditor.compose.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bcgg.core.util.PlaceSearchResult
import com.bcgg.feature.planeditor.compose.editor.editorAiRecommendByAddress

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MapSearchResultContainer(
    modifier: Modifier = Modifier,
    placeSearchResults: LazyPagingItems<PlaceSearchResult>?,
    addedPlaceSearchResult: List<PlaceSearchResult>,
    aiPlaceSearchResult: List<PlaceSearchResult>,
    aiAddressSearchResult: List<PlaceSearchResult>,
    selectedSearchResult: PlaceSearchResult?,
    isAiSearching: Boolean,
    isMapSearching: Boolean,
    expanded: Boolean,
    lazyListState: LazyListState = rememberLazyListState(),
    onAddButtonClick: (PlaceSearchResult) -> Unit,
    onItemClick: (Int, PlaceSearchResult) -> Unit,
    onRequestExpandButtonClicked: () -> Unit
) {
    val localConfiguration = LocalConfiguration.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                MaterialTheme.shapes.large.copy(
                    bottomEnd = CornerSize(0),
                    bottomStart = CornerSize(0),
                    topStart = CornerSize(16.dp),
                    topEnd = CornerSize(16.dp)
                )
            )
            .background(MaterialTheme.colorScheme.surface)
            .clickable { if (!expanded) onRequestExpandButtonClicked() }
            .heightIn(min = 40.dp, max = (localConfiguration.screenHeightDp * 0.4).dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = slideInVertically() + expandVertically() + fadeIn(),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = lazyListState
            ) {
                if (aiAddressSearchResult.isNotEmpty())
                    editorAiRecommendByAddress(
                        isSearching = false,
                        placeSearchResult = aiAddressSearchResult,
                        addedPlaceSearchResult = addedPlaceSearchResult,
                        onAddButtonClick = onAddButtonClick,
                        onItemClick = onItemClick,
                        selectedSearchResult = selectedSearchResult,
                    )

                aiMapSearchResult(
                    isSearching = isAiSearching,
                    aiPlaceSearchResult = aiPlaceSearchResult,
                    addedPlaceSearchResult = addedPlaceSearchResult,
                    selectedSearchResult = selectedSearchResult,
                    onItemClick = onItemClick,
                    onAddButtonClick = onAddButtonClick,
                )

                mapSearchResult(
                    isSearching = isMapSearching,
                    placeSearchResult = placeSearchResults,
                    addedPlaceSearchResult = addedPlaceSearchResult,
                    selectedSearchResult = selectedSearchResult,
                    onItemClick = onItemClick,
                    onAddButtonClick = onAddButtonClick,
                )
            }
        }
        AnimatedVisibility(
            visible = !expanded,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = "검색 결과를 확인하려면 터치하세요.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        }
    }
}
