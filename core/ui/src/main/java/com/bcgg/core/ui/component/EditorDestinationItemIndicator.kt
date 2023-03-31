package com.bcgg.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun RowWithIndicator(
    modifier: Modifier = Modifier,
    indicatorType: IndicatorType,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min)
    ) {
        ListItemIndicator(indicatorType = indicatorType)
        content()
    }
}

@Composable
fun ListItemIndicator(
    modifier: Modifier = Modifier,
    indicatorType: IndicatorType
) {
    Box(
        modifier = modifier
            .width(56.dp),
    ) {
        Box(
            modifier = Modifier
                .indicatorLine(indicatorType)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.TopCenter)
        )
        Box(
            modifier = Modifier
                .padding(top = 28.dp)
                .size(8.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.primary)
                .align(Alignment.TopCenter)
        )
    }
}

internal fun Modifier.indicatorLine(indicatorType: IndicatorType): Modifier {
    return when (indicatorType) {
        IndicatorType.Header ->
            this
                .width(2.dp)
                .fillMaxHeight()
                .padding(top = 28.dp)
        IndicatorType.Footer ->
            this
                .width(2.dp)
                .height(28.dp)
        IndicatorType.Normal ->
            this
                .width(2.dp)
                .fillMaxHeight()
        IndicatorType.One -> this
    }
}

fun getIndicatorType(position: Int, size: Int) : IndicatorType {
    if(size == 1) return IndicatorType.One

    if(position == 0) return IndicatorType.Header
    if(position == size - 1) return IndicatorType.Footer
    return IndicatorType.Normal
}

enum class IndicatorType {
    Header, Footer, Normal, One
}