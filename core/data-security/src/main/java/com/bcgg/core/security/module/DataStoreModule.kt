package com.bcgg.core.security.module

import android.content.Context
import com.bcgg.core.security.source.JwtTokenSecuredLocalDataSource
import com.bcgg.core.security.source.JwtTokenSecuredLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideJwtTokenSecuredLocalDataSource(
        @ApplicationContext context: Context
    ) : JwtTokenSecuredLocalDataSource {
        return JwtTokenSecuredLocalDataSourceImpl(context)
    }
}
