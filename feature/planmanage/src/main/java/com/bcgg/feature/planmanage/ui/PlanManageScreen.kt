package com.bcgg.feature.planmanage.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Add
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.theme.divider
import com.bcgg.feature.planmanage.state.PlanItemUiState
import com.bcgg.feature.planmanage.viewmodel.PlanManageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlanManageScreen(
    modifier: Modifier = Modifier,
    snackBarHostState: SnackbarHostState,
    viewModel: PlanManageViewModel = hiltViewModel(),
    onNewPlan: () -> Unit,
    onEditPlan: (planId: Int) -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                viewModel.planItemRemovedEvent.collectLatest { (position, item) ->
                    coroutineScope.launch {
                        val result = snackBarHostState.showSnackbar(
                            message = "삭제되었습니다",
                            actionLabel = "취소",
                            duration = SnackbarDuration.Long
                        )
                        if (result == SnackbarResult.ActionPerformed) {
                            viewModel.onPlanItemRemoveUndo(position, item)
                        }
                    }
                }
            }
            launch {
                viewModel.planItemClickedEvent.collectLatest { (position, item) ->
                    onEditPlan(item.id)
                }
            }
            launch {
                viewModel.addButtonClickedEvent.collectLatest {
                    onNewPlan()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            PlanManageScreenTopAppBar()
        },
        floatingActionButton = {
            PlanManageScreenFab(onNewPlan)
        }
    ) {
        if(uiState.value.plans.isEmpty()) {
            EmptyPlans()
        } else {
            LazyColumn(
                modifier = modifier.padding(it)
            ) {
                val plans = uiState.value.plans

                items(plans.size, key = { index -> plans[index].id }) { position ->
                    plans[position].let {
                        PlanManageItem(
                            modifier = Modifier.animateItemPlacement(),
                            item = it,
                            onClick = { viewModel.onPlanItemClick(position, it) },
                            onDelete = {
                                viewModel.onPlanItemRemove(position, it)
                            }
                        )
                        if (position < plans.lastIndex) {
                            Divider(
                                modifier = Modifier.padding(horizontal = 12.dp),
                                color = MaterialTheme.colorScheme.divider
                            )
                        }
                    }
                }
            }
        }
    }
}
