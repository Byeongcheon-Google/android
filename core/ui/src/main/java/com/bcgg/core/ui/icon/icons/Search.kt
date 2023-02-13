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

public val Icons.Search: ImageVector
    get() {
        if (_search != null) {
            return _search!!
        }
        _search = Builder(
            name = "Search", defaultWidth = 24.0.dp, defaultHeight = 24.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = NonZero
            ) {
                moveTo(17.8133f, 16.3991f)
                lineTo(22.4105f, 20.9963f)
                curveTo(22.8011f, 21.3868f, 22.8011f, 22.02f, 22.4105f, 22.4105f)
                curveTo(22.02f, 22.8011f, 21.3868f, 22.8011f, 20.9963f, 22.4105f)
                lineTo(16.3991f, 17.8133f)
                curveTo(14.8594f, 19.0449f, 12.9064f, 19.7814f, 10.7814f, 19.7814f)
                curveTo(5.8108f, 19.7814f, 1.7814f, 15.752f, 1.7814f, 10.7814f)
                curveTo(1.7814f, 5.8108f, 5.8108f, 1.7814f, 10.7814f, 1.7814f)
                curveTo(15.752f, 1.7814f, 19.7814f, 5.8108f, 19.7814f, 10.7814f)
                curveTo(19.7814f, 12.9064f, 19.0449f, 14.8594f, 17.8133f, 16.3991f)
                lineTo(17.8133f, 16.3991f)
                close()
                moveTo(10.7814f, 17.7814f)
                curveTo(14.6474f, 17.7814f, 17.7814f, 14.6474f, 17.7814f, 10.7814f)
                curveTo(17.7814f, 6.9154f, 14.6474f, 3.7814f, 10.7814f, 3.7814f)
                curveTo(6.9154f, 3.7814f, 3.7814f, 6.9154f, 3.7814f, 10.7814f)
                curveTo(3.7814f, 14.6474f, 6.9154f, 17.7814f, 10.7814f, 17.7814f)
                close()
            }
        }
            .build()
        return _search!!
    }

private var _search: ImageVector? = null
