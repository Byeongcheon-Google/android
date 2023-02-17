package com.bcgg.core.datamap.module

import com.bcgg.core.datamap.constant.KakaoMapConstant
import com.bcgg.core.networking.qualifiers.KakaoMap
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KakaoMapUrlModule {

    @KakaoMap
    @Provides
    @Singleton
    fun provideKakaoMapUrl() = KakaoMapConstant.URL
}
