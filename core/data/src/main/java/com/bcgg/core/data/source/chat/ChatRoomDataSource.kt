package com.bcgg.core.data.source.chat

import com.bcgg.core.data.model.chat.ChatRoomResponse
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatRoomDataSource {
    @GET("chat/rooms")
    fun getRooms(): List<ChatRoomResponse>

    @POST("chat/room")
    fun newRoom(
        @Query("name") roomName: String
    ): ChatRoomResponse
}