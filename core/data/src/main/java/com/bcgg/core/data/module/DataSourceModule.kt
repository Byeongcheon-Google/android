package com.bcgg.core.data.module

import android.util.Log
import com.bcgg.core.data.source.chat.ChatRoomDataSource
import com.bcgg.core.data.source.user.UserDataSource
import com.bcgg.core.data.source.user.UserFakeDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.naiksoftware.stomp.Stomp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideUserDataSource(): UserDataSource {
        return UserFakeDataSource()
    }
}
