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
import com.bcgg.core.util.constant.LocalTimeConstants.MINUTES_PER_HOUR
import com.bcgg.core.util.ext.floor
import com.bcgg.core.util.ext.mediateOutOfRangedValue
import com.bcgg.feature.planeditor.constant.Constant.SLIDER_MINUTE_FLOOR
import com.bcgg.feature.planeditor.constant.Constant.TIME_SLIDER_END_TIME
import com.bcgg.feature.planeditor.util.calculateComeTimeSliderLeftThreshold
import com.bcgg.feature.planeditor.util.calculateComeTimeSliderRightThreshold
import com.bcgg.feature.planeditor.util.calculateMaxStayTime
import java.time.LocalTime

@Composable
fun ComeTimeSlider(
    comeTime: LocalTime,
    availableTime: ClosedRange<LocalTime>,
    onComeTimeChange: (LocalTime, Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        VerticalLines()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            SliderTrack()
            SliderLeftThresholdTrack(fraction = calculateComeTimeSliderLeftThreshold(availableTime))
            SliderRightThresholdTrack(fraction = calculateComeTimeSliderRightThreshold(availableTime))
        }
        Slider(
            value = (comeTime.hour + (comeTime.minute / MINUTES_PER_HOUR)).toFloat(),
            valueRange = 0f..TIME_SLIDER_END_TIME,
            onValueChange = {
                val minute = ((it - it.toInt()) * MINUTES_PER_HOUR).floor(SLIDER_MINUTE_FLOOR).toInt()
                val newTime = LocalTime.of(it.toInt(), minute).mediateOutOfRangedValue(availableTime)

                onComeTimeChange(newTime, calculateMaxStayTime(newTime, availableTime))
            },
            colors = SliderDefaults.colors(
                activeTrackColor = Color.Transparent,
                inactiveTrackColor = Color.Transparent
            )
        )
    }
}
