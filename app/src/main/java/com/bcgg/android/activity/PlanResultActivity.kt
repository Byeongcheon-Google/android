package com.bcgg.android.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.bcgg.feature.planresult.compose.PlanResultScreen
import com.bcgg.android.activity.contract.PlanResultActivityContract.Companion.PLAN_ID
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.planresult.viewmodel.PlanResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlanResultActivity : ComponentActivity() {

    @Inject
    lateinit var planResultViewModelFactory: PlanResultViewModel.Factory

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        val scheduleId = intent.extras?.getInt(PLAN_ID) ?: kotlin.run {
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            finish()
            0
        }

        val planResultViewModel by viewModels<PlanResultViewModel> {
            PlanResultViewModel.provideFactory(planResultViewModelFactory, scheduleId)
        }

        planResultViewModel.getPathFindResult()

        setContent {
            val planResultScreenUiState by planResultViewModel.planResultScreenUiState.collectAsState()
            val isLoading by planResultViewModel.isLoading.collectAsState()
            val snackbarHostState = remember {
                SnackbarHostState()
            }

            LaunchedEffect(key1 = Unit) {
                lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                    launch {
                        planResultViewModel.errorMessage.collectLatest {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
            }

            EdgeToEdge()

            AppTheme {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState)
                    }
                ) { paddingValues ->
                    if (isLoading) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .size(100.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                    if (planResultScreenUiState.dates.isNotEmpty()) {
                        PlanResultScreen(
                            modifier = Modifier,
                            scaffoldPaddingValues = paddingValues,
                            planResultViewModel = planResultViewModel
                        ) {
                            finish()
                        }
                    }
                }
            }
        }

    }
}