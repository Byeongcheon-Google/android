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

public val Icons.Arrowrightdown: ImageVector
    get() {
        if (_arrowrightdown != null) {
            return _arrowrightdown!!
        }
        _arrowrightdown = Builder(
            name = "Arrowrightdown", defaultWidth = 24.0.dp,
            defaultHeight =
            24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(17.0f, 15.5858f)
                lineTo(7.0503f, 5.636f)
                curveTo(6.6597f, 5.2455f, 6.0266f, 5.2455f, 5.636f, 5.636f)
                curveTo(5.2455f, 6.0266f, 5.2455f, 6.6597f, 5.636f, 7.0503f)
                lineTo(15.5858f, 17.0f)
                lineTo(10.0f, 17.0f)
                curveTo(9.4477f, 17.0f, 9.0f, 17.4477f, 9.0f, 18.0f)
                curveTo(9.0f, 18.5523f, 9.4477f, 19.0f, 10.0f, 19.0f)
                lineTo(17.0f, 19.0f)
                curveTo(18.1046f, 19.0f, 19.0f, 18.1046f, 19.0f, 17.0f)
                lineTo(19.0f, 10.0f)
                curveTo(19.0f, 9.4477f, 18.5523f, 9.0f, 18.0f, 9.0f)
                curveTo(17.4477f, 9.0f, 17.0f, 9.4477f, 17.0f, 10.0f)
                lineTo(17.0f, 15.5858f)
                lineTo(17.0f, 15.5858f)
                close()
            }
        }
            .build()
        return _arrowrightdown!!
    }

private var _arrowrightdown: ImageVector? = null
