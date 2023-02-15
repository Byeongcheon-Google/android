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
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.MapSearchResult
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.theme.divider

@Composable
fun MapSearchResultContainer(
    modifier: Modifier = Modifier,
    mapSearchResults: List<MapSearchResult>,
    expanded: Boolean = true,
    onAddButtonClick: (MapSearchResult) -> Unit,
    onRemoveButtonClick: (MapSearchResult) -> Unit,
    onItemClick: (MapSearchResult) -> Unit,
    onRequestExpandButtonClicked: () -> Unit
) {
    val localConfiguration = LocalConfiguration.current

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .clickable { if (!expanded) onRequestExpandButtonClicked() }
            .heightIn(min = 40.dp, max = (localConfiguration.screenHeightDp * 0.4).dp),
        contentAlignment = Alignment.Center
    ) {
        if (mapSearchResults.isEmpty()) {
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
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(mapSearchResults.size) {
                        MapSearchResultItem(
                            mapSearchResult = mapSearchResults[it],
                            isAdded = it == 0,
                            onAddButtonClick = onAddButtonClick,
                            onRemoveButtonClick = onRemoveButtonClick,
                            onItemClick = onItemClick
                        )
                        if (it != mapSearchResults.lastIndex) {
                            Divider(color = MaterialTheme.colorScheme.divider)
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
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(device = Devices.NEXUS_5)
@Composable
private fun MapSearchResultItemPreview() {

    var heightDp by remember {
        mutableStateOf(240.dp)
    }
    var expanded by remember {
        mutableStateOf(true)
    }

    val sample = listOf<MapSearchResult>(
        /*MapSearchResult(
            name = "성심당 DCC점",
            distance = "50km",
            address = "대전 유성구 엑스포로 107",
            lat = 1.0,
            lng = 1.0
        ),
        MapSearchResult(
            name = "성심당 롯데백화점대전점",
            distance = "53km",
            address = "대전 서구 계롱로 598",
            lat = 1.0,
            lng = 1.0
        ),
        MapSearchResult(
            name = "성심당 대전역점",
            distance = "56km",
            address = "대전 동구 중앙로 215",
            lat = 1.0,
            lng = 1.0
        )*/
    )

    AppTheme(
        useDarkTheme = false
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.secondary,
            bottomBar = {
                MapSearchResultContainer(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    mapSearchResults = sample,
                    expanded = expanded,
                    onAddButtonClick = {},
                    onRemoveButtonClick = {},
                    onItemClick = { expanded = false },
                    onRequestExpandButtonClicked = { expanded = true }
                )
            }
        ) {
            Box(modifier = Modifier.padding(it)) {
            }
        }
    }
}
