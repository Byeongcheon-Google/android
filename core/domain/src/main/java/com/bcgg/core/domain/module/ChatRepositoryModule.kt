package com.bcgg.core.domain.module

import com.bcgg.core.data.source.chat.ChatRemoteDataSource
import com.bcgg.core.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ChatRepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideChatRepository(
        chatRemoteDataSource: ChatRemoteDataSource
    ): ChatRepository = ChatRepository(chatRemoteDataSource)
}