package com.bcgg.core.datamap.module

import com.bcgg.core.datamap.BuildConfig
import com.bcgg.core.networking.qualifiers.KakaoMap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoMapInterceptorModule {
    @KakaoMap
    @Provides
    @Singleton
    fun provideKakaoMapInterceptor(): Interceptor {
        return Interceptor { chain ->
            val kakaoApiKey = BuildConfig.KAKAO_API_KEY
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "KakaoAK $kakaoApiKey")
                .build()

            chain.proceed(newRequest)
        }
    }
}
