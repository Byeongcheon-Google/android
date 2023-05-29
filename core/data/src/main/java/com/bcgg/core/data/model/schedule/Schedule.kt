package com.bcgg.core.data.model.schedule

import com.bcgg.core.util.ext.dateTimeNow
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    @SerialName("id") val id: Int = -1,
    @SerialName("ownerId") val ownerId: String = "",
    @SerialName("name") val name: String,
    @SerialName("createdAt") val createdAt: LocalDateTime = dateTimeNow,
    @SerialName("updatedAt") val updatedAt: LocalDateTime = dateTimeNow
)