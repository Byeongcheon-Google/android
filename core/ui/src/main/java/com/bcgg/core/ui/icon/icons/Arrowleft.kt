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

public val Icons.Arrowleft: ImageVector
    get() {
        if (_arrowleft != null) {
            return _arrowleft!!
        }
        _arrowleft = Builder(
            name = "Arrowleft", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(6.4142f, 11.0f)
                lineTo(20.0f, 11.0f)
                curveTo(20.5523f, 11.0f, 21.0f, 11.4477f, 21.0f, 12.0f)
                curveTo(21.0f, 12.5523f, 20.5523f, 13.0f, 20.0f, 13.0f)
                lineTo(6.4142f, 13.0f)
                lineTo(9.6569f, 16.2426f)
                curveTo(10.0474f, 16.6332f, 10.0474f, 17.2663f, 9.6569f, 17.6569f)
                curveTo(9.2663f, 18.0474f, 8.6332f, 18.0474f, 8.2426f, 17.6569f)
                lineTo(4.0f, 13.4142f)
                curveTo(3.219f, 12.6332f, 3.219f, 11.3668f, 4.0f, 10.5858f)
                lineTo(8.2426f, 6.3431f)
                curveTo(8.6332f, 5.9526f, 9.2663f, 5.9526f, 9.6569f, 6.3431f)
                curveTo(10.0474f, 6.7337f, 10.0474f, 7.3668f, 9.6569f, 7.7574f)
                lineTo(6.4142f, 11.0f)
                close()
            }
        }
            .build()
        return _arrowleft!!
    }

private var _arrowleft: ImageVector? = null
