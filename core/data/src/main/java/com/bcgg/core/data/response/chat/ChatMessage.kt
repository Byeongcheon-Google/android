package com.bcgg.core.data.response.chat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

internal val REGEX = """\[(.*)\]:(.*)""".toRegex()

@Serializable
data class ChatMessage(
    @SerialName("type") val type: String,
    @SerialName("roomId") val roomId: String,
    @SerialName("command") val command: ChatMessageCommand?,
    @SerialName("sender") val sender: String,
    @SerialName("message") val message: String,
)

enum class ChatMessageCommand {
    TITLE, VIEWINGLOCATION, STARTTIME, ENDTIME, MEALTIMES, POINTS, STARTPLACE, ENDPLACE, INFO
}