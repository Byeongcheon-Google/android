package com.bcgg.android.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.core.view.WindowCompat
import com.bcgg.android.activity.contract.SignupContract
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.feature.ui.signup.ui.SignUpScreen
import com.bcgg.feature.ui.signup.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignupActivity : ComponentActivity() {

    private val signUpViewModel: SignUpViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val snackbarHostState = remember { SnackbarHostState() }

            AppTheme {
                SignUpScreen(
                    viewModel = signUpViewModel,
                    snackbarHostState = snackbarHostState,
                    onSignupCompleted = {
                        setResult(RESULT_OK, Intent().apply {
                            putExtra(SignupContract.ID, it)
                        })
                        finish()
                    },
                    onBack = {
                        finish()
                    }
                )
            }
        }
    }
}