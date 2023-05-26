package com.bcgg.core.data.module

import com.bcgg.core.data.qualifier.Backend
import com.bcgg.core.data.util.ErrorInterceptor
import com.bcgg.core.networking.qualifiers.Auth
import com.bcgg.core.networking.qualifiers.NoAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {

    @Backend
    @Provides
    @Singleton
    fun provideBackendDefaultOkHttpClientBuilder(
        defaultOkHttpClientBuilder: OkHttpClient.Builder
    ) : OkHttpClient.Builder {
        return defaultOkHttpClientBuilder
            //.addInterceptor(ErrorInterceptor())
    }

    @NoAuth
    @Provides
    @Singleton
    fun provideNoAuthOkHttpClient(
        @Backend defaultOkHttpClientBuilder: OkHttpClient.Builder
    ): OkHttpClient {
        return defaultOkHttpClientBuilder.build()
    }

    @Auth
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        @Backend defaultOkHttpClientBuilder: OkHttpClient.Builder,
        @Auth authInterceptor: Interceptor
    ): OkHttpClient {
        return defaultOkHttpClientBuilder
            .addInterceptor(authInterceptor)
            .build()
    }
}