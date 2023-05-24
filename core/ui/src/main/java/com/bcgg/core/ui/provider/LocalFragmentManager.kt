package com.bcgg.core.ui.provider

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.fragment.app.FragmentManager

val LocalFragmentManager = staticCompositionLocalOf<FragmentManager> {
    noLocalProvidedFor("LocalFragmentManager")
}

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present")
}
