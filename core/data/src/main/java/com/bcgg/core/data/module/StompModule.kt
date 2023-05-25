package com.bcgg.core.data.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StompModule {

    @Provides
    @Singleton
    fun provideStompClient(): StompClient =
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://192.168.0.30:8080/ws-stomp/websocket")
}