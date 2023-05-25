package com.bcgg.core.domain.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object LocalTimeListSerializer : KSerializer<List<LocalTime>>{
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("time", PrimitiveKind.STRING)

    private const val FORMAT = "HH:mm"
    private val formatter = DateTimeFormatter.ofPattern(FORMAT)

    override fun deserialize(decoder: Decoder): List<LocalTime> {
        return decoder.decodeString()
            .apply { slice(1 until length) }
            .split(", ")
            .map { LocalTime.parse(it, formatter) }
    }

    override fun serialize(encoder: Encoder, value: List<LocalTime>) {
        encoder.encodeString(value.joinToString(prefix = "[", postfix = "]") { it.format(formatter) })
    }
}