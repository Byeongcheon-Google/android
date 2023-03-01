package com.bcgg.feature.ui.login.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.component.BcggUserLoginTextField
import com.bcgg.core.ui.component.LargeButton
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.ui.login.state.LoginUiState
import com.bcgg.feature.ui.login.viewmodel.LoginViewModel
import com.bcgg.feature.user.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val loginUiState by viewModel.loginUiState.collectAsState()
    LoginScreen(
        uiState = loginUiState,
        onEmailChange = {
            viewModel.setEmail(it)
        },
        onPasswordChange = { viewModel.setPassword(it) },
        onLoginButtonClick = { viewModel.login() },
        onSignUpButtonClick = { },
        onFindPasswordButtonClick = { }
    )
}

@Composable
fun LoginScreen(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    onFindPasswordButtonClick: () -> Unit,
    onBack: () -> Unit = {}
) {
    fun handleBackPress() {
        onBack()
    }

    BackHandler {
        handleBackPress()
    }

    AppTheme {
        EdgeToEdge()

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .imePadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
            ) {
                BcggUserLoginTextField(
                    value = uiState.email,
                    onValueChange = {
                        onEmailChange(it)
                    },
                    hint = stringResource(R.string.login_screen_input_hint_email),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
                )
                BcggUserLoginTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    hint = stringResource(R.string.login_screen_input_hint_password),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(8.dp))

                LargeButton(onClick = onLoginButtonClick, enabled = uiState.isLoginAvailable) {
                    Text(stringResource(R.string.login_screen_button_login))
                }

                CompositionLocalProvider(LocalTextStyle provides MaterialTheme.typography.bodySmall) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier
                                .clickable(onClick = onSignUpButtonClick)
                                .padding(8.dp),
                            text = stringResource(R.string.login_screen_text_signup)
                        )
                        Text(text = " | ")
                        Text(
                            modifier = Modifier
                                .clickable(onClick = onFindPasswordButtonClick)
                                .padding(8.dp),
                            text = stringResource(R.string.login_screen_text_find_password)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
internal fun LoginScreenPreview() {
    var uiState by remember { mutableStateOf(LoginUiState()) }
    val coroutineScope = rememberCoroutineScope()

    LoginScreen(
        uiState = uiState,
        onEmailChange = {
            uiState = uiState.copy(email = it)
        },
        onPasswordChange = {
            uiState = uiState.copy(password = it)
        },
        onLoginButtonClick = {
            coroutineScope.launch {
                uiState = uiState.copy(isLoading = true)
                delay(1000)
                uiState = uiState.copy(isLoading = false)
            }
        },
        onBack = {},
        onFindPasswordButtonClick = {},
        onSignUpButtonClick = {}
    )
}
