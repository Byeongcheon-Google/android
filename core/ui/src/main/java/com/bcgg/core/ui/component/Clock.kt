package com.bcgg.core.ui.component

import android.graphics.Paint
import android.graphics.RectF
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.constant.ClockConstant.HOUR_DIVIDER
import com.bcgg.core.ui.constant.ClockConstant.MINUTE_DIVIDER
import com.bcgg.core.ui.preview.PreviewContainer
import java.time.LocalTime
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random
import kotlin.random.nextInt

@Composable
fun ClockIcon(
    modifier: Modifier = Modifier,
    start: LocalTime,
    endInclusive: LocalTime? = null
) {
    val backgroundColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f).toArgb()
    val handleColor = MaterialTheme.colorScheme.secondary.toArgb()
    val arcColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.35f).toArgb()

    val time1Degree by animateFloatAsState(
        targetValue = -90 + start.hour * HOUR_DIVIDER + start.minute * MINUTE_DIVIDER
    )

    val time2Degree by animateFloatAsState(
        targetValue = endInclusive?.let { endInclusive ->
            -90 + endInclusive.hour * HOUR_DIVIDER + endInclusive.minute * MINUTE_DIVIDER
        } ?: Float.NaN
    )

    Canvas(modifier = modifier) {
        // 시계 배경
        val radius: Float = size.minDimension / 2.0f // 원의 반지름

        drawContext.canvas.nativeCanvas.apply {
            this.drawCircle(
                center.x, center.y, radius,
                Paint().apply {
                    color = backgroundColor
                }
            )

            if (!time2Degree.isNaN()) {
                this.drawArc(
                    RectF(
                        0f, 0f, center.x * 2, center.y * 2
                    ),
                    time1Degree,
                    (time2Degree - time1Degree),
                    true,
                    Paint().apply {
                        color = arcColor
                    }
                )

                this.drawLine(
                    center.x,
                    center.y,
                    (center.x + radius * 0.85 * cos(Math.toRadians(time2Degree.toDouble()))).toFloat(),
                    (center.x + radius * 0.85 * sin(Math.toRadians(time2Degree.toDouble()))).toFloat(),
                    Paint().apply {
                        color = handleColor
                        strokeWidth = 1.dp.toPx()
                        strokeCap = Paint.Cap.ROUND
                    }
                )
            }

            this.drawLine(
                center.x,
                center.y,
                (center.x + radius * 0.85 * cos(Math.toRadians(time1Degree.toDouble()))).toFloat(),
                (center.x + radius * 0.85 * sin(Math.toRadians(time1Degree.toDouble()))).toFloat(),
                Paint().apply {
                    color = handleColor
                    strokeWidth = 1.dp.toPx()
                    strokeCap = Paint.Cap.ROUND
                }
            )
        }
    }
}

@Composable
fun ClockIcon(
    modifier: Modifier = Modifier,
    timeRange: ClosedRange<LocalTime>
) = ClockIcon(modifier, timeRange.start, timeRange.endInclusive)

@Preview
@Composable
private fun ClockPreview() {

    PreviewContainer {
        Row {
            ClockIcon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp),
                start = LocalTime.now()
            )
            ClockIcon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp),
                start = LocalTime.of(9, 0),
                endInclusive = LocalTime.of(12, 30)
            )
            ClockIcon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp),
                start = LocalTime.of(9, 0),
                endInclusive = LocalTime.of(12, 30)
            )
        }
    }
}
