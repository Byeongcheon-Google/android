package com.bcgg.core.ui.icon.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.icon.Icons

public val Icons.Arrowup: ImageVector
    get() {
        if (_arrowup != null) {
            return _arrowup!!
        }
        _arrowup = Builder(
            name = "Arrowup", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(12.9497f, 6.364f)
                lineTo(12.9497f, 20.0f)
                curveTo(12.9497f, 20.5523f, 12.502f, 21.0f, 11.9497f, 21.0f)
                curveTo(11.3975f, 21.0f, 10.9497f, 20.5523f, 10.9497f, 20.0f)
                lineTo(10.9497f, 6.4645f)
                lineTo(7.7574f, 9.6569f)
                curveTo(7.3668f, 10.0474f, 6.7337f, 10.0474f, 6.3431f, 9.6569f)
                curveTo(5.9526f, 9.2663f, 5.9526f, 8.6332f, 6.3431f, 8.2426f)
                lineTo(10.5858f, 4.0f)
                curveTo(11.3668f, 3.219f, 12.6332f, 3.219f, 13.4142f, 4.0f)
                lineTo(17.6569f, 8.2426f)
                curveTo(18.0474f, 8.6332f, 18.0474f, 9.2663f, 17.6569f, 9.6569f)
                curveTo(17.2663f, 10.0474f, 16.6332f, 10.0474f, 16.2426f, 9.6569f)
                lineTo(12.9497f, 6.364f)
                close()
            }
        }
            .build()
        return _arrowup!!
    }

private var _arrowup: ImageVector? = null
