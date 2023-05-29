package com.bcgg.core.data.module

import com.bcgg.core.data.qualifier.BackendUrl
import com.bcgg.core.data.source.find.PathFinderDataSource
import com.bcgg.core.data.source.schedule.ScheduleAuthDataSource
import com.bcgg.core.data.source.user.UserAuthDataSource
import com.bcgg.core.networking.qualifiers.Auth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataSourceModule {


    @Auth
    @Provides
    @Singleton
    fun provideAuthRetrofit(
        @Auth okHttpClient: OkHttpClient,
        @BackendUrl url: String
    ): Retrofit {

        val json = Json{ignoreUnknownKeys = true}

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideUserAuthDataSource(
        @Auth retrofit: Retrofit
    ): UserAuthDataSource {
        return retrofit.create(UserAuthDataSource::class.java)
    }

    @Provides
    @Singleton
    fun provideScheduleAuthDataSource(
        @Auth retrofit: Retrofit
    ): ScheduleAuthDataSource {
        return retrofit.create(ScheduleAuthDataSource::class.java)
    }

    @Provides
    @Singleton
    fun providePathFinderDataSource(
        @Auth retrofit: Retrofit
    ): PathFinderDataSource {
        return retrofit.create(PathFinderDataSource::class.java)
    }
}
