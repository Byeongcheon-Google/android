package com.bcgg.feature.planeditor.compose.webview

import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import com.bcgg.core.ui.icon.Icons
import com.bcgg.core.ui.icon.icons.Arrowleft
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewState
import com.google.accompanist.web.rememberWebViewState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun PlaceSearchResultDetailWebView(
    placeSearchResult: PlaceSearchResult,
    isAdded: Boolean,
    show: Boolean,
    webViewState: WebViewState,
    onDismissRequest: () -> Unit,
    onAddButtonClick: (PlaceSearchResult) -> Unit,
) {
    AnimatedVisibility(visible = show) {

        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Column(
                                modifier = Modifier.padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    text = placeSearchResult.name,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                                Text(
                                    text = placeSearchResult.address,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    overflow = TextOverflow.Ellipsis,
                                    maxLines = 1
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = onDismissRequest) {
                                Icon(
                                    imageVector = androidx.compose.material.icons.Icons.Rounded.Close,
                                    contentDescription = ""
                                )
                            }
                        },
                        actions = {
                            FilledTonalButton(
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .heightIn(min = 32.dp),
                                onClick = { onAddButtonClick(placeSearchResult) },
                                enabled = !isAdded
                            ) {
                                Text(
                                    text = if (isAdded) "추가됨" else "추가",
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    )
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    WebView(state = webViewState, onCreated = {
                        it.webChromeClient = WebChromeClient()
                        it.settings.apply {
                            javaScriptEnabled = true
                            setSupportMultipleWindows(false)
                            loadWithOverviewMode = true
                            useWideViewPort = true
                            setSupportZoom(false)
                            builtInZoomControls = false
                            cacheMode = WebSettings.LOAD_NO_CACHE
                            domStorageEnabled = true
                        }
                    })

                    AnimatedVisibility(
                        visible = webViewState.isLoading,
                        enter = fadeIn() + scaleIn(initialScale = 0.85f),
                        exit = fadeOut() + scaleOut(targetScale = 0.85f)
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}