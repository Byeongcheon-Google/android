package com.bcgg.core.data.source.chat

import android.util.Log
import com.bcgg.core.data.response.chat.ChatMessage
import com.bcgg.core.data.response.chat.ChatMessageCommand
import com.bcgg.core.data.source.user.UserAuthDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.yield
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.util.*
import javax.inject.Inject

private const val CHAT_DEBUG = false

class ChatRemoteDataSource @Inject constructor(
    private val stompClient: StompClient,
    private val userAuthDataSource: UserAuthDataSource,
    private val coroutineScope: CoroutineScope
) {

    private val ROOM_ID = "7682954f-c008-4113-a39a-e503dffa694a"
    private var userId: String? = null
    private val stompClientLifecycle = stompClient.lifecycle().asFlow()
    val topic =
        stompClient.topic("/sub/chat/room/$ROOM_ID").asFlow().map {
            Json.decodeFromString<ChatMessage>(it.payload)
        }
    private var lastSentTime = System.currentTimeMillis()
    private val messageStack = Stack<Pair<ChatMessageCommand, String>>()

    init {
        stompClient.connect()
        coroutineScope.launch {
            while (isActive) {
                if (System.currentTimeMillis() - lastSentTime < 500 || messageStack.isEmpty()) delay(100)
                else {
                    waitConnection()
                    if(CHAT_DEBUG) Log.d("Stomp", "Sending message")
                    val (command, message) = messageStack.pop()
                    messageStack.removeIf { it.first == command }
                    stompClient.send(
                        "/pub/chat/message",
                        Json.encodeToString(generateMessage(command, message))
                    ).await()
                    if(CHAT_DEBUG) Log.d("Stomp", "Message Sent")
                    lastSentTime = System.currentTimeMillis()
                }
            }
        }
    }

    fun sendMessage(command: ChatMessageCommand, message: String) {
        messageStack.push(command to message)
    }

    suspend fun sendMessageWithoutBackPressure(command: ChatMessageCommand, message: String) {
        waitConnection()
        if(CHAT_DEBUG) Log.d("Stomp", "Sending message")
        stompClient.send(
            "/pub/chat/message",
            Json.encodeToString(generateMessage(command, message))
        ).await()
        if(CHAT_DEBUG) Log.d("Stomp", "Message Sent")
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
                    if(CHAT_DEBUG) Log.d("Stomp", "Connected")
                    return@collectLatest
                } else if (it.type == LifecycleEvent.Type.ERROR) {
                    if(CHAT_DEBUG) Log.e("Stomp", it.exception.message ?: "Unknown error")
                    throw it.exception
                }
            }
        }
    }

    private suspend fun generateMessage(command: ChatMessageCommand, message: String) = ChatMessage(
        type = "TALK",
        roomId = ROOM_ID,
        sender = getUserId(),
        message = message,
        command = command
    )
}