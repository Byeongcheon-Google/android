package com.bcgg.core.data.model.chat

import kotlinx.serialization.SerialName

data class ChatRoomResponse(
    @SerialName("roomId") val roomId: String,
    @SerialName("name") val name: String
)
