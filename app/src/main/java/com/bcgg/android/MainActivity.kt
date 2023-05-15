package com.bcgg.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.bcgg.core.ui.provider.LocalActivity
import com.bcgg.core.ui.theme.AppTheme
import com.bcgg.core.ui.util.EdgeToEdge
import com.bcgg.feature.planmanage.ui.PlanManageScreen
import com.bcgg.feature.planresult.compose.PlanResultScreen
import com.bcgg.feature.planresult.state.PlanResultItemUiState
import com.bcgg.feature.planresult.util.getSample
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            var selectedPlanResultItemUiState by remember { mutableStateOf<PlanResultItemUiState?>(null) }
            EdgeToEdge()
            CompositionLocalProvider(LocalActivity provides this) {
                PlanManageScreen(
                    snackBarHostState = ,
                    plans = ,
                    onAddButtonClick = { /*TODO*/ },
                    onPlanItemClick = ,
                    onPlanItemRemove = ,
                    onPlanItemRemoveUndo = 
                )
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
