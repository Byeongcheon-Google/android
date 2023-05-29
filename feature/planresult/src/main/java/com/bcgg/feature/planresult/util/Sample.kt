package com.bcgg.feature.planresult.util

import android.content.Context
import com.bcgg.core.domain.model.result.PathFinderResult
import com.bcgg.feature.planresult.state.PlanResultItemUiState
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import kotlinx.datetime.toJavaLocalTime
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.jsonObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate

fun getSample(context: Context): List<PlanResultItemUiState> {
    val reader = context.assets.open("sample.json")

    val result = Json.decodeFromStream<PathFinderResult>(reader).result

    return result.mapIndexed { index, item ->
        when (item) {
            is PathFinderResult.PathFinderItem.Move -> {
                val startPlace = result[index - 1] as PathFinderResult.PathFinderItem.Place
                val endPlace = result[index + 1] as PathFinderResult.PathFinderItem.Place

                PlanResultItemUiState.Move(
                    distanceDescription = "${
                        String.format(
                            "%.1f",
                            item.distance
                        )
                    }${item.distanceUnit} 이동",
                    date = LocalDate.now(),
                    timeRange = item.startTime.toJavaLocalTime()..item.startTime.toJavaLocalTime()
                        .plusMinutes(item.durationMinute),
                    bound = LatLngBounds(
                        LatLng(item.boundSouthWest.lat, item.boundSouthWest.lng),
                        LatLng(item.boundNorthEast.lat, item.boundNorthEast.lng)
                    ),
                    points = item.points.map { LatLng(it.lat, it.lng) },
                )
            }

            is PathFinderResult.PathFinderItem.Place -> {
                PlanResultItemUiState.Place(
                    name = item.name,
                    date = LocalDate.now(),
                    timeRange = item.startTime.toJavaLocalTime()..item.startTime.toJavaLocalTime()
                        .plusMinutes(item.stayTimeMinute),
                    point = LatLng(item.position.lat, item.position.lng),
                    number = (index + 2) / 2,
                    classification = item.classification
                )
            }
        }
    }
}