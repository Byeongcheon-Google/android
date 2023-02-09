package com.bcgg.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bcgg.core.ui.theme.AppTheme
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.NaverMap

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalNaverMapApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                NaverMap(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        Greeting("Android")
    }
}
