package com.bcgg.core.ui.component

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings.LOAD_NO_CACHE
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun WebView(
    modifier: Modifier = Modifier,
    url: String,
    webViewController: WebViewController = remember { WebViewController() },
    onPageLoading: @Composable () -> Unit = {},
) {
    val isPageLoading = rememberSaveable { mutableStateOf(false) }

    Box {
        AndroidView(modifier = modifier, factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                webChromeClient = WebChromeClient()
                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        isPageLoading.value = true
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        isPageLoading.value = false
                    }
                }

                settings.apply {
                    javaScriptEnabled = true
                    setSupportMultipleWindows(false)
                    loadWithOverviewMode = true
                    useWideViewPort = true
                    setSupportZoom(false)
                    builtInZoomControls = false
                    cacheMode = LOAD_NO_CACHE
                    domStorageEnabled = true
                }

                loadUrl(url)
            }
        }, update = {
            it.loadUrl(url)
        })

        if(isPageLoading.value) onPageLoading()
    }
}

@Stable
class WebViewController {
    var canGoBack = mutableStateOf(false)
    val goBackEvent = MutableSharedFlow<Unit>()


}