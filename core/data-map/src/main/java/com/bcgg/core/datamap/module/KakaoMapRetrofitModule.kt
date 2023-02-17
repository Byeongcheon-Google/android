package com.bcgg.core.datamap.module

import com.bcgg.core.networking.qualifiers.KakaoMap
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoMapRetrofitModule {

    @OptIn(ExperimentalSerializationApi::class)
    @KakaoMap
    @Provides
    @Singleton
    fun provideKakaoMapRetrofit(
        @KakaoMap baseUrl: String,
        @KakaoMap okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
}
