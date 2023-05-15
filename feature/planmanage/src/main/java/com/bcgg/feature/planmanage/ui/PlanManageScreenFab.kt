package com.bcgg.feature.planmanage.ui

import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Add

@Composable
fun PlanManageScreenFab(onAddButtonClick: () -> Unit) {
    FloatingActionButton(onClick = onAddButtonClick) {
        Icon(imageVector = Icons.Add, contentDescription = "새 여행 계획 생성")
    }
}