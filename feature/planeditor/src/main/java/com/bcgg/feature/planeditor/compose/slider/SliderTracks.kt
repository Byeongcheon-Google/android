package com.bcgg.feature.planeditor.compose.slider

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bcgg.feature.planeditor.constant.Constant.TIME_SLIDER_END_TIME
import com.bcgg.feature.planeditor.constant.Constant.VERTICAL_LINE_TICK_START

@Composable
fun BoxScope.SliderTrack(
    modifier: Modifier = Modifier,
    trackHeight: Dp = 4.dp,
    trackColor: Color = MaterialTheme.colorScheme.outlineVariant
) {

    CoreSliderTrack(
        modifier = modifier
            .fillMaxWidth(),
        trackHeight = trackHeight,
        trackColor = trackColor
    )
}

@Composable
fun BoxScope.SliderLeftThresholdTrack(
    modifier: Modifier = Modifier,
    trackHeight: Dp = 4.dp,
    trackColor: Color = MaterialTheme.colorScheme.secondary,
    fraction: Float
) {
    CoreSliderTrack(
        modifier = modifier
            .fillMaxWidth(fraction)
            .align(Alignment.CenterStart),
        trackHeight = trackHeight,
        trackColor = trackColor
    )
}

@Composable
fun BoxScope.SliderRightThresholdTrack(
    modifier: Modifier = Modifier,
    trackHeight: Dp = 4.dp,
    trackColor: Color = MaterialTheme.colorScheme.secondary,
    fraction: Float
) {
    CoreSliderTrack(
        modifier = modifier
            .fillMaxWidth(fraction)
            .align(Alignment.CenterEnd),
        trackHeight = trackHeight,
        trackColor = trackColor
    )
}

@Composable
private fun CoreSliderTrack(
    modifier: Modifier = Modifier,
    trackHeight: Dp = 4.dp,
    trackColor: Color = MaterialTheme.colorScheme.outlineVariant
) {
    Box(
        modifier = modifier
            .height(trackHeight)
            .clip(MaterialTheme.shapes.small)
            .background(trackColor)
    )
}

@Composable
internal fun VerticalLines() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(10.dp)
    ) {
        val ticks = (TIME_SLIDER_END_TIME * 6).toInt()
        val color = MaterialTheme.colorScheme.outline
        val drawPadding: Float = with(LocalDensity.current) { 10.dp.toPx() }
        Canvas(modifier = Modifier.fillMaxSize()) {
            val yStart = 0f
            val yEnd = size.height
            val distance: Float = (size.width.minus(2 * drawPadding)).div(ticks)
            for (index in VERTICAL_LINE_TICK_START..ticks step 6) {
                drawLine(
                    color = color,
                    start = Offset(x = drawPadding + index.times(distance), y = yStart),
                    end = Offset(x = drawPadding + index.times(distance), y = yEnd)
                )
            }
        }
    }
}
