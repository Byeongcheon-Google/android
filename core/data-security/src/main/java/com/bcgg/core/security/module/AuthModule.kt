package com.bcgg.core.security.module

import android.content.Context
import com.bcgg.core.networking.qualifiers.Auth
import com.bcgg.core.security.source.JwtTokenSecuredLocalDataSource
import com.bcgg.core.security.util.putAccessToken
import com.bcgg.core.util.ext.newRequest
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Auth
    @Provides
    @Singleton
    fun provideAuthInterceptor(
        jwtTokenSecuredLocalDataSource: JwtTokenSecuredLocalDataSource
    ): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val accessToken = jwtTokenSecuredLocalDataSource.getAccessToken() ?: ""
            val newRequest: Request = chain.request().newRequest {
                putAccessToken(name = "Authorization", accessToken = accessToken)
            }
            chain.proceed(newRequest)
        }
    }
}