package com.bcgg.core.ui.icon

import androidx.compose.ui.graphics.vector.ImageVector
import com.bcgg.core.ui.icon.icons.Add
import com.bcgg.core.ui.icon.icons.Arrowdown
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.icon.icons.Arrowleftdown
import com.bcgg.core.ui.icon.icons.Arrowleftup
import com.bcgg.core.ui.icon.icons.Arrowright
import com.bcgg.core.ui.icon.icons.Arrowrightdown
import com.bcgg.core.ui.icon.icons.Arrowrightup
import com.bcgg.core.ui.icon.icons.Arrowup
import com.bcgg.core.ui.icon.icons.Check
import com.bcgg.core.ui.icon.icons.Food
import com.bcgg.core.ui.icon.icons.Home
import com.bcgg.core.ui.icon.icons.Rocketfly
import com.bcgg.core.ui.icon.icons.Search
import com.bcgg.core.util.detekt.IgnoreDetektNaming
import kotlin.collections.List as ____KtList

@IgnoreDetektNaming
public object Icons

@IgnoreDetektNaming
private var __Icons: ____KtList<ImageVector>? = null

@IgnoreDetektNaming
public val Icons.Icons: ____KtList<ImageVector>
    get() {
        if (__Icons != null) {
            return __Icons!!
        }
        __Icons = listOf(
            Search, Arrowdown, Home, Arrowleftup, Arrowright, Arrowleftdown, Check, Add,
            Arrowrightup, Arrowrightdown, Arrowup, Rocketfly, Arrowleft, Food
        )
        return __Icons!!
    }
