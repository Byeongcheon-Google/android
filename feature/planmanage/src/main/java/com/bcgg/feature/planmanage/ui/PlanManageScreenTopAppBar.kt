package com.bcgg.feature.planmanage.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanManageScreenTopAppBar() {
    TopAppBar(
        title = {
            Text("플랜 관리")
        }
    )
}