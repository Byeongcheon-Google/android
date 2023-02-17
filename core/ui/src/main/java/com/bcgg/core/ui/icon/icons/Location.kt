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

public val Icons.Location: ImageVector
    get() {
        if (_location != null) {
            return _location!!
        }
        _location = Builder(
            name = "Location", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(13.2729f, 23.2555f)
                curveTo(12.5339f, 23.8654f, 11.4661f, 23.8654f, 10.7271f, 23.2555f)
                curveTo(5.2769f, 18.7582f, 2.5f, 14.3495f, 2.5f, 10.0f)
                curveTo(2.5f, 4.7533f, 6.7533f, 0.5f, 12.0f, 0.5f)
                curveTo(17.2467f, 0.5f, 21.5f, 4.7533f, 21.5f, 10.0f)
                curveTo(21.5f, 14.3495f, 18.7231f, 18.7582f, 13.2729f, 23.2555f)
                close()
                moveTo(19.5f, 10.0f)
                curveTo(19.5f, 5.8579f, 16.1421f, 2.5f, 12.0f, 2.5f)
                curveTo(7.8579f, 2.5f, 4.5f, 5.8579f, 4.5f, 10.0f)
                curveTo(4.5f, 13.6444f, 6.9654f, 17.5585f, 12.0f, 21.7129f)
                curveTo(17.0346f, 17.5585f, 19.5f, 13.6444f, 19.5f, 10.0f)
                close()
                moveTo(12.0f, 13.0f)
                curveTo(9.7909f, 13.0f, 8.0f, 11.2091f, 8.0f, 9.0f)
                curveTo(8.0f, 6.7909f, 9.7909f, 5.0f, 12.0f, 5.0f)
                curveTo(14.2091f, 5.0f, 16.0f, 6.7909f, 16.0f, 9.0f)
                curveTo(16.0f, 11.2091f, 14.2091f, 13.0f, 12.0f, 13.0f)
                close()
                moveTo(12.0f, 11.0f)
                curveTo(13.1046f, 11.0f, 14.0f, 10.1046f, 14.0f, 9.0f)
                curveTo(14.0f, 7.8954f, 13.1046f, 7.0f, 12.0f, 7.0f)
                curveTo(10.8954f, 7.0f, 10.0f, 7.8954f, 10.0f, 9.0f)
                curveTo(10.0f, 10.1046f, 10.8954f, 11.0f, 12.0f, 11.0f)
                close()
            }
        }
            .build()
        return _location!!
    }

private var _location: ImageVector? = null
