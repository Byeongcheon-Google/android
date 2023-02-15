package com.bcgg.core.networking.module

import com.bcgg.core.networking.constant.Constant.CONNECT_TIMEOUT_SECOND
import com.bcgg.core.networking.constant.Constant.READ_TIMEOUT_SECOND
import com.bcgg.core.networking.constant.Constant.WRITE_TIMEOUT_SECOND
import com.bcgg.core.networking.qualifiers.NoAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OkHttpClientModule {
    @NoAuth
    @Provides
    @Singleton
    fun provideNoAuthOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
        }.build()
    }
}
