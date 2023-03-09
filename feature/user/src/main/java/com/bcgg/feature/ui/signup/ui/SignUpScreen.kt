package com.bcgg.feature.ui.signup.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bcgg.core.ui.component.BcggUserLoginTextField
import com.bcgg.core.ui.component.LargeButton
import com.bcgg.core.ui.component.ProgressDialog
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.ui.signup.state.SignUpUiState
import com.bcgg.feature.ui.signup.viewmodel.SignUpViewModel
import com.bcgg.feature.user.R
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(viewModel: SignUpViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val signUpUiState by viewModel.signUpUiState.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest {
            snackbarHostState.showSnackbar(it)
        }
    }

    LaunchedEffect(Unit) {
        viewModel.signUpCompletedEvent.collectLatest {
            // 회원가입 완료 시 처리
        }
    }

    EdgeToEdge()
    SignUpScreen(
        uiState = signUpUiState,
        isLoading = isLoading,
        snackBarHostState = snackbarHostState,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    isLoading: Boolean,
    snackBarHostState: SnackbarHostState,
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

    AppTheme {
        ProgressDialog(show = isLoading)
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            snackbarHost = { SnackbarHost(hostState = snackBarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(text = stringResource(R.string.signup_screen_title)) },
                    navigationIcon = {
                        Icon(
                            modifier = Modifier
                                .clickable(onClick = onBack)
                                .padding(16.dp),
                            imageVector = Icons.Arrowleft,
                            contentDescription = ""
                        )
                    }
                )
            },
            bottomBar = {
                LargeButton(
                    modifier = Modifier.padding(16.dp).imePadding(),
                    onClick = onSignUpButtonClick,
                    enabled = uiState.id.isNotBlank() &&
                        uiState.password.isNotBlank() &&
                        uiState.passwordConfirm.isNotBlank() &&
                        uiState.isIdDuplicated == false
                ) {
                    Text(text = stringResource(R.string.signup_screen_signup_button_text))
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .padding(16.dp)
                    .imePadding(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
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
                    enabled = uiState.isIdDuplicated != true
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

                BcggUserLoginTextField(
                    value = uiState.passwordConfirm,
                    onValueChange = onPasswordConfirmChange,
                    hint = stringResource(R.string.signup_screen_input_hint_password_confirm),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )
            }
        }
    }
}

@Preview
@Composable
internal fun SignUpScreenPreview() {
    SignUpScreen(
        uiState = SignUpUiState(),
        isLoading = true,
        onIdChange = {},
        onPasswordChange = {},
        onPasswordConfirmChange = {},
        onSignUpButtonClick = { },
        onCheckIdDuplicatedButtonClick = { },
        snackBarHostState = SnackbarHostState()
    )
}
