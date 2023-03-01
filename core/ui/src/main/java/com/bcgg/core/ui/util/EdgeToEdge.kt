package com.bcgg.core.ui.util

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun EdgeToEdge() {
    val view = LocalView.current
    val isDarkTheme = !isSystemInDarkTheme()
    SideEffect {
        val window = (view.context as Activity).window
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        window.statusBarColor = Color.Transparent.toArgb()
        window.navigationBarColor = Color.Transparent.toArgb()

        windowInsetsController.isAppearanceLightNavigationBars = isDarkTheme
        windowInsetsController.isAppearanceLightStatusBars = isDarkTheme
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
