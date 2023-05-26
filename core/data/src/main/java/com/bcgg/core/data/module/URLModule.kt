package com.bcgg.core.data.module

import com.bcgg.core.data.qualifier.BackendUrl
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
    fun provideBackendServerUrl(): String = "http://192.168.0.33:8081"
}