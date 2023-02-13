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

public val Icons.Arrowleftup: ImageVector
    get() {
        if (_arrowleftup != null) {
            return _arrowleftup!!
        }
        _arrowleftup = Builder(
            name = "Arrowleftup", defaultWidth = 24.0.dp,
            defaultHeight =
            24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(8.4142f, 7.0f)
                lineTo(18.364f, 16.9497f)
                curveTo(18.7545f, 17.3403f, 18.7545f, 17.9734f, 18.364f, 18.364f)
                curveTo(17.9734f, 18.7545f, 17.3403f, 18.7545f, 16.9497f, 18.364f)
                lineTo(7.0f, 8.4142f)
                lineTo(7.0f, 14.0f)
                curveTo(7.0f, 14.5523f, 6.5523f, 15.0f, 6.0f, 15.0f)
                curveTo(5.4477f, 15.0f, 5.0f, 14.5523f, 5.0f, 14.0f)
                lineTo(5.0f, 7.0f)
                curveTo(5.0f, 5.8954f, 5.8954f, 5.0f, 7.0f, 5.0f)
                lineTo(14.0f, 5.0f)
                curveTo(14.5523f, 5.0f, 15.0f, 5.4477f, 15.0f, 6.0f)
                curveTo(15.0f, 6.5523f, 14.5523f, 7.0f, 14.0f, 7.0f)
                lineTo(8.4142f, 7.0f)
                close()
            }
        }
            .build()
        return _arrowleftup!!
    }

private var _arrowleftup: ImageVector? = null
