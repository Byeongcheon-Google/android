package com.bcgg.core.data.source.chat

import android.util.Log
import com.bcgg.core.data.response.chat.ChatMessage
import com.bcgg.core.data.source.user.UserAuthDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx2.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import javax.inject.Inject

class ChatRemoteDataSource @Inject constructor(
    private val stompClient: StompClient,
    private val userAuthDataSource: UserAuthDataSource
) {
    private var userId: String? = null
    private val stompClientLifecycle = stompClient.lifecycle().asFlow()
    val topic =
        stompClient.topic("/sub/chat/room/f2e29adc-9b7f-4bc0-a0f4-dca10bb1c13b").asFlow().map {
            Json.decodeFromString<ChatMessage>(it.payload)
        }
    var lastSentTime = System.currentTimeMillis()

    init {
        stompClient.connect()
    }

    suspend fun sendMessage(message: String) {
        if (System.currentTimeMillis() - lastSentTime < 500) return
        waitConnection()
        Log.d("Stomp", "Sending message")
        stompClient.send(
            "/pub/chat/message",
            Json.encodeToString(generateMessage(message))
        ).await()
        Log.d("Stomp", "Message Sent")
        lastSentTime = System.currentTimeMillis()
    }

    private suspend fun getUserId(): String {
        if (userId == null) userId = userAuthDataSource.getUserId()
        return userId!!
    }

    private suspend fun waitConnection() {
        if (!stompClient.isConnected) {
            stompClientLifecycle.collectLatest {
                if (it.type == LifecycleEvent.Type.OPENED) {
                    Log.d("Stomp", "Connected")
                    return@collectLatest
                } else if (it.type == LifecycleEvent.Type.ERROR) {
                    Log.e("Stomp", it.exception.message ?: "Unknown error")
                    throw it.exception
                }
            }
        }
    }

    private suspend fun generateMessage(message: String) = ChatMessage(
        type = "TALK",
        roomId = "f2e29adc-9b7f-4bc0-a0f4-dca10bb1c13b",
        sender = getUserId(),
        message = message
    )
}