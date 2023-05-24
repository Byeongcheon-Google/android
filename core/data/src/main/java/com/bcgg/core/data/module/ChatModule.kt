package com.bcgg.core.data.module

import com.bcgg.core.data.source.chat.ChatRemoteDataSource
import com.bcgg.core.data.source.user.UserAuthDataSource
import com.bcgg.core.data.source.user.UserDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import java.util.concurrent.*
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object ChatModule {

    @Provides
    @ViewModelScoped
    fun provideChatDataSource(
        stompClient: StompClient,
        userAuthDataSource: UserAuthDataSource
    ): ChatRemoteDataSource = ChatRemoteDataSource(stompClient, userAuthDataSource)
}