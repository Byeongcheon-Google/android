package com.bcgg.feature.planeditor.compose.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.component.SearchAppBar
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.icon.icons.Search
import com.bcgg.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    search: String,
    isSearching: Boolean,
    onSearchTextChanged: (String) -> Unit,
    onSearch: (String) -> Unit,
    onBack: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    SearchAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(
                enabled = true,
                onClick = { onBack() }
            ) {
                Icon(
                    imageVector = Icons.Arrowleft,
                    contentDescription = ""
                )
            }
        },
        actions = {
            if (isSearching) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    strokeWidth = 2.dp,
                )
            } else {
                IconButton(
                    enabled = search.isNotBlank(),
                    onClick = {
                        keyboardController?.hide()
                        onSearch(search)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Search,
                        contentDescription = ""
                    )
                }
            }
        },
        search = search,
        onSearchTextChanged = onSearchTextChanged,
        placeholderText = "지역, 장소 검색"
    )
}

@Preview
@Composable
internal fun SearchBarPreview() {
    var search by remember {
        mutableStateOf("")
    }
    Column {
        AppTheme(useDarkTheme = false) {
            SearchBar(
                search = search,
                onSearchTextChanged = {
                    search = it
                },
                onSearch = {},
                onBack = {},
                isSearching = true
            )
        }
        AppTheme(useDarkTheme = true) {
            SearchBar(
                search = search,
                onSearchTextChanged = {
                    search = it
                },
                onSearch = {},
                onBack = {},
                isSearching = false
            )
        }
    }
}
