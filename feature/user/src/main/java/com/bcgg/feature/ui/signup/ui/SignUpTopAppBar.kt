package com.bcgg.feature.ui.signup.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.bcgg.feature.ui.signup.viewmodel.SignUpViewModel
import com.bcgg.feature.user.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpTopAppBar(
    onBack: () -> Unit
) {
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
}