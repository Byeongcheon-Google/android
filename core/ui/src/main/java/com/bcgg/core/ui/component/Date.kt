package com.bcgg.core.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.theme.Shapes
import com.bcgg.core.util.date.monthDayFormatter
import com.bcgg.core.util.date.weekdayFormatter
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun DateItem(
    modifier: Modifier = Modifier,
    date: LocalDate,
    isSelected: Boolean,
    isValid: Boolean,
    onClick: ((LocalDate) -> Unit)? = null
) {
    val backgroundColor = when {
        isSelected -> MaterialTheme.colorScheme.surfaceColorAtElevation(16.dp)
        isValid -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
        else -> Color.Transparent
    }

    Column(
        modifier = modifier
            .size(52.dp)
            .clip(Shapes.medium)
            .background(backgroundColor)
            .clickable(
                enabled = onClick != null
            ) {
                if (onClick != null) {
                    onClick(date)
                }
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        CompositionLocalProvider(
            LocalContentColor provides when (date.dayOfWeek) {
                DayOfWeek.SUNDAY -> MaterialTheme.colorScheme.error
                DayOfWeek.SATURDAY -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurface
            }
        ) {
            Text(
                modifier = Modifier.alpha(if (isValid) 1f else 0.5f),
                text = date.format(monthDayFormatter),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                modifier = Modifier.alpha(if (isValid) 1f else 0.5f),
                text = date.format(weekdayFormatter),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}