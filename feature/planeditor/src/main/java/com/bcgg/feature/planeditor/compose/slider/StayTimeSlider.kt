package com.bcgg.feature.planeditor.compose.slider

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bcgg.feature.planeditor.constant.Constant

@Composable
fun StayTimeSlider(
    stayTimeHour: Int,
    onStayTimeChange: (Int) -> Unit
) {

    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            SliderTrack()
        }

        Slider(
            value = stayTimeHour.toFloat(),
            valueRange = Constant.MIN_STAY_TIME.toFloat()..Constant.MAX_STAY_TIME.toFloat(),
            steps = Constant.MAX_STAY_TIME,
            onValueChange = {
                onStayTimeChange(it.toInt())
            },
            colors = SliderDefaults.colors(
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )
    }
}
