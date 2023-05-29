package com.bcgg.core.data.model.schedule

import com.bcgg.core.util.Classification
import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Place(
    @SerialName("id") val id: Int,
    @SerialName("scheduleId") val scheduleId: Int,
    @SerialName("date") val date: LocalDate,
    @SerialName("kakaoPlaceId") val kakaoPlaceId: String,
    @SerialName("name") val name: String,
    @SerialName("address") val address: String,
    @SerialName("lat") val lat: Double,
    @SerialName("lng") val lng: Double,
    @SerialName("classification") val classification: Classification,
    @SerialName("stayTimeHour") val stayTimeHour: Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Place

        if (scheduleId != other.scheduleId) return false
        if (date != other.date) return false
        if (kakaoPlaceId != other.kakaoPlaceId) return false
        if (name != other.name) return false
        if (address != other.address) return false
        if (lat != other.lat) return false
        if (lng != other.lng) return false
        if (classification != other.classification) return false

        return true
    }

    override fun hashCode(): Int {
        var result = scheduleId
        result = 31 * result + date.hashCode()
        result = 31 * result + kakaoPlaceId.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + lat.hashCode()
        result = 31 * result + lng.hashCode()
        result = 31 * result + classification.hashCode()
        return result
    }
}