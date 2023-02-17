package com.bcgg.core.datamap.module

import com.bcgg.core.datamap.BuildConfig
import com.bcgg.core.datamap.source.KakaoMapPlaceDataSource
import com.bcgg.core.datamap.source.fake.FakeKakaoMapPlaceDataSource
import com.bcgg.core.datamap.source.retrofit.RetrofitKakaoMapPlaceDataSource
import com.bcgg.core.networking.qualifiers.KakaoMap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoMapPlaceModule {

    @Provides
    @Singleton
    fun provideKakaoMapPlaceDataSource(
        @KakaoMap retrofit: Retrofit
    ): KakaoMapPlaceDataSource {
        if (BuildConfig.FLAVOR == "fake") {
            return FakeKakaoMapPlaceDataSource()
        }

        return retrofit.create(RetrofitKakaoMapPlaceDataSource::class.java)
    }
}
