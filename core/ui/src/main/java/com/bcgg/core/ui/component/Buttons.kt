package com.bcgg.core.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.theme.AppTheme

@Composable
fun LargeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .heightIn(min = 48.dp),
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@Composable
fun SmallButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit
) {
    FilledTonalButton(
        modifier = modifier
            .heightIn(min = 32.dp),
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

@Preview
@Composable
internal fun LargeButtonPreview() {
    Column {
        AppTheme(useDarkTheme = false) {
            LargeButtonPreviewSample()
        }
        AppTheme(useDarkTheme = true) {
            LargeButtonPreviewSample()
        }
    }
}

@Composable
internal fun LargeButtonPreviewSample() {
    Surface {
        Column(
            modifier = Modifier.padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            LargeButton(onClick = { /*TODO*/ }) {
                Text(text = "Large Button")
            }
            SmallButton(onClick = { /*TODO*/ }) {
                Text(text = "Small Button")
            }
            LargeButton(onClick = { /*TODO*/ }, enabled = false) {
                Text(text = "Large Button")
            }
            SmallButton(onClick = { /*TODO*/ }, enabled = false) {
                Text(text = "Small Button")
            }
        }
    }
}
