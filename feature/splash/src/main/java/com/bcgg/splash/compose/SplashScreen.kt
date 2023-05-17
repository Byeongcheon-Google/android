package com.bcgg.splash.compose

import android.window.SplashScreen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.withResumed
import com.bcgg.core.ui.R
import com.bcgg.core.util.ext.collectOnFailure
import com.bcgg.core.util.ext.collectOnSuccess
import com.bcgg.splash.viewmodel.SplashViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    splashViewModel: SplashViewModel = hiltViewModel(),
    onAutoLoginSuccess: () -> Unit,
    onAutoLoginFailure: () -> Unit
) {
    val tokenIsValid by splashViewModel.tokenIsValid.collectAsState()

    LaunchedEffect(tokenIsValid) {
        if(tokenIsValid == true) onAutoLoginSuccess()
        if(tokenIsValid == false) onAutoLoginFailure()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground_splash),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        CircularProgressIndicator(modifier = Modifier.size(20.dp))
    }
}