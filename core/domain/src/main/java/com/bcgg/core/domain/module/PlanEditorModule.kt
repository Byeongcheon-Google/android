package com.bcgg.core.domain.module

import com.bcgg.core.domain.repository.PlanEditorRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent

@Module
@InstallIn(ActivityRetainedComponent::class)
object PlanEditorModule {

    @Provides
    fun providePlanEditorRepository() : PlanEditorRepository {
        return PlanEditorRepository()
    }
}