package com.bcgg.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcgg.core.ui.compositionlocal.LocalScaffoldPaddingValues
import com.bcgg.core.ui.provider.LocalActivity
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.planmanage.navigation.PlanManageScreenNavigation
import com.bcgg.feature.planmanage.ui.PlanManageScreen
import com.bcgg.feature.ui.login.navigation.LoginScreenNavigation
import com.bcgg.feature.ui.signup.navigation.SignUpScreenNavigation
import com.bcgg.feature.ui.login.ui.LoginScreen
import com.bcgg.feature.ui.signup.ui.SignUpScreen
import com.bcgg.feature.ui.signup.ui.SignUpTopAppBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberNavController()
            val snackbarHostState = remember { SnackbarHostState() }

            var signupCompleteId by rememberSaveable { mutableStateOf<String?>(null) }

            EdgeToEdge()
            CompositionLocalProvider(LocalActivity provides this) {
                AppTheme {
                    NavHost(
                        navController = navController,
                        startDestination = LoginScreenNavigation.id
                    ) {
                        composable(LoginScreenNavigation.id) {
                            LoginScreen(
                                snackbarHostState = snackbarHostState,
                                onSignUpButtonClick = {
                                    signupCompleteId = null
                                    navController.navigate(SignUpScreenNavigation.id) {
                                        popUpTo(LoginScreenNavigation.id)
                                    }
                                },
                                onFindPasswordButtonClick = {

                                },
                                signUpCompletedId = signupCompleteId,
                                onLoginCompleted = {
                                    navController.navigate(PlanManageScreenNavigation.id) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            inclusive = true
                                        }
                                    }
                                }
                            )
                        }
                        composable(SignUpScreenNavigation.id) {
                            SignUpScreen(
                                snackbarHostState = snackbarHostState,
                                onSignupCompleted = {
                                    navController.popBackStack()
                                    signupCompleteId = it
                                },
                                onBack = {
                                    navController.popBackStack()
                                }
                            )
                        }
                        composable(PlanManageScreenNavigation.id) {
                            PlanManageScreen(
                                snackBarHostState = snackbarHostState,
                                onNewPlan = { /*TODO*/ },
                                onEditPlan = {

                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        Greeting("Android")
    }
}
