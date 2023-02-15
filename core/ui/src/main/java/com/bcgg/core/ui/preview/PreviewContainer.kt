package com.bcgg.core.ui.preview

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.bcgg.core.ui.theme.AppTheme

@Composable
fun PreviewContainer(
    contentOutAppTheme: @Composable () -> Unit = {},
    contentOnAppTheme: @Composable () -> Unit
) {
    Column {
        listOf(false, true).map {
            AppTheme(useDarkTheme = it) {
                Surface {
                    contentOnAppTheme()
                }
            }
        }
        contentOutAppTheme()
    }
}
