package com.bcgg.core.datamap.module

import com.bcgg.core.networking.qualifiers.KakaoMap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoMapOkHttpClientModule {
    @KakaoMap
    @Provides
    @Singleton
    fun provideKakaoMapOkHttpClient(
        defaultBuilder: OkHttpClient.Builder,
        @KakaoMap kakaoMapInterceptor: Interceptor
    ): OkHttpClient {
        return defaultBuilder
            .addInterceptor(kakaoMapInterceptor)
            .build()
    }
}
