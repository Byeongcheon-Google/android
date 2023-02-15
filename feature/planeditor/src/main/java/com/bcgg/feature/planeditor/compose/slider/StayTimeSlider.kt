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
import com.bcgg.feature.planeditor.util.calculateStayTimeSliderRightThreshold
import java.time.LocalTime

@Composable
fun StayTimeSlider(
    comeTime: LocalTime,
    stayTimeHour: Int,
    availableTime: ClosedRange<LocalTime>,
    unlimited: Boolean,
    onStayTimeChange: (Int) -> Unit
) {
    val maxStayTimeHour = availableTime.endInclusive.plusHours(1).hour - comeTime.hour

    Box(
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            SliderTrack()
            if (!unlimited) {
                SliderRightThresholdTrack(
                    fraction = calculateStayTimeSliderRightThreshold(
                        maxStayTimeHour
                    )
                )
            }
        }

        Slider(
            value = stayTimeHour.toFloat(),
            valueRange = Constant.MIN_STAY_TIME.toFloat()..Constant.MAX_STAY_TIME.toFloat(),
            steps = Constant.MAX_STAY_TIME,
            onValueChange = {
                var newValue = it.toInt()

                if (!unlimited && newValue > maxStayTimeHour) newValue = maxStayTimeHour

                onStayTimeChange(newValue)
            },
            colors = SliderDefaults.colors(
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )
    }
}
