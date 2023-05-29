package com.bcgg.core.data.module

import com.bcgg.core.data.qualifier.Backend
import com.bcgg.core.data.util.ErrorInterceptor
import com.bcgg.core.networking.constant.Constant
import com.bcgg.core.networking.qualifiers.Auth
import com.bcgg.core.networking.qualifiers.NoAuth
import com.bcgg.core.networking.qualifiers.Wss
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
    @NoAuth
    @Provides
    @Singleton
    fun provideNoAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Auth authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constant.CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(Constant.READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(Constant.WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Auth
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @Auth authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(Constant.CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(Constant.READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(Constant.WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    @Wss
    @Provides
    @Singleton
    fun provideWsOkHttpClient(
        @Auth authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .pingInterval(5, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()
    }
}