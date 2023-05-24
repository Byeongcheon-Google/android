package com.bcgg.core.data.response.chat

import android.icu.text.CaseMap.Title
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.lang.IllegalStateException

internal val REGEX = """\[(.*)\]:(.*)""".toRegex()

@Serializable
data class ChatMessage(
    @SerialName("type") val type: String,
    @SerialName("roomId") val roomId: String,
    @SerialName("sender") val sender: String,
    @SerialName("message") val message: String,
) {
    fun getChatMessageType(): ChatMessageType {
        return when(REGEX.matchEntire(message)?.groupValues?.get(1)) {
            "title" -> ChatMessageType.Title
            "viewinglocation" -> ChatMessageType.ViewingLocation
            else -> throw IllegalStateException("wrong type")
        }
    }

    fun getValue() = REGEX.matchEntire(message)?.groupValues?.get(2)
}

enum class ChatMessageType {
    Title, ViewingLocation
}