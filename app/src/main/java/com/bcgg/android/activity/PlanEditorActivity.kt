package com.bcgg.android.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bcgg.core.ui.component.ProgressDialog
import com.bcgg.core.ui.provider.LocalFragmentManager
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.planeditor.R
import com.bcgg.android.activity.contract.PlanEditorContract.Companion.PLAN_ID
import com.bcgg.android.activity.contract.PlanResultActivityContract
import com.bcgg.feature.planeditor.compose.navigation.PlanEditorMapNavigation
import com.bcgg.feature.planeditor.compose.navigation.PlanEditorOptionsNavigation
import com.bcgg.feature.planeditor.compose.screen.PlanEditorMapScreen
import com.bcgg.feature.planeditor.compose.screen.PlanEditorScreenOptions
import com.bcgg.feature.planeditor.compose.state.initialPlanEditorOptionsUiStatePerDate
import com.bcgg.feature.planeditor.viewmodel.PlanEditorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class PlanEditorActivity : AppCompatActivity() {

    private val planResultActivityContract =
        registerForActivityResult(PlanResultActivityContract()) {

        }

    @Inject
    lateinit var planEditorViewModelFactory: PlanEditorViewModel.Factory

    var scheduleId = -1

    val planEditorViewModel by viewModels<PlanEditorViewModel> {
        PlanEditorViewModel.provideFactory(planEditorViewModelFactory, scheduleId)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        scheduleId = intent.extras?.getInt(PLAN_ID) ?: kotlin.run {
            Toast.makeText(this, "잘못된 접근입니다.", Toast.LENGTH_SHORT).show()
            finish()
            0
        }

        planEditorViewModel.initData(scheduleId)

        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }
            val navigationBarPaddingValues = WindowInsets.navigationBars.asPaddingValues()
            val isLoading by planEditorViewModel.isLoading.collectAsState()
            val optionsUiState by planEditorViewModel.optionsUiState.collectAsState()
            val optionsUiStatePerDates by planEditorViewModel.optionsUiStatePerDate.collectAsState()
            val mapUiState by planEditorViewModel.mapUiState.collectAsState()
            var confirmDialogMessage by rememberSaveable { mutableStateOf<String?>(null) }
            var showInviteDialog by rememberSaveable { mutableStateOf(false) }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val placeSearchResult = mapUiState.placeSearchResult

            val bottomBarCornerRadius by
                animateDpAsState(
                    targetValue =
                    if (
                        optionsUiStatePerDates[optionsUiState.selectedDate]?.aiAddressSearchResult?.isNotEmpty() == true
                        || mapUiState.placeSearchResultAi.isNotEmpty()
                        || placeSearchResult != null
                    ) {
                        0.dp
                    } else {
                        16.dp
                    }
                )

            val addresses by remember(optionsUiState, optionsUiStatePerDates[optionsUiState.selectedDate]) {
                derivedStateOf {
                    optionsUiStatePerDates[optionsUiState.selectedDate]?.searchResultMaps?.map { it.placeSearchResult.address } ?: emptyList()
                }
            }

            LaunchedEffect(key1 = Unit) {
                lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                    launch {
                        planEditorViewModel.errorMessage.collectLatest {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                    launch {
                        planEditorViewModel.showConfirmDialogEvent.collectLatest {
                            confirmDialogMessage = it
                        }
                    }
                    launch {
                        planEditorViewModel.showInviteeDialogEvent.collectLatest {
                            showInviteDialog = true
                        }
                    }
                }
            }

            LaunchedEffect(addresses) {
                planEditorViewModel.updateRecommendPlaceByAddress()
            }

            EdgeToEdge()

            AppTheme {
                ProgressDialog(show = isLoading)

                AnimatedVisibility(
                    visible = confirmDialogMessage != null,
                    exit = ExitTransition.None
                ) {
                    AlertDialog(onDismissRequest = { confirmDialogMessage = null }) {
                        if (confirmDialogMessage != null)
                            Column(
                                modifier = Modifier
                                    .clip(MaterialTheme.shapes.large)
                                    .background(MaterialTheme.colorScheme.surface)
                                    .padding(horizontal = 24.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    modifier = Modifier.padding(top = 16.dp),
                                    text = optionsUiState.name,
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = confirmDialogMessage ?: "",
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Row(
                                    modifier = Modifier.padding(top = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Spacer(modifier = Modifier.weight(1f))
                                    TextButton(
                                        modifier = Modifier.padding(8.dp),
                                        onClick = { confirmDialogMessage = null }) {
                                        Text("취소", color = MaterialTheme.colorScheme.secondary)
                                    }
                                    TextButton(
                                        modifier = Modifier.padding(8.dp),
                                        onClick = {
                                            planResultActivityContract.launch(scheduleId)
                                        }) {
                                        Text("확인", color = MaterialTheme.colorScheme.secondary)
                                    }
                                }
                            }
                    }
                }

                AnimatedVisibility(visible = showInviteDialog, exit = ExitTransition.None) {
                    AlertDialog(onDismissRequest = { showInviteDialog = false }) {
                        Column(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.surface)
                                .padding(horizontal = 24.dp, vertical = 8.dp)
                        ) {
                            var textFieldValue by rememberSaveable {
                                mutableStateOf("")
                            }
                            TextField(
                                modifier = Modifier.padding(top = 16.dp),
                                value = textFieldValue,
                                onValueChange = { textFieldValue = it },
                                label = {
                                    Text(
                                        text = "사용자 id로 초대",
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                },
                            )

                            Row(
                                modifier = Modifier,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                TextButton(
                                    modifier = Modifier.padding(8.dp),
                                    onClick = { showInviteDialog = false }) {
                                    Text("취소", color = MaterialTheme.colorScheme.secondary)
                                }
                                TextButton(
                                    modifier = Modifier.padding(8.dp),
                                    onClick = { planEditorViewModel.addCollaborator(textFieldValue) }) {
                                    Text("확인", color = MaterialTheme.colorScheme.secondary)
                                }
                            }
                        }
                    }
                }

                Scaffold(
                    bottomBar = {
                        Surface(
                            color = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clip(
                                MaterialTheme.shapes.large.copy(
                                    bottomEnd = CornerSize(0),
                                    bottomStart = CornerSize(0),
                                    topStart = CornerSize(bottomBarCornerRadius),
                                    topEnd = CornerSize(bottomBarCornerRadius)
                                )
                            ),
                            elevation = 16.dp
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                                    .height(56.dp)
                                    .selectableGroup(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            modifier = Modifier.padding(bottom = 8.dp),
                                            painter = painterResource(id = R.drawable.options),
                                            contentDescription = ""
                                        )
                                    },
                                    label = {
                                        Text("옵션")
                                    },
                                    selected = currentDestination?.hierarchy?.any { it.route == PlanEditorOptionsNavigation.id } == true,
                                    onClick = {
                                        navController.navigate(PlanEditorOptionsNavigation.id) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )

                                BottomNavigationItem(
                                    icon = {
                                        Icon(
                                            modifier = Modifier.padding(bottom = 8.dp),
                                            painter = painterResource(id = R.drawable.map),
                                            contentDescription = ""
                                        )
                                    },
                                    label = {
                                        Text("지도 보기")
                                    },
                                    selected = currentDestination?.hierarchy?.any { it.route == PlanEditorMapNavigation.id } == true,
                                    onClick = {
                                        navController.navigate(PlanEditorMapNavigation.id) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }

                    },
                    snackbarHost = {
                        SnackbarHost(
                            modifier = Modifier.imePadding(),
                            hostState = snackbarHostState
                        )
                    },
                ) { scaffoldPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = PlanEditorOptionsNavigation.id
                    ) {
                        composable(PlanEditorOptionsNavigation.id) {
                            CompositionLocalProvider(LocalFragmentManager provides supportFragmentManager) {
                                PlanEditorScreenOptions(
                                    planEditorViewModel = planEditorViewModel,
                                    scaffoldPaddingValues = scaffoldPadding,
                                    onBack = {
                                        finish()
                                    },
                                    optionsUiState = optionsUiState,
                                    optionsUiStatePerDates = optionsUiStatePerDates,
                                    navController = navController
                                )
                            }
                        }
                        composable(PlanEditorMapNavigation.id) {
                            PlanEditorMapScreen(
                                snackbarHostState = snackbarHostState,
                                planEditorViewModel = planEditorViewModel,
                                mapPadding = scaffoldPadding,
                                onBack = {
                                    navController.navigate(PlanEditorOptionsNavigation.id) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                optionsUiState = optionsUiState,
                                optionsUiStatePerDate = optionsUiStatePerDates[optionsUiState.selectedDate] ?: initialPlanEditorOptionsUiStatePerDate,
                                mapUiState = mapUiState
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        planEditorViewModel.closeChat()
    }

    override fun onRestart() {
        super.onRestart()
        planEditorViewModel.newChatRepository()
    }
}