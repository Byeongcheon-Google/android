package com.bcgg.core.util

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.buildSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable
data class PathFinderResult(
    val foundTime: LocalTime,
    val date: LocalDate,
    val result: List<PathFinderItem>
) {

    @Serializable(with = PathFinderResultPolymophicSerializer::class)
    sealed class PathFinderItem {

        @Serializable
        @SerialName("Place")
        data class Place(
            val name: String,
            val classification: Classification,
            val position: LatLng,
            val stayTimeMinute: Long,
            val startTime: LocalTime
        ) : PathFinderItem()

        @Serializable
        @SerialName("Move")
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

    enum class DistanceUnit {
        km, m
    }

    @Serializable
    data class LatLng(
        val lat: Double,
        val lng: Double
    )
}

object PathFinderResultPolymophicSerializer : JsonContentPolymorphicSerializer<PathFinderResult.PathFinderItem>(PathFinderResult.PathFinderItem::class){
    override fun selectDeserializer(element: JsonElement): DeserializationStrategy<PathFinderResult.PathFinderItem> {
        return when {
            "stayTimeMinute" in element.jsonObject -> PathFinderResult.PathFinderItem.Place.serializer()
            "durationMinute" in element.jsonObject -> PathFinderResult.PathFinderItem.Move.serializer()
            else -> PathFinderResult.PathFinderItem.serializer()
        }
    }
}