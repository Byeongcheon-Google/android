package com.bcgg.core.data.module

import android.util.Log
import com.bcgg.core.data.qualifier.Backend
import com.bcgg.core.data.qualifier.BackendUrl
import com.bcgg.core.data.source.chat.ChatRoomDataSource
import com.bcgg.core.data.source.user.UserDataSource
import com.bcgg.core.data.source.user.UserFakeDataSource
import com.bcgg.core.networking.qualifiers.Auth
import com.bcgg.core.networking.qualifiers.NoAuth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import ua.naiksoftware.stomp.Stomp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {
    @NoAuth
    @Provides
    @Singleton
    fun provideNoAuthRetrofit(
        @NoAuth okHttpClient: OkHttpClient,
        @BackendUrl url: String
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserDataSource(
        @NoAuth retrofit: Retrofit,
    ): UserDataSource {
        return retrofit.create(UserDataSource::class.java)
    }
}
