package com.bcgg.feature.planeditor.compose.editor

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.component.DateItem
import com.bcgg.core.ui.theme.AppTheme
import java.time.LocalDate

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun EditorDate(
    selectedDate: LocalDate,
    dates: Set<LocalDate>,
    dateLength: Int = 30,
    expanded: Boolean,
    onDateClick: (LocalDate) -> Unit
) {
    val localDensity = LocalDensity.current
    val listState = rememberLazyListState()
    var scrollOffset by remember { mutableStateOf(0) }

    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        AnimatedVisibility(
            visible = expanded,
            enter = expandHorizontally() + fadeIn(),
            exit = shrinkHorizontally() + fadeOut()
        ) {
            LazyRow(
                modifier = Modifier.onGloballyPositioned {
                    scrollOffset = -it.size.width / 2 + with(localDensity) { 34.dp.roundToPx() }
                },
                state = listState,
                contentPadding = PaddingValues(all = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(dateLength, key = { it }) {
                    val date = selectedDate.minusDays((dateLength - it).toLong())
                    DateItem(
                        date = date,
                        isSelected = false,
                        isValid = date in dates,
                        onClick = {
                            onDateClick(it)
                        }
                    )
                }
                items(1, key = { it + dateLength }) {
                    DateItem(
                        date = selectedDate,
                        isSelected = true,
                        isValid = selectedDate in dates,
                        onClick = {
                            onDateClick(it)
                        }
                    )
                }
                items(dateLength, key = { it + dateLength + 1 }) {
                    val date = selectedDate.plusDays((it + 1).toLong())
                    DateItem(
                        date = date,
                        isSelected = false,
                        isValid = date in dates,
                        onClick = {
                            onDateClick(it)
                        }
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = !expanded,
            enter = expandHorizontally() + fadeIn(),
            exit = shrinkHorizontally() + fadeOut()
        ) {
            DateItem(
                modifier = Modifier.padding(top = 8.dp, start = 8.dp, end = 0.dp, bottom = 8.dp),
                date = selectedDate,
                isSelected = true,
                isValid = selectedDate in dates
            )
        }
    }

    LaunchedEffect(key1 = selectedDate) {
        listState.scrollToItem(dateLength, scrollOffset)
    }
}



@Preview
@Composable
fun EditorDateItemPreview() {
    Column {
        listOf(false, true).map {
            AppTheme(useDarkTheme = it) {
                Row(
                    modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                ) {
                    listOf(false to false, false to true, true to false, true to true).map {
                        DateItem(
                            date = LocalDate.now(),
                            isSelected = it.first,
                            isValid = it.second,
                            onClick = {}
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EditorDatePreview() {
    var selectedDate by remember {
        mutableStateOf(LocalDate.now().plusDays(1))
    }
    var expanded by remember {
        mutableStateOf(true)
    }
    Column {
        listOf(false, true).map {
            AppTheme(useDarkTheme = it) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface),
                ) {
                    EditorDate(
                        selectedDate = selectedDate,
                        dates = setOf(LocalDate.now(), LocalDate.now().plusDays(1)),
                        expanded = expanded,
                        onDateClick = { selectedDate = it }
                    )
                }
            }
        }
        Button(onClick = { expanded = !expanded }) {
            Text(text = "Toggle expanded")
        }
    }
}
