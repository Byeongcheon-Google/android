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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Add
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.theme.divider
import com.bcgg.feature.planmanage.state.PlanItemUiState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PlanManageScreen(
    snackBarHostState: SnackbarHostState,
    plans: List<PlanItemUiState>,
    onAddButtonClick: () -> Unit,
    onPlanItemClick: (position: Int, planItemUiState: PlanItemUiState) -> Unit,
    onPlanItemRemove: (position: Int, planItemUiState: PlanItemUiState) -> Unit,
    onPlanItemRemoveUndo: (position: Int, planItemUiState: PlanItemUiState) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    AppTheme {
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopAppBar(
                    title = {
                        Text("플랜 관리")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = onAddButtonClick) {
                    Icon(imageVector = Icons.Add, contentDescription = "새 여행 계획 생성")
                }
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
            ) {
                items(plans.size, key = { index -> plans[index].id }) { position ->
                    plans[position].let {
                        PlanManageItem(
                            modifier = Modifier.animateItemPlacement(),
                            item = it,
                            onClick = { onPlanItemClick(position, it) },
                            onDelete = {
                                coroutineScope.launch {
                                    val result = snackBarHostState.showSnackbar(
                                        message = "삭제되었습니다",
                                        actionLabel = "취소",
                                        duration = SnackbarDuration.Long
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        onPlanItemRemoveUndo(position, it)
                                    }
                                }
                                onPlanItemRemove(position, it)
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

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
internal fun PlanManageScreenPreview() {
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    var list by remember {
        mutableStateOf(
            List(10) {
                PlanItemUiState(
                    id = it,
                    title = "test $it",
                    modifiedDateTime = LocalDateTime.now().minusDays(it.toLong()),
                    date = LocalDate.now().minusDays(it.toLong())..LocalDate.now()
                        .minusDays(it.toLong()).plusDays(2),
                    destinations = List(it * 2) {
                        "test destination $it"
                    }
                )
            }
        )
    }

    PlanManageScreen(
        plans = list,
        snackBarHostState = snackbarHostState,
        onAddButtonClick = { },
        onPlanItemClick = { _, _ -> },
        onPlanItemRemove = { _, item ->
            list = list.filter { it != item }
        },
        onPlanItemRemoveUndo = { position, item ->
            val mutableList = list.toMutableList()
            mutableList.add(position, item)
            list = mutableList
        }
    )
}
