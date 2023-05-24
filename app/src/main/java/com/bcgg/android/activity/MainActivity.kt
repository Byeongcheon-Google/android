package com.bcgg.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bcgg.core.ui.provider.LocalActivity
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.planeditor.activity.PlanEditorContract
import com.bcgg.feature.planeditor.compose.screen.PlanEditorMapScreen
import com.bcgg.feature.planeditor.navigation.PlanEditorScreenNavigation
import com.bcgg.feature.planmanage.navigation.PlanManageScreenNavigation
import com.bcgg.feature.planmanage.ui.PlanManageScreen
import com.bcgg.feature.ui.login.navigation.LoginScreenNavigation
import com.bcgg.feature.ui.signup.navigation.SignUpScreenNavigation
import com.bcgg.feature.ui.login.ui.LoginScreen
import com.bcgg.feature.ui.signup.ui.SignUpScreen
import com.bcgg.splash.compose.SplashScreen
import com.bcgg.splash.navigation.SplashScreenNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val planEditorContract = registerForActivityResult(PlanEditorContract()) {}

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
                        startDestination = SplashScreenNavigation.id
                    ) {
                        composable(SplashScreenNavigation.id) {
                            SplashScreen(
                                onAutoLoginSuccess = {
                                    navController.navigate(PlanManageScreenNavigation.id) {
                                        popUpTo(SplashScreenNavigation.id)
                                    }
                                },
                                onAutoLoginFailure = {
                                    navController.navigate(LoginScreenNavigation.id) {
                                        popUpTo(SplashScreenNavigation.id)
                                    }
                                }
                            )
                        }
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
                                        popUpTo(LoginScreenNavigation.id) {
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
                                onNewPlan = { planEditorContract.launch(null) },
                                onEditPlan = { planEditorContract.launch(it) }
                            )
                        }
                        composable(PlanEditorScreenNavigation.id) {
                            PlanEditorMapScreen(
                                mapPadding = PaddingValues(),
                                onBack = {
                                    navController.popBackStack()
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
