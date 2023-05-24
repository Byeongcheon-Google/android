package com.bcgg.feature.planeditor.compose.wss

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.User
import com.bcgg.core.ui.preview.PreviewContainer
import com.bcgg.core.ui.util.times

@Composable
fun ActiveUsers(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    activeUsers: List<User>
) {
    val defaultSizeModifier = Modifier.size(
        when (activeUsers.size) {
            1 -> size * 0.65
            2 -> size * 0.65
            else -> size * 0.55
        }
    )
    Box(
        modifier = modifier.size(size)
    ) {
        if (activeUsers.isNotEmpty()) {
            WssUser(
                modifier = defaultSizeModifier.align(
                    when (activeUsers.size) {
                        1 -> Alignment.Center
                        2 -> Alignment.CenterStart
                        3 -> Alignment.TopCenter
                        else -> Alignment.TopStart
                    }
                ),
                user = activeUsers[0],
                colorPosition = 0
            )

            if(activeUsers.size >= 2){
                WssUser(
                    modifier = defaultSizeModifier.align(
                        when (activeUsers.size) {
                            2 -> Alignment.CenterEnd
                            3 -> Alignment.BottomStart
                            else -> Alignment.TopEnd
                        }
                    ),
                    user = activeUsers[1],
                    colorPosition = 1
                )

                if(activeUsers.size >= 3) {
                    WssUser(
                        modifier = defaultSizeModifier.align(
                            when (activeUsers.size) {
                                3 -> Alignment.BottomEnd
                                else -> Alignment.BottomStart
                            }
                        ),
                        user = activeUsers[2],
                        colorPosition = 2
                    )

                    if(activeUsers.size == 4) {
                        WssUser(
                            modifier = defaultSizeModifier.align(
                                Alignment.BottomEnd
                            ),
                            user = activeUsers[3],
                            colorPosition = 3
                        )
                    } else if(activeUsers.size >= 5) {
                        WssUsers(
                            modifier = defaultSizeModifier.align(
                                Alignment.BottomEnd
                            ),
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
internal fun ActiveUsersPreview() {
    val testList = (0 until 5).map { User("User $it") }

    PreviewContainer {
        Row(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            for (i in 0 until 5) {
                ActiveUsers(activeUsers = testList.subList(0, i + 1))
            }
        }
    }
}