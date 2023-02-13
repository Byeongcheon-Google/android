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

public val Icons.Arrowleftdown: ImageVector
    get() {
        if (_arrowleftdown != null) {
            return _arrowleftdown!!
        }
        _arrowleftdown = Builder(
            name = "Arrowleftdown", defaultWidth = 24.0.dp,
            defaultHeight =
            24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(7.0f, 15.5826f)
                lineTo(16.9497f, 5.6329f)
                curveTo(17.3403f, 5.2424f, 17.9734f, 5.2424f, 18.364f, 5.6329f)
                curveTo(18.7545f, 6.0234f, 18.7545f, 6.6566f, 18.364f, 7.0471f)
                lineTo(8.4142f, 16.9969f)
                lineTo(14.0f, 16.9969f)
                curveTo(14.5523f, 16.9969f, 15.0f, 17.4446f, 15.0f, 17.9969f)
                curveTo(15.0f, 18.5491f, 14.5523f, 18.9969f, 14.0f, 18.9969f)
                lineTo(7.0f, 18.9969f)
                curveTo(5.8954f, 18.9969f, 5.0f, 18.1014f, 5.0f, 16.9969f)
                lineTo(5.0f, 9.9969f)
                curveTo(5.0f, 9.4446f, 5.4477f, 8.9969f, 6.0f, 8.9969f)
                curveTo(6.5523f, 8.9969f, 7.0f, 9.4446f, 7.0f, 9.9969f)
                lineTo(7.0f, 15.5826f)
                lineTo(7.0f, 15.5826f)
                close()
            }
        }
            .build()
        return _arrowleftdown!!
    }

private var _arrowleftdown: ImageVector? = null
