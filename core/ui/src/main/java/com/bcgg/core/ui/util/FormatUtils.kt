package com.bcgg.core.ui.util

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bcgg.core.ui.R
import java.time.Duration
import kotlin.math.abs

@Composable
fun Duration.format(
    prefix: String = "",
    postfix: String = stringResource(id = R.string.duration_format_default_postfix)
) : String {
    val minutes = abs(seconds) / 60
    val result = StringBuilder(prefix)

    if(minutes <= 0) {
        result.append(stringResource(id = R.string.duration_format_less_than_minute))
        result.append(' ')
        result.append(postfix)

        return result.toString()
    }

    if(minutes >= 60) {
        result.append(minutes / 60)
        result.append(stringResource(id = R.string.duration_format_hour))
        result.append(' ')
    }

    if(minutes % 60 > 0) {
        result.append(minutes % 60)
        result.append(stringResource(id = R.string.duration_format_minute))
        result.append(' ')
    }

    result.append(postfix)

    return result.toString()
}