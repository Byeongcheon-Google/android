package com.bcgg.core.datamap.module

import com.bcgg.core.datamap.BuildConfig
import com.bcgg.core.datamap.source.NaverMapPlaceDataSource
import com.bcgg.core.datamap.source.fake.FakeNaverMapPlaceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NaverMapPlaceModule {
    @Provides
    @Singleton
    fun provideNaverMapPlaceDataSource(): NaverMapPlaceDataSource {
        if (BuildConfig.FLAVOR == "fake") {
            return FakeNaverMapPlaceDataSource()
        }

        return FakeNaverMapPlaceDataSource()
    }
}
