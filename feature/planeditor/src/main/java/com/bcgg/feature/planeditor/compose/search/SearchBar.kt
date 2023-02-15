package com.bcgg.feature.planeditor.compose.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.component.SearchAppBar
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Search
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.feature.planeditor.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    search: String,
    onSearchTextChanged: (String) -> Unit
) {
    SearchAppBar(
        navigationIcon = {
            Icon(
                modifier = Modifier.padding(16.dp),
                imageVector = Icons.Search,
                contentDescription = ""
            )
        },
        search = search,
        onSearchTextChanged = onSearchTextChanged,
        placeholderText = stringResource(R.string.search_bar_placeholder)
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
                }
            )
        }
        AppTheme(useDarkTheme = true) {
            SearchBar(
                search = search,
                onSearchTextChanged = {
                    search = it
                }
            )
        }
    }
}
