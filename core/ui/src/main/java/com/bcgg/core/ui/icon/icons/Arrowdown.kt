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

public val Icons.Arrowdown: ImageVector
    get() {
        if (_arrowdown != null) {
            return _arrowdown!!
        }
        _arrowdown = Builder(
            name = "Arrowdown", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(11.0f, 17.5355f)
                lineTo(11.0f, 4.0f)
                curveTo(11.0f, 3.4477f, 11.4477f, 3.0f, 12.0f, 3.0f)
                curveTo(12.5523f, 3.0f, 13.0f, 3.4477f, 13.0f, 4.0f)
                lineTo(13.0f, 17.435f)
                lineTo(16.1924f, 14.2426f)
                curveTo(16.5829f, 13.8521f, 17.2161f, 13.8521f, 17.6066f, 14.2426f)
                curveTo(17.9971f, 14.6332f, 17.9971f, 15.2663f, 17.6066f, 15.6569f)
                lineTo(13.364f, 19.8995f)
                curveTo(12.5829f, 20.6805f, 11.3166f, 20.6805f, 10.5355f, 19.8995f)
                lineTo(6.2929f, 15.6569f)
                curveTo(5.9024f, 15.2663f, 5.9024f, 14.6332f, 6.2929f, 14.2426f)
                curveTo(6.6834f, 13.8521f, 7.3166f, 13.8521f, 7.7071f, 14.2426f)
                lineTo(11.0f, 17.5355f)
                lineTo(11.0f, 17.5355f)
                close()
            }
        }
            .build()
        return _arrowdown!!
    }

private var _arrowdown: ImageVector? = null
