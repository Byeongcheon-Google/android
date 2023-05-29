package com.bcgg.core.domain.module

import com.bcgg.core.data.source.chat.KtorWebSocketDataSource
import com.bcgg.core.data.source.user.UserAuthDataSource
import com.bcgg.core.domain.repository.ChatRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ChatRepositoryModule {

    @Provides
    @Singleton
    fun provideChatRepositoryFactory(
        ktorWebSocketDataSourceFactory: KtorWebSocketDataSource.Factory,
        userAuthDataSource: UserAuthDataSource
    ): ChatRepository.Factory = object : ChatRepository.Factory {
        override fun create(scope: CoroutineScope, scheduleId: Int): ChatRepository {
            return ChatRepository(
                ktorWebSocketDataSourceFactory.create(scope, scheduleId),
                userAuthDataSource
            )
        }
    }
}