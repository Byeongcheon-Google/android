package com.bcgg.core.data.source

import android.util.Log
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.*
import javax.inject.Inject

interface IWebSocketChannel {
    fun getIncoming(): Flow<String>
    fun isClosed(): Boolean
    fun close(
        code: Int = 1000,
        reason: String? = null
    )

    fun send(data: String)
}

class WebSocketChannel @Inject constructor(
    private val serverUrl: String,
    private val scheduleId: Int,
    private val authInterceptor: Interceptor,
    private val scope: CoroutineScope
) : IWebSocketChannel {

    interface Factory {
        fun create(scope: CoroutineScope, scheduleId: Int): WebSocketChannel
    }

    private val socket: WebSocket
    private val incoming = Channel<String>()
    private val outgoing = Channel<String>()
    private val incomingFlow: Flow<String> = incoming.consumeAsFlow()

    init {
        val request = Request.Builder()
            .url("${serverUrl}/${scheduleId}")
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .pingInterval(15, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()

        socket =
            okHttpClient.newWebSocket(request, WebSocketChannelListener(incoming, outgoing))

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        // okHttpClient.dispatcher.executorService.shutdown()

        // Everything that goes into the outgoing channel is sent to the web socket.
        // Everything that gets into the incoming channel is sent to incomingFlow.
        scope.launch(Dispatchers.IO) {
            try {
                outgoing.consumeEach {
                    socket.send(it)
                }
            } finally {
                close()
            }
        }
    }

    override fun getIncoming(): Flow<String> {
        return incomingFlow
    }

    override fun isClosed(): Boolean {
        return incoming.isClosedForReceive || outgoing.isClosedForSend
    }

    override fun close(
        code: Int,
        reason: String?
    ) {
        scope.launch(Dispatchers.IO) {
            socket.close(code, reason) // note: Channels will close in WebSocket.onClosed
        }
    }

    override fun send(data: String) {
        val result = outgoing.trySendBlocking(data)
        if(result.exceptionOrNull() != null) {
            Log.d("Websocket", "Message send error: ${result.exceptionOrNull()!!.message}")
        }
    }

    inner class WebSocketChannelListener(
        private val incoming: Channel<String>,
        private val outgoing: Channel<String>
    ) : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {}

        override fun onMessage(webSocket: WebSocket, text: String) {
            scope.launch(Dispatchers.IO) {
                incoming.send(text)
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            scope.launch(Dispatchers.IO) {
                incoming.send(bytes.toString())
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {}

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            incoming.close()
            outgoing.close()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            incoming.close(t)
            outgoing.close(t)
        }
    }
}

/**
 * Usage

class Repository {
private lateinit var channel: IWebSocketChannel
fun webSocketCreate(scope: CoroutineScope): Flow<RawData> {
channel = WebSocketChannel(scope)
return channel.getIncoming()
}

fun webSocketSend(data: RawData) {
channel.send(data)
}
}
 */