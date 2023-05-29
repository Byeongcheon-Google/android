package com.bcgg.core.datamap.module

import com.bcgg.core.networking.constant.Constant
import com.bcgg.core.networking.qualifiers.KakaoMap
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
object KakaoMapOkHttpClientModule {
    @KakaoMap
    @Provides
    @Singleton
    fun provideKakaoMapOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @KakaoMap kakaoMapInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(Constant.CONNECT_TIMEOUT_SECOND, TimeUnit.SECONDS)
            readTimeout(Constant.READ_TIMEOUT_SECOND, TimeUnit.SECONDS)
            writeTimeout(Constant.WRITE_TIMEOUT_SECOND, TimeUnit.SECONDS)
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(kakaoMapInterceptor)
        }.build()
    }
}
