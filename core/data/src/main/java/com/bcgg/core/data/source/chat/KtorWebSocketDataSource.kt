package com.bcgg.core.data.source.chat

import android.util.Log
import com.bcgg.core.data.model.chat.Chat
import com.bcgg.core.data.model.chat.ChatPolymophicSerializer
import com.bcgg.core.data.source.WebSocketChannel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.util.*
import javax.inject.Inject

@ViewModelScoped
class KtorWebSocketDataSource @Inject constructor(
    private val webSocketChannelFactory: WebSocketChannel.Factory,
    private val serverUrl: String,
    val scheduleId: Int,
    private val scope: CoroutineScope
) {
    interface Factory {
        fun create(scope: CoroutineScope, scheduleId: Int): KtorWebSocketDataSource
    }

    private val webSocketChannel = webSocketChannelFactory.create(scope, scheduleId)
    val topic = webSocketChannel.getIncoming().map {
        Log.d("Topic in getIncoming", it)
        val chat = Json.decodeFromString(ChatPolymophicSerializer, it)
        Log.d("Topic in getIncoming", chat.toString())
        chat

    }

    private var lastSentTime = System.currentTimeMillis()
    private val messageStack = Stack<Chat>()

    init {
        scope.launch {
            while(isActive) {
                if(System.currentTimeMillis() - lastSentTime < 500 || messageStack.isEmpty()) {
                    delay(100)
                    continue
                }

                val chat = messageStack.pop()
                messageStack.removeIf { chat.javaClass.simpleName == it.javaClass.simpleName }

                webSocketChannel.send(Json.encodeToString(ChatPolymophicSerializer, chat))
                lastSentTime = System.currentTimeMillis()
            }
        }
    }

    fun sendMessage(chat: Chat) {
        messageStack.push(chat)
    }

    fun sendMessageImmediately(chat: Chat) {
        webSocketChannel.send(Json.encodeToString(ChatPolymophicSerializer, chat))
        lastSentTime = System.currentTimeMillis()
    }

    fun close() {
        if(webSocketChannel.isClosed()) return
        while (messageStack.isNotEmpty()) {
            val chat = messageStack.pop()
            messageStack.removeIf { chat.javaClass.simpleName == it.javaClass.simpleName }

            sendMessageImmediately(chat)
        }
        webSocketChannel.close()
    }
}