package com.bcgg.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.android.activity.contract.PlanEditorContract
import com.bcgg.feature.planmanage.ui.PlanManageScreen
import com.bcgg.feature.planmanage.viewmodel.PlanManageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlanManageActivity : ComponentActivity() {
    private val planManageViewModel by viewModels<PlanManageViewModel>()
    private val planEditorContract = registerForActivityResult(PlanEditorContract()) {
        planManageViewModel.getSchedules()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            EdgeToEdge()

            AppTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) {
                    PlanManageScreen(
                        modifier = Modifier.padding(it),
                        viewModel = planManageViewModel,
                        snackBarHostState = snackbarHostState,
                        onEditPlan = { planEditorContract.launch(it) }
                    )
                }
            }
        }
    }
}