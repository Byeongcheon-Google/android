package com.bcgg.feature.planeditor.compose.map

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import com.bcgg.core.ui.component.WebView
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.feature.planeditor.util.getClassificationIcon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchResultItem(
    placeSearchResult: PlaceSearchResult,
    isAdded: Boolean,
    selected: Boolean,
    onAddButtonClick: (PlaceSearchResult) -> Unit,
    onItemClick: (PlaceSearchResult) -> Unit
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (selected) {
            MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
        } else {
            Color.Transparent
        }
    )
    var show by rememberSaveable { mutableStateOf(false) }

    AnimatedVisibility(visible = show) {
        Dialog(
            onDismissRequest = { show = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    text = placeSearchResult.name,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                Text(
                                    text = placeSearchResult.name,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = { show = false }) {
                                Icon(imageVector = Icons.Arrowleft, contentDescription = "")
                            }
                        },
                        actions = {
                            FilledTonalButton(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .heightIn(min = 32.dp),
                                onClick = { onAddButtonClick(placeSearchResult) },
                                enabled = !isAdded
                            ) {
                                Text(
                                    text = if (isAdded) "추가됨" else "추가",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    )
                }
            ) {
                WebView(
                    modifier = Modifier.padding(it),
                    url = "http://place.map.kakao.com/${placeSearchResult.id}",
                    onPageLoading = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .background(color = backgroundColor)
            .clickable {
                onItemClick(placeSearchResult)
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.Top
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = placeSearchResult.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Text(
            text = placeSearchResult.address,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Row(
            modifier = Modifier.padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = getClassificationIcon(placeSearchResult.classification),
                contentDescription = ""
            )
            FilledTonalButton(
                modifier = Modifier.heightIn(min = 32.dp),
                onClick = { onAddButtonClick(placeSearchResult) },
                enabled = !isAdded
            ) {
                Text(
                    text = if (isAdded) "추가됨" else "추가",
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            TextButton(onClick = { show = true }) {
                Text(text = "자세히 보기")
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
                        placeSearchResult = PlaceSearchResult(
                            name = "성심당 DCC점",
                            address = "대전 유성구 엑스포로 107",
                            id = "asdf"
                        ),
                        isAdded = it.second,
                        onAddButtonClick = {},
                        selected = true
                    ) {
                    }
                }
            }
        }
    }
}
