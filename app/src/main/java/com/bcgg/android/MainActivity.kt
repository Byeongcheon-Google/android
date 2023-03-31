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
import com.bcgg.feature.planmanage.state.PlanItemUiState
import com.bcgg.feature.planmanage.ui.PlanManageScreen
import com.bcgg.feature.planresult.compose.PlanResultScreen
import com.bcgg.feature.planresult.state.PlanResultItem
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val items = listOf(
            PlanResultItem.Place(
                name = "한국기술교육대학교 솔빛관",
                date = LocalDate.now(),
                timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
                point = LatLng(36.7612467, 127.2817232)
            ),
            PlanResultItem.Move(
                distanceDescription = "620m 이동",
                date = LocalDate.now(),
                timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
                points = listOf(
                    LatLng(36.7612467, 127.2817232),
                    LatLng(36.7655739, 127.2823278)
                )
            ),
            PlanResultItem.Place(
                name = "한국기술교육대학교 솔빛관",
                date = LocalDate.now(),
                timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
                point = LatLng(36.7612467, 127.2817232)
            ),
            PlanResultItem.Place(
                name = "한국기술교육대학교 솔빛관",
                date = LocalDate.now().plusDays(1),
                timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
                point = LatLng(36.7612467, 127.2817232)
            ),
            PlanResultItem.Move(
                distanceDescription = "620m 이동",
                date = LocalDate.now().plusDays(1),
                timeRange = LocalTime.now()..LocalTime.now().plusSeconds(45),
                points = listOf(
                    LatLng(36.7612467, 127.2817232),
                    LatLng(36.7655739, 127.2823278)
                )
            ),
            PlanResultItem.Place(
                name = "한국기술교육대학교 솔빛관",
                date = LocalDate.now().plusDays(1),
                timeRange = LocalTime.now()..LocalTime.now().plusHours(2),
                point = LatLng(36.7612467, 127.2817232)
            ),
        )

        setContent {
            var selectedPlanResultItem by remember { mutableStateOf<PlanResultItem?>(null) }
            EdgeToEdge()
            CompositionLocalProvider(LocalActivity provides this) {
                PlanResultScreen(
                    planName = "테스트",
                    planResultItems = items,
                    selectedPlanResultItem = selectedPlanResultItem,
                    onSelectedPlanResultItem = { selectedPlanResultItem = it }
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
