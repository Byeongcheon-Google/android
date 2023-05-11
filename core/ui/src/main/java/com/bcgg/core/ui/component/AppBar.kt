package com.bcgg.core.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.constant.UiConstant.SEMI_TRANSPARENT_AMOUNT
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.icon.icons.Food
import com.bcgg.core.ui.icon.icons.Search
import com.bcgg.core.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    title: @Composable () -> Unit,
) {
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .clip(MaterialTheme.shapes.large),
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = SEMI_TRANSPARENT_AMOUNT),
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.secondary
        ),
        windowInsets = WindowInsets(0.dp),
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchAppBar(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    search: String,
    placeholderText: String,
    onSearchTextChanged: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    RoundedAppBar(
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        scrollBehavior = scrollBehavior,
    ) {
        BasicTextField(
            value = search,
            onValueChange = onSearchTextChanged,
            singleLine = true,
            textStyle = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            ),
            decorationBox = { innerTextField ->
                if (search.isEmpty()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = placeholderText,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Normal),
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.56f)
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(imeAction = androidx.compose.ui.text.input.ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() }
            )
        )
    }
}

@Preview
@Composable
internal fun RoundedAppBarPreview() {
    Column {
        AppTheme(useDarkTheme = false) {
            RoundedAppBarPreviewSample()
        }
        AppTheme(useDarkTheme = true) {
            RoundedAppBarPreviewSample()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RoundedAppBarPreviewSample() {
    var search by remember { mutableStateOf("") }

    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Column(
            modifier = Modifier.padding(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            RoundedAppBar(
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Arrowleft,
                        contentDescription = ""
                    )
                },
                actions = {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Food,
                        contentDescription = ""
                    )
                }
            ) {
                Text(text = "App Bar")
            }

            SearchAppBar(
                navigationIcon = {
                    Icon(
                        modifier = Modifier.padding(16.dp),
                        imageVector = Icons.Search,
                        contentDescription = ""
                    )
                },
                search = search,
                onSearchTextChanged = { search = it },
                placeholderText = "Search"
            )
        }
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Icon(
        modifier = modifier
            .clickable(onClick = onBackClick)
            .padding(16.dp),
        imageVector = Icons.Arrowleft,
        contentDescription = ""
    )
}