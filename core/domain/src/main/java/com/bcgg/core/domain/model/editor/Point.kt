package com.bcgg.core.domain.model.editor

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Point(
    @Transient val id: String = "",
    @SerialName("name") val name: String? = null,
    @SerialName("lat") val lat: Double,
    @SerialName("lon") val lon: Double,
    @SerialName("classification") val classification: Classification,
    @SerialName("stayTimeMinute") val stayTimeMinute: Long
) {
    enum class Classification {
        Travel, House, Food;

        override fun toString(): String {
            return when(this) {
                Travel -> "✈️"
                House -> "🏠"
                Food -> "🍴"
            }
        }
    }
    override fun toString(): String {
        return "$classification ${String.format("%s\t\t", name ?: "")} ${String.format("(%.7f, %.7f)", lat, lon)}, "
    }

    override fun equals(other: Any?): Boolean {
        if(other is Point) {
            return this.lat.equals(other.lat) && this.lon.equals(other.lon)
        }
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return this.lat.hashCode() * this.lon.hashCode() * 31
    }
}
