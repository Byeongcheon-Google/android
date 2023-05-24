package com.bcgg.core.data.response.chat

import kotlinx.serialization.SerialName

data class ChatRoomResponse(
    @SerialName("roomId") val roomId: String,
    @SerialName("name") val name: String
)
