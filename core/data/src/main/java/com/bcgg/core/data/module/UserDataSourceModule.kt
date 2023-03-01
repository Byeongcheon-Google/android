package com.bcgg.core.data.module

import com.bcgg.core.data.user.UserDataSource
import com.bcgg.core.data.user.UserFakeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataSourceModule {

    @Provides
    @Singleton
    fun provideUserDataSource(): UserDataSource {
        return UserFakeDataSource()
    }
}
