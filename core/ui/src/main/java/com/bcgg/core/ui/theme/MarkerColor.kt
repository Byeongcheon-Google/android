package com.bcgg.core.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

internal val marker_color_light_1 = Color(0xFFF44336)
internal val marker_color_light_2 = Color(0xFFE91E63)
internal val marker_color_light_3 = Color(0xFF9C27B0)
internal val marker_color_light_4 = Color(0xFF673AB7)
internal val marker_color_light_5 = Color(0xFF3F51B5)
internal val marker_color_light_6 = Color(0xFF009688)
internal val marker_color_light_7 = Color(0xFFFFC107)
internal val marker_color_light_8 = Color(0xFFFF9800)

internal val marker_color_dark_1 = Color(0xFFE57373)
internal val marker_color_dark_2 = Color(0xFFF06292)
internal val marker_color_dark_3 = Color(0xFFBA68C8)
internal val marker_color_dark_4 = Color(0xFF9575CD)
internal val marker_color_dark_5 = Color(0xFF7986CB)
internal val marker_color_dark_6 = Color(0xFF4DB6AC)
internal val marker_color_dark_7 = Color(0xFFFFD54F)
internal val marker_color_dark_8 = Color(0xFFFFB74D)

@Stable
data class MarkerColorScheme(
    val colors: List<Color>
) {
    operator fun get(index: Int) = colors[index % colors.size]
}

val lightMarkerColors = MarkerColorScheme(
    colors = listOf(
        marker_color_light_1,
        marker_color_light_2,
        marker_color_light_3,
        marker_color_light_4,
        marker_color_light_5,
        marker_color_light_6,
        marker_color_light_7,
        marker_color_light_8
    )
)

val darkMarkerColors = MarkerColorScheme(
    colors = listOf(
        marker_color_dark_1,
        marker_color_dark_2,
        marker_color_dark_3,
        marker_color_dark_4,
        marker_color_dark_5,
        marker_color_dark_6,
        marker_color_dark_7,
        marker_color_dark_8
    )
)
