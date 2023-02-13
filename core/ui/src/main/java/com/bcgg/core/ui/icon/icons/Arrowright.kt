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

public val Icons.Arrowright: ImageVector
    get() {
        if (_arrowright != null) {
            return _arrowright!!
        }
        _arrowright = Builder(
            name = "Arrowright", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(17.636f, 11.0503f)
                lineTo(4.0f, 11.0503f)
                curveTo(3.4477f, 11.0503f, 3.0f, 11.498f, 3.0f, 12.0503f)
                curveTo(3.0f, 12.6025f, 3.4477f, 13.0503f, 4.0f, 13.0503f)
                lineTo(17.5355f, 13.0503f)
                lineTo(14.3431f, 16.2426f)
                curveTo(13.9526f, 16.6332f, 13.9526f, 17.2663f, 14.3431f, 17.6569f)
                curveTo(14.7337f, 18.0474f, 15.3668f, 18.0474f, 15.7574f, 17.6569f)
                lineTo(20.0f, 13.4142f)
                curveTo(20.781f, 12.6332f, 20.781f, 11.3668f, 20.0f, 10.5858f)
                lineTo(15.7574f, 6.3431f)
                curveTo(15.3668f, 5.9526f, 14.7337f, 5.9526f, 14.3431f, 6.3431f)
                curveTo(13.9526f, 6.7337f, 13.9526f, 7.3668f, 14.3431f, 7.7574f)
                lineTo(17.636f, 11.0503f)
                lineTo(17.636f, 11.0503f)
                close()
            }
        }
            .build()
        return _arrowright!!
    }

private var _arrowright: ImageVector? = null
