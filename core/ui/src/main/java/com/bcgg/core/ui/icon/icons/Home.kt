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

public val Icons.Home: ImageVector
    get() {
        if (_home != null) {
            return _home!!
        }
        _home = Builder(
            name = "Home", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(16.0f, 20.5f)
                lineTo(18.0f, 20.5f)
                curveTo(19.1046f, 20.5f, 20.0f, 19.6046f, 20.0f, 18.5f)
                lineTo(20.0f, 9.9079f)
                curveTo(20.0f, 9.2704f, 19.6961f, 8.6711f, 19.1818f, 8.2944f)
                lineTo(13.1818f, 3.8996f)
                curveTo(12.4782f, 3.3843f, 11.5218f, 3.3843f, 10.8182f, 3.8996f)
                lineTo(4.8182f, 8.2944f)
                curveTo(4.3039f, 8.6711f, 4.0f, 9.2704f, 4.0f, 9.9079f)
                lineTo(4.0f, 18.5f)
                curveTo(4.0f, 19.6046f, 4.8954f, 20.5f, 6.0f, 20.5f)
                lineTo(8.0f, 20.5f)
                lineTo(8.0f, 12.5f)
                curveTo(8.0f, 11.9477f, 8.4477f, 11.5f, 9.0f, 11.5f)
                lineTo(15.0f, 11.5f)
                curveTo(15.5523f, 11.5f, 16.0f, 11.9477f, 16.0f, 12.5f)
                lineTo(16.0f, 20.5f)
                close()
                moveTo(14.0f, 20.5f)
                lineTo(14.0f, 13.5f)
                lineTo(10.0f, 13.5f)
                lineTo(10.0f, 20.5f)
                lineTo(14.0f, 20.5f)
                close()
                moveTo(3.6364f, 6.6809f)
                lineTo(9.6364f, 2.2862f)
                curveTo(11.0436f, 1.2555f, 12.9564f, 1.2555f, 14.3636f, 2.2862f)
                lineTo(20.3636f, 6.6809f)
                curveTo(21.3922f, 7.4343f, 22.0f, 8.6329f, 22.0f, 9.9079f)
                lineTo(22.0f, 18.5f)
                curveTo(22.0f, 20.7091f, 20.2091f, 22.5f, 18.0f, 22.5f)
                lineTo(6.0f, 22.5f)
                curveTo(3.7909f, 22.5f, 2.0f, 20.7091f, 2.0f, 18.5f)
                lineTo(2.0f, 9.9079f)
                curveTo(2.0f, 8.6329f, 2.6078f, 7.4343f, 3.6364f, 6.6809f)
                close()
            }
        }
            .build()
        return _home!!
    }

private var _home: ImageVector? = null
