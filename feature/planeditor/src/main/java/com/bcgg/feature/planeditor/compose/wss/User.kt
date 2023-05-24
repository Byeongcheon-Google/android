package com.bcgg.feature.planeditor.compose.wss

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bcgg.core.domain.model.User
import com.bcgg.core.ui.provider.LocalMarkerColors
import com.bcgg.feature.planeditor.R

@Composable
fun WssUser(
    modifier: Modifier = Modifier,
    user: User,
    colorPosition: Int = -1,
) {
    val foregroundColor =
        if (colorPosition == -1) {
            MaterialTheme.colorScheme.secondary
        } else {
            LocalMarkerColors.current.colors[colorPosition]
        }

    val backgroundColor =
        if (colorPosition == -1) {
            MaterialTheme.colorScheme.background
        } else {
            MaterialTheme.colorScheme.background
        }

    val strokeColor =
        if (colorPosition == -1) {
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        } else {
            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        }

    Icon(
        modifier = modifier.clip(CircleShape).background(backgroundColor).border(width = 0.35.dp, color = strokeColor, shape = CircleShape),
        painter = painterResource(id = R.drawable.person),
        contentDescription = user.userId,
        tint = foregroundColor
    )
}

@Composable
fun WssUsers(
    modifier: Modifier = Modifier,
    colorPosition: Int = -1,
) {
    val foregroundColor =
        if (colorPosition == -1) {
            MaterialTheme.colorScheme.secondary
        } else {
            LocalMarkerColors.current.colors[colorPosition]
        }

    val backgroundColor =
        if (colorPosition == -1) {
            MaterialTheme.colorScheme.secondaryContainer
        } else {
            MaterialTheme.colorScheme.primaryContainer
        }

    Icon(
        modifier = modifier.clip(CircleShape).background(backgroundColor),
        painter = painterResource(id = R.drawable.people),
        contentDescription = "users",
        tint = foregroundColor
    )
}