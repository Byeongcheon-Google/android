package com.bcgg.android.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.bcgg.android.activity.contract.PlanManageActivityContract
import com.bcgg.android.activity.contract.SignupContract
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.feature.ui.login.ui.LoginScreen
import com.bcgg.feature.ui.login.viewmodel.LoginViewModel
import com.bcgg.splash.compose.SplashScreen
import com.bcgg.splash.viewmodel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val splashViewModel: SplashViewModel by viewModels()

    private val signupContract = registerForActivityResult(SignupContract()) {
        if (it != null) {
            loginViewModel.showSignUpCompletedToastMessage()
            loginViewModel.setId(it)
        }
    }
    private val planManageActivityContract =
        registerForActivityResult(PlanManageActivityContract()) {}

    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }
            var showLoginScreen by rememberSaveable { mutableStateOf<Boolean?>(null) }

            val tokenIsValid by splashViewModel.tokenIsValid.collectAsState()

            LaunchedEffect(tokenIsValid) {
                showLoginScreen = tokenIsValid == true
                if (tokenIsValid == true) {
                    planManageActivityContract.launch(Unit)
                    finish()
                }
            }

            LaunchedEffect(Unit) {
                lifecycle.repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                    launch {
                        loginViewModel.toastMessage.collectLatest {
                            snackbarHostState.showSnackbar(it)
                        }
                    }
                }
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarHostState)
                }
            ) {
                AnimatedVisibility(
                    visible = showLoginScreen == null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    SplashScreen()
                }

                AnimatedVisibility(
                    visible = showLoginScreen == false,
                    enter = fadeIn() + scaleIn(initialScale = 0.85f),
                    exit = fadeOut() + scaleOut(targetScale = 0.85f)
                ) {
                    LoginScreen(
                        modifier = Modifier.padding(it),
                        snackbarHostState = snackbarHostState,
                        onSignUpButtonClick = {
                            signupContract.launch(Unit)
                        },
                        onFindPasswordButtonClick = {

                        },
                        onLoginCompleted = {
                            planManageActivityContract.launch(Unit)
                            finish()
                        }
                    )
                }
            }
        }
    }
}