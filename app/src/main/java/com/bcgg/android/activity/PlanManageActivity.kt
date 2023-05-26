package com.bcgg.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.bcgg.core.ui.provider.LocalActivity
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.planeditor.activity.PlanEditorContract
import com.bcgg.feature.planmanage.ui.PlanManageScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanManageActivity : ComponentActivity() {
    private val planEditorContract = registerForActivityResult(PlanEditorContract()) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            EdgeToEdge()

            AppTheme {
                PlanManageScreen(
                    snackBarHostState = snackbarHostState,
                    onNewPlan = { planEditorContract.launch(null) },
                    onEditPlan = { planEditorContract.launch(it) }
                )
            }
        }
    }
}