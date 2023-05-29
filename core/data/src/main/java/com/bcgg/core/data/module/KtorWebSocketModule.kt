package com.bcgg.core.data.module

import com.bcgg.core.data.source.WebSocketChannel
import com.bcgg.core.data.source.chat.KtorWebSocketDataSource
import com.bcgg.core.networking.qualifiers.Auth
import com.bcgg.core.networking.qualifiers.Wss
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorWebSocketModule {

    @Provides
    @Singleton
    fun provideKtorWebSocketFactory(
        webSocketChannelFactory: WebSocketChannel.Factory,
        @Wss serverUrl: String
    ): KtorWebSocketDataSource.Factory {
        return object : KtorWebSocketDataSource.Factory {
            override fun create(scope: CoroutineScope, scheduleId: Int) =
                KtorWebSocketDataSource(webSocketChannelFactory, serverUrl, scheduleId, scope)
        }
    }

    @Provides
    @Singleton
    fun prodiveWebSocketChannelFactory(
        @Wss serverUrl: String,
        @Auth authInterceptor: Interceptor,
    ) = object : WebSocketChannel.Factory {
        override fun create(scope: CoroutineScope, scheduleId: Int): WebSocketChannel =
            WebSocketChannel(serverUrl, scheduleId, authInterceptor, scope)
    }
}