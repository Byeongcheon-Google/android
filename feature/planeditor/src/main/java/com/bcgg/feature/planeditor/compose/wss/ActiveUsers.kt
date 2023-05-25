package com.bcgg.feature.planeditor.compose.wss

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.User
import com.bcgg.core.ui.preview.PreviewContainer
import com.bcgg.core.ui.util.animateAlignmentAsState
import com.bcgg.core.ui.util.times
import kotlinx.coroutines.delay

@Composable
fun ActiveUsers(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    activeUserCount: Int
) {
    val defaultSize = when (activeUserCount) {
        1 -> size * 0.65
        2 -> size * 0.65
        else -> size * 0.55
    }

    val userAlignments = listOf(
        animateAlignmentAsState(
            when (activeUserCount) {
                1 -> Alignment.Center
                2 -> Alignment.CenterStart
                3 -> Alignment.TopCenter
                else -> Alignment.TopStart
            }
        ),
        animateAlignmentAsState(
            when (activeUserCount) {
                1 -> Alignment.Center
                2 -> Alignment.CenterEnd
                3 -> Alignment.BottomStart
                else -> Alignment.TopEnd
            }
        ),
        animateAlignmentAsState(
            when (activeUserCount) {
                1 -> Alignment.Center
                2 -> Alignment.Center
                3 -> Alignment.BottomEnd
                else -> Alignment.BottomStart
            }
        ),
        animateAlignmentAsState(
            when (activeUserCount) {
                1 -> Alignment.Center
                2 -> Alignment.Center
                3 -> Alignment.Center
                else -> Alignment.BottomEnd
            }
        )
    )

    val userSizes = listOf(
        animateDpAsState(if (activeUserCount >= 1) defaultSize else 0.dp),
        animateDpAsState(if (activeUserCount >= 2) defaultSize else 0.dp),
        animateDpAsState(if (activeUserCount >= 3) defaultSize else 0.dp),
        animateDpAsState(if (activeUserCount == 4) defaultSize else 0.dp),
    )

    val userOver5Size = animateDpAsState(if (activeUserCount >= 5) defaultSize else 0.dp)

    Box(
        modifier = modifier.size(size)
    ) {
        if (activeUserCount > 0) {
            repeat(4) {
                if (it < 3) {
                    WssUser(
                        modifier = Modifier
                            .size(userSizes[it].value)
                            .align(userAlignments[it].value),
                        colorPosition = it
                    )
                } else {
                    WssUser(
                        modifier = Modifier
                            .size(userSizes[3].value)
                            .align(userAlignments[3].value),
                        colorPosition = 3
                    )
                    WssUsers(
                        modifier = Modifier
                            .size(userOver5Size.value)
                            .align(userAlignments[3].value),
                        colorPosition = it
                    )

                }

            }
        }
    }
}

@Composable
@Preview
internal fun ActiveUsersPreview() {
    var activeUserCount by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)

            activeUserCount = (activeUserCount + 1) % 5
        }
    }

    PreviewContainer {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            ActiveUsers(activeUserCount = activeUserCount + 1)
        }
    }
}