package com.bcgg.core.data.module

import com.bcgg.core.data.source.user.UserAuthDataSource
import com.bcgg.core.data.source.user.UserDataSource
import com.bcgg.core.data.source.user.UserFakeAuthDataSource
import com.bcgg.core.data.source.user.UserFakeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthDataSourceModule {

    @Provides
    @Singleton
    fun provideUserAuthDataSource(): UserAuthDataSource {
        return UserFakeAuthDataSource()
    }
}
