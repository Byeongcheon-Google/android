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

public val Icons.Arrowrightup: ImageVector
    get() {
        if (_arrowrightup != null) {
            return _arrowrightup!!
        }
        _arrowrightup = Builder(
            name = "Arrowrightup", defaultWidth = 24.0.dp,
            defaultHeight =
            24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(15.5858f, 7.0f)
                lineTo(5.636f, 16.9497f)
                curveTo(5.2455f, 17.3403f, 5.2455f, 17.9734f, 5.636f, 18.364f)
                curveTo(6.0266f, 18.7545f, 6.6597f, 18.7545f, 7.0503f, 18.364f)
                lineTo(17.0f, 8.4142f)
                lineTo(17.0f, 14.0f)
                curveTo(17.0f, 14.5523f, 17.4477f, 15.0f, 18.0f, 15.0f)
                curveTo(18.5523f, 15.0f, 19.0f, 14.5523f, 19.0f, 14.0f)
                lineTo(19.0f, 7.0f)
                curveTo(19.0f, 5.8954f, 18.1046f, 5.0f, 17.0f, 5.0f)
                lineTo(10.0f, 5.0f)
                curveTo(9.4477f, 5.0f, 9.0f, 5.4477f, 9.0f, 6.0f)
                curveTo(9.0f, 6.5523f, 9.4477f, 7.0f, 10.0f, 7.0f)
                lineTo(15.5858f, 7.0f)
                close()
            }
        }
            .build()
        return _arrowrightup!!
    }

private var _arrowrightup: ImageVector? = null
