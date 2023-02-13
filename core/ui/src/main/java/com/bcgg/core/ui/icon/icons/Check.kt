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

public val Icons.Check: ImageVector
    get() {
        if (_check != null) {
            return _check!!
        }
        _check = Builder(
            name = "Check", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(20.4591f, 4.977f)
                lineTo(9.2629f, 17.0528f)
                curveTo(8.8874f, 17.4578f, 8.2547f, 17.4817f, 7.8497f, 17.1062f)
                curveTo(7.8207f, 17.0793f, 7.7932f, 17.0506f, 7.7676f, 17.0204f)
                lineTo(3.5696f, 12.0804f)
                curveTo(3.212f, 11.6595f, 2.5809f, 11.6083f, 2.16f, 11.9659f)
                curveTo(1.7392f, 12.3235f, 1.6879f, 12.9546f, 2.0456f, 13.3755f)
                lineTo(6.2435f, 18.3155f)
                curveTo(6.3205f, 18.4061f, 6.4028f, 18.492f, 6.4899f, 18.5728f)
                curveTo(7.7049f, 19.6993f, 9.603f, 19.6276f, 10.7295f, 18.4126f)
                lineTo(21.9257f, 6.3368f)
                curveTo(22.3012f, 5.9318f, 22.2773f, 5.2991f, 21.8723f, 4.9236f)
                curveTo(21.4673f, 4.5481f, 20.8346f, 4.572f, 20.4591f, 4.977f)
                close()
            }
        }
            .build()
        return _check!!
    }

private var _check: ImageVector? = null
