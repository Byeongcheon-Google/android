package com.bcgg.core.datamap.module

import com.bcgg.core.datamap.BuildConfig
import com.bcgg.core.datamap.source.KakaoMapPlaceDataSource
import com.bcgg.core.datamap.source.fake.FakeKakaoMapPlaceDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoMapPlaceModule {
    @Provides
    @Singleton
    fun provideKakaoMapPlaceDataSource(): KakaoMapPlaceDataSource {
        if (BuildConfig.FLAVOR == "fake") {
            return FakeKakaoMapPlaceDataSource()
        }

        return FakeKakaoMapPlaceDataSource()
    }
}
