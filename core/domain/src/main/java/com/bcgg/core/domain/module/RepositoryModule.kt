package com.bcgg.core.domain.module

import com.bcgg.core.data.source.find.PathFinderDataSource
import com.bcgg.core.domain.repository.PathFinderRepository
import com.bcgg.core.domain.repository.PlanEditorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePathFinderRepository(
        pathFinderDataSource: PathFinderDataSource
    ) : PathFinderRepository {
        return PathFinderRepository(pathFinderDataSource)
    }
}