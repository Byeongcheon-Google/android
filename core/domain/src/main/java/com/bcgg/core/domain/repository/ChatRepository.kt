package com.bcgg.core.domain.repository

import com.bcgg.core.data.source.chat.ChatRemoteDataSource
import com.bcgg.core.domain.model.Location
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatRemoteDataSource: ChatRemoteDataSource
) {
    val topic = chatRemoteDataSource.topic

    suspend fun updateTitle(newTitle: String) {
        chatRemoteDataSource.sendMessage("[title]:$newTitle")
    }

    suspend fun updateViewingPosition(lat: Double, lng: Double) {
        chatRemoteDataSource.sendMessage("[viewinglocation]:${Json.encodeToString(Location(lat, lng))}")
    }
}