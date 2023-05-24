package com.bcgg.feature.ui.signup.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.bcgg.core.domain.model.state.UserPasswordValidationState
import com.bcgg.core.ui.component.BcggUserLoginTextField
import com.bcgg.core.ui.component.LargeButton
import com.bcgg.core.ui.component.ProgressDialog
import com.bcgg.core.ui.provider.LocalScaffoldPaddingValues
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.ui.signup.state.SignUpUiState
import com.bcgg.feature.ui.signup.viewmodel.SignUpViewModel
import com.bcgg.feature.user.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onSignupCompleted: (String) -> Unit
) {
    val signUpUiState by viewModel.signUpUiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    LaunchedEffect(Unit) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            launch {
                viewModel.errorMessage.collectLatest {
                    snackbarHostState.showSnackbar(it)
                }
            }
            launch {
                viewModel.signUpCompletedEvent.collectLatest {
                    onSignupCompleted(it)
                }
            }
        }
    }

    EdgeToEdge()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState, modifier = Modifier.imePadding())
        },
        topBar = {
            SignUpTopAppBar(onBack)
        }
    ) { paddingValues ->
        CompositionLocalProvider(LocalScaffoldPaddingValues provides paddingValues) {
            SignUpScreen(
                uiState = signUpUiState,
                isLoading = isLoading,
                onIdChange = {
                    viewModel.updateIdText(it)
                },
                onPasswordChange = {
                    viewModel.updatePasswordText(it)
                },
                onPasswordConfirmChange = {
                    viewModel.updatePasswordConfirmText(it)
                },
                onSignUpButtonClick = { viewModel.signUp() },
                onCheckIdDuplicatedButtonClick = { viewModel.checkIdIsDuplicated() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SignUpScreen(
    uiState: SignUpUiState,
    isLoading: Boolean,
    onIdChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordConfirmChange: (String) -> Unit,
    onSignUpButtonClick: () -> Unit,
    onCheckIdDuplicatedButtonClick: () -> Unit,
    onBack: () -> Unit = {}
) {
    fun handleBackPress() {
        onBack()
    }

    BackHandler {
        handleBackPress()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding()
            .padding(LocalScaffoldPaddingValues.current)
    ) {
        Column {
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                BcggUserLoginTextField(
                    value = uiState.id,
                    onValueChange = {
                        onIdChange(it)
                    },
                    hint = stringResource(R.string.signup_screen_input_hint_id),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                    )
                )

                LargeButton(
                    onClick = onCheckIdDuplicatedButtonClick,
                    enabled = uiState.isIdDuplicateCheckEnabled
                ) {
                    Text(text = stringResource(R.string.signup_screen_check_id_text))
                }

                if (uiState.isIdDuplicated != null) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = if (uiState.isIdDuplicated) {
                            stringResource(R.string.signup_screen_id_is_duplicated)
                        } else {
                            stringResource(R.string.signup_screen_id_is_not_duplicated)
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = if (uiState.isIdDuplicated) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                BcggUserLoginTextField(
                    value = uiState.password,
                    onValueChange = onPasswordChange,
                    hint = stringResource(R.string.signup_screen_input_hint_password),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Text(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = when (uiState.passwordState) {
                        UserPasswordValidationState.NoPassword -> "8자 이상의 비밀번호를 입력해 주세요"
                        UserPasswordValidationState.OK -> ""
                        UserPasswordValidationState.TooShort -> "8자 이상의 비밀번호를 입력해 주세요"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.error
                )

                BcggUserLoginTextField(
                    value = uiState.passwordConfirm,
                    onValueChange = onPasswordConfirmChange,
                    hint = stringResource(R.string.signup_screen_input_hint_password_confirm),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                if (uiState.passwordConfirm.isNotBlank() && !uiState.isPasswordMatch) {
                    Text(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        text = stringResource(R.string.signup_screen_password_is_not_match),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            LargeButton(
                modifier = Modifier
                    .imePadding(),
                onClick = onSignUpButtonClick,
                enabled = uiState.isSignUpEnabled
            ) {
                Text(text = stringResource(R.string.signup_screen_signup_button_text))
            }
        }

        ProgressDialog(show = isLoading)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
internal fun SignUpScreenPreview() {
    SignUpScreen(
        uiState = SignUpUiState(),
        isLoading = false,
        onIdChange = {},
        onPasswordChange = {},
        onPasswordConfirmChange = {},
        onSignUpButtonClick = { },
        onCheckIdDuplicatedButtonClick = { }
    )
}
