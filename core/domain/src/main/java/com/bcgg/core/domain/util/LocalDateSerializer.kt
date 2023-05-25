package com.bcgg.core.domain.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalDateSerializer : KSerializer<LocalDate>{
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("date", PrimitiveKind.STRING)

    private const val FORMAT = "yyyy-MM-dd"
    private val formatter = DateTimeFormatter.ofPattern(FORMAT)

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString(), formatter)
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.format(formatter))
    }
}