package com.bcgg.feature.ui.login.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.bcgg.core.ui.component.BcggUserLoginTextField
import com.bcgg.core.ui.component.LargeButton
import com.bcgg.core.ui.component.ProgressDialog
import com.bcgg.core.ui.compositionlocal.LocalScaffoldPaddingValues
import com.bcgg.feature.ui.login.state.LoginUiState
import com.bcgg.feature.ui.login.viewmodel.LoginViewModel
import com.bcgg.feature.user.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onSignUpButtonClick: () -> Unit,
    onFindPasswordButtonClick: () -> Unit,
    signUpCompletedId: String? = null,
    onLoginCompleted: () -> Unit
) {
    val loginUiState by loginViewModel.loginUiState.collectAsState()
    val isLoading by loginViewModel.isLoading.collectAsState()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                loginViewModel.errorMessage.collectLatest {
                    snackbarHostState.showSnackbar(it)
                }
            }
            launch {
                loginViewModel.loginCompletedEvent.collectLatest {
                    onLoginCompleted()
                }
            }
        }
    }

    LaunchedEffect(signUpCompletedId) {
        if(signUpCompletedId == null) return@LaunchedEffect

        loginViewModel.setId(signUpCompletedId)
        snackbarHostState.showSnackbar("회원가입을 완료했습니다.")
    }
    
    ProgressDialog(show = isLoading)

    LoginScreen(
        modifier = modifier.padding(LocalScaffoldPaddingValues.current),
        uiState = loginUiState,
        onIdChange = {
            loginViewModel.setId(it)
        },
        onPasswordChange = { loginViewModel.setPassword(it) },
        onLoginButtonClick = { loginViewModel.login() },
        onSignUpButtonClick = onSignUpButtonClick,
        onFindPasswordButtonClick = onFindPasswordButtonClick,
        snackbarHostState = snackbarHostState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    snackbarHostState: SnackbarHostState,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onSignUpButtonClick: () -> Unit,
    onFindPasswordButtonClick: () -> Unit
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.imePadding())
        }
    ) {
        Column(
            modifier = modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Box {
                Icon(
                    painter = painterResource(id = com.bcgg.core.ui.R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            BcggUserLoginTextField(
                value = uiState.id,
                onValueChange = {
                    onIdChange(it)
                },
                hint = stringResource(R.string.login_screen_input_hint_id),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
            BcggUserLoginTextField(
                value = uiState.password,
                onValueChange = onPasswordChange,
                hint = stringResource(R.string.login_screen_input_hint_password),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
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

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
internal fun LoginScreenPreview() {
    var uiState by remember { mutableStateOf(LoginUiState()) }
    val coroutineScope = rememberCoroutineScope()

    LoginScreen(
        uiState = uiState,
        onIdChange = {
            uiState = uiState.copy(id = it)
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
        onFindPasswordButtonClick = {},
        onSignUpButtonClick = {},
        snackbarHostState = SnackbarHostState()
    )
}
