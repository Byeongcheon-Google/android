package com.bcgg.core.data.module

import com.bcgg.core.data.qualifier.AiBackend
import com.bcgg.core.data.qualifier.BackendUrl
import com.bcgg.core.networking.qualifiers.Wss
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object URLModule {

    @BackendUrl
    @Provides
    @Singleton
    fun provideBackendServerUrl(): String = "http://192.168.0.30:8080"

    @Wss
    @Provides
    @Singleton
    fun provideBackendServerWssUrl(): String = "ws://192.168.0.30:8080/ws"

    @AiBackend
    @Provides
    @Singleton
    fun provideAiBackendServerWssUrl(): String = "http://192.168.0.29:8000"
}