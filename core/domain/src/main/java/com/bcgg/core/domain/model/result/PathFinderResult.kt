package com.bcgg.core.domain.model.result

import com.bcgg.core.domain.model.Classification
import kotlinx.serialization.Serializable
import kotlinx.datetime.LocalTime
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class PathFinderResult(
    val result: List<PathFinderItem>
) {
    @Serializable(with = PathFinderItemSerializer::class)
    sealed class PathFinderItem {
        @Serializable
        data class Place(
            val name: String,
            val classification: Classification,
            val position: LatLng,
            val stayTimeMinute: Long,
            val startTime: LocalTime
        ) : PathFinderItem()

        @Serializable
        data class Move(
            val distance: Double,
            val distanceUnit: DistanceUnit,
            val points: List<LatLng>,
            val boundSouthWest: LatLng,
            val boundNorthEast: LatLng,
            val startTime: LocalTime,
            val durationMinute: Long
        ) : PathFinderItem()
    }

    @Serializable
    enum class DistanceUnit {
        KM, M
    }

    @Serializable
    data class LatLng(
        val lat: Double,
        val lng: Double
    )
}

object PathFinderItemSerializer : JsonContentPolymorphicSerializer<PathFinderResult.PathFinderItem>(PathFinderResult.PathFinderItem::class) {
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out PathFinderResult.PathFinderItem> {
        return if(element.jsonObject["classification"] != null) PathFinderResult.PathFinderItem.Place.serializer() else PathFinderResult.PathFinderItem.Move.serializer()
    }
}