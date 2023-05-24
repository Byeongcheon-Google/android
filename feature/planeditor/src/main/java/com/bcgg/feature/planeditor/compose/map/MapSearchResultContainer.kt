package com.bcgg.feature.planeditor.compose.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.bcgg.core.domain.model.Destination
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import com.bcgg.core.ui.constant.UiConstant
import com.bcgg.core.ui.theme.divider
import com.bcgg.feature.planeditor.compose.screen.contains

@Composable
fun MapSearchResultContainer(
    modifier: Modifier = Modifier,
    placeSearchResults: LazyPagingItems<PlaceSearchResult>,
    addedPlaceSearchResult: List<PlaceSearchResult>,
    selectedSearchResultPosition: Int,
    expanded: Boolean = true,
    lazyListState: LazyListState = rememberLazyListState(),
    onAddButtonClick: (PlaceSearchResult) -> Unit,
    onItemClick: (Int, PlaceSearchResult) -> Unit,
    onRequestExpandButtonClicked: () -> Unit
) {
    val localConfiguration = LocalConfiguration.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = UiConstant.SEMI_TRANSPARENT_AMOUNT))
            .clickable { if (!expanded) onRequestExpandButtonClicked() }
            .heightIn(min = 40.dp, max = (localConfiguration.screenHeightDp * 0.4).dp),
        contentAlignment = Alignment.Center
    ) {
        if (placeSearchResults.itemCount == 0) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp),
                text = "검색 결과가 없습니다.",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outlineVariant
            )
        } else {
            AnimatedVisibility(
                visible = expanded,
                enter = slideInVertically() + expandVertically() + fadeIn(),
                exit = slideOutVertically() + shrinkVertically() + fadeOut()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = lazyListState
                ) {
                    items(placeSearchResults.itemCount) { position ->
                        val result = placeSearchResults[position]

                        if (result != null) {
                            MapSearchResultItem(
                                placeSearchResult = result,
                                isAdded = addedPlaceSearchResult.contains(result),
                                onAddButtonClick = onAddButtonClick,
                                selected = position == selectedSearchResultPosition,
                                onItemClick = {
                                    onItemClick(position, it)
                                }
                            )
                            if (position != placeSearchResults.itemCount - 1) {
                                Divider(color = MaterialTheme.colorScheme.divider)
                            }
                        }
                    }
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
}
