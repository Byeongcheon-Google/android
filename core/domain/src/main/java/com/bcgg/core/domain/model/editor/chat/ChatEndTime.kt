package com.bcgg.core.domain.model.editor.chat

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.datetime.serializers.LocalTimeIso8601Serializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatEndTime(
    @SerialName("date") @Serializable(with = LocalDateIso8601Serializer::class) val date: LocalDate,
    @SerialName("endTime") @Serializable(with = LocalTimeIso8601Serializer::class) val endTime : LocalTime
)
