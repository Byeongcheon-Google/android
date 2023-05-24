package com.bcgg.feature.planeditor.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bcgg.core.ui.component.ProgressDialog
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.planeditor.R
import com.bcgg.feature.planeditor.compose.navigation.PlanEditorMapNavigation
import com.bcgg.feature.planeditor.compose.navigation.PlanEditorOptionsNavigation
import com.bcgg.feature.planeditor.compose.screen.PlanEditorMapScreen
import com.bcgg.feature.planeditor.compose.screen.PlanEditorScreenOptions
import com.bcgg.feature.planeditor.viewmodel.PlanEditorViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlanEditorActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            val planEditorViewModel = hiltViewModel<PlanEditorViewModel>()
            val navigationBarPaddingValues = WindowInsets.navigationBars.asPaddingValues()
            val isLoading by planEditorViewModel.isLoading.collectAsState()
            val optionsUiState by planEditorViewModel.optionsUiState.collectAsState()
            var confirmDialogMessage by rememberSaveable { mutableStateOf<String?>(null) }

            AnimatedVisibility(visible = confirmDialogMessage != null, exit = ExitTransition.None) {
                AlertDialog(onDismissRequest = { confirmDialogMessage = null }) {
                    if (confirmDialogMessage != null)
                        Column(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.background)
                                .padding(horizontal = 24.dp, vertical = 8.dp)
                        ) {
                            Text(
                                modifier = Modifier.padding(top = 16.dp),
                                text = optionsUiState.name,
                                style = MaterialTheme.typography.headlineSmall
                            )
                            Text(text = confirmDialogMessage ?: "")

                            Row(
                                modifier = Modifier.padding(top = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Spacer(modifier = Modifier.weight(1f))
                                TextButton(
                                    modifier = Modifier.padding(8.dp),
                                    onClick = { confirmDialogMessage = null }) {
                                    Text("취소")
                                }
                                TextButton(
                                    modifier = Modifier.padding(8.dp),
                                    onClick = { /*TODO*/ }) {
                                    Text("확인")
                                }
                            }
                        }
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
                }
            }

            ProgressDialog(show = isLoading)

            EdgeToEdge()

            AppTheme {
                Scaffold(
                    bottomBar = {
                        Surface(
                            color = MaterialTheme.colorScheme.background,
                            contentColor = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clip(
                                MaterialTheme.shapes.large.copy(
                                    bottomEnd = CornerSize(0),
                                    bottomStart = CornerSize(0)
                                )
                            ),
                            elevation = 16.dp
                        ) {
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(navigationBarPaddingValues)
                                    .padding(vertical = 16.dp)
                                    .height(56.dp)
                                    .selectableGroup(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                val navBackStackEntry by navController.currentBackStackEntryAsState()
                                val currentDestination = navBackStackEntry?.destination

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
                            hostState = snackbarHostState
                        )
                    },
                ) { scaffoldPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = PlanEditorOptionsNavigation.id
                    ) {
                        composable(PlanEditorOptionsNavigation.id) {
                            PlanEditorScreenOptions(
                                planEditorViewModel = planEditorViewModel,
                                scaffoldPaddingValues = scaffoldPadding
                            )
                        }
                        composable(PlanEditorMapNavigation.id) {
                            PlanEditorMapScreen(
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
                                })
                        }
                    }
                }
            }
        }
    }
}