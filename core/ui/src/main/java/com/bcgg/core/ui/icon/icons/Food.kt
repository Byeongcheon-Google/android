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

public val Icons.Food: ImageVector
    get() {
        if (_food != null) {
            return _food!!
        }
        _food = Builder(
            name = "Food", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(7.5f, 3.0f)
                lineTo(6.5f, 3.0f)
                lineTo(6.5f, 6.0f)
                curveTo(6.5f, 6.5523f, 6.0523f, 7.0f, 5.5f, 7.0f)
                curveTo(4.9477f, 7.0f, 4.5f, 6.5523f, 4.5f, 6.0f)
                lineTo(4.5f, 3.2676f)
                curveTo(3.9022f, 3.6134f, 3.5f, 4.2597f, 3.5f, 5.0f)
                lineTo(3.5f, 8.5581f)
                curveTo(3.5f, 9.1657f, 3.7762f, 9.7403f, 4.2506f, 10.1199f)
                lineTo(5.2494f, 10.9189f)
                curveTo(5.7238f, 11.2984f, 6.0f, 11.8731f, 6.0f, 12.4806f)
                lineTo(6.0f, 20.0f)
                curveTo(6.0f, 20.5523f, 6.4477f, 21.0f, 7.0f, 21.0f)
                curveTo(7.5523f, 21.0f, 8.0f, 20.5523f, 8.0f, 20.0f)
                lineTo(8.0f, 12.4806f)
                curveTo(8.0f, 11.8731f, 8.2762f, 11.2984f, 8.7506f, 10.9189f)
                lineTo(9.7494f, 10.1199f)
                curveTo(10.2238f, 9.7403f, 10.5f, 9.1657f, 10.5f, 8.5581f)
                lineTo(10.5f, 5.0f)
                curveTo(10.5f, 4.2597f, 10.0978f, 3.6134f, 9.5f, 3.2676f)
                lineTo(9.5f, 6.0f)
                curveTo(9.5f, 6.5523f, 9.0523f, 7.0f, 8.5f, 7.0f)
                curveTo(7.9477f, 7.0f, 7.5f, 6.5523f, 7.5f, 6.0f)
                lineTo(7.5f, 3.0f)
                close()
                moveTo(16.5f, 15.0f)
                curveTo(14.8431f, 15.0f, 13.5f, 13.6569f, 13.5f, 12.0f)
                lineTo(13.5f, 8.0f)
                curveTo(13.5f, 4.134f, 16.634f, 1.0f, 20.5f, 1.0f)
                lineTo(21.5f, 1.0f)
                curveTo(22.0523f, 1.0f, 22.5f, 1.4477f, 22.5f, 2.0f)
                lineTo(22.5f, 20.0f)
                curveTo(22.5f, 21.6569f, 21.1569f, 23.0f, 19.5f, 23.0f)
                curveTo(17.8431f, 23.0f, 16.5f, 21.6569f, 16.5f, 20.0f)
                lineTo(16.5f, 15.0f)
                close()
                moveTo(15.5f, 8.0f)
                lineTo(15.5f, 12.0f)
                curveTo(15.5f, 12.5523f, 15.9477f, 13.0f, 16.5f, 13.0f)
                lineTo(17.5f, 13.0f)
                curveTo(18.0523f, 13.0f, 18.5f, 13.4477f, 18.5f, 14.0f)
                lineTo(18.5f, 20.0f)
                curveTo(18.5f, 20.5523f, 18.9477f, 21.0f, 19.5f, 21.0f)
                curveTo(20.0523f, 21.0f, 20.5f, 20.5523f, 20.5f, 20.0f)
                lineTo(20.5f, 3.0f)
                curveTo(17.7386f, 3.0f, 15.5f, 5.2386f, 15.5f, 8.0f)
                close()
                moveTo(5.5f, 1.0f)
                lineTo(8.5f, 1.0f)
                curveTo(10.7091f, 1.0f, 12.5f, 2.7909f, 12.5f, 5.0f)
                lineTo(12.5f, 8.5581f)
                curveTo(12.5f, 9.7733f, 11.9476f, 10.9225f, 10.9988f, 11.6816f)
                lineTo(10.0f, 12.4806f)
                lineTo(10.0f, 20.0f)
                curveTo(10.0f, 21.6569f, 8.6569f, 23.0f, 7.0f, 23.0f)
                curveTo(5.3431f, 23.0f, 4.0f, 21.6569f, 4.0f, 20.0f)
                lineTo(4.0f, 12.4806f)
                lineTo(3.0012f, 11.6816f)
                curveTo(2.0524f, 10.9225f, 1.5f, 9.7733f, 1.5f, 8.5581f)
                lineTo(1.5f, 5.0f)
                curveTo(1.5f, 2.7909f, 3.2909f, 1.0f, 5.5f, 1.0f)
                close()
            }
        }
            .build()
        return _food!!
    }

private var _food: ImageVector? = null
