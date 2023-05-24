package com.bcgg.feature.planeditor.compose.mealtime

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.icon.Icons
import com.bcgg.feature.planeditor.compose.picker.TimePicker
import java.time.LocalTime

fun LazyListScope.mealTimes(
    mealTimes: List<LocalTime>,
    onChange: (List<LocalTime>) -> Unit
) {
    item {
        Text(
            text = "식사 시간",
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }

    items(count = mealTimes.count(), key = { position -> mealTimes[position] }) { position ->
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TimePicker(
                modifier = Modifier.weight(1f),
                label = "식사 시간 ${position + 1}",
                value = mealTimes[position],
                onValueChange = {
                    onChange(mealTimes.mapIndexed { index, localTime -> if (position == index) it else localTime })
                })
            IconButton(
                modifier = Modifier.padding(start = 16.dp),
                onClick = { onChange(mealTimes.filterIndexed { index, localTime -> position != index }) }) {
                Icon(
                    modifier = Modifier.padding(8.dp),
                    imageVector = androidx.compose.material.icons.Icons.Rounded.Clear,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (mealTimes.isEmpty()) {
        item {
            Text(
                text = "식사 시간을 설정하지 않았습니다",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.surfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }

    item {
        Text(
            text = "식사 시간 추가",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onChange(
                        mealTimes.plusElement(
                            mealTimes
                                .lastOrNull()
                                ?.plusHours(1) ?: LocalTime.of(12, 0)
                        )
                    )
                }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}