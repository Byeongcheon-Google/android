package com.bcgg.core.domain.module

import com.bcgg.core.datamap.source.NaverMapPlaceDataSource
import com.bcgg.core.domain.repository.MapPlaceRepository
import com.bcgg.core.networking.qualifiers.NoAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapPlaceModule {
    @NoAuth
    @Provides
    @Singleton
    fun provideMapPlaceRepository(
        naverMapPlaceDataSource: NaverMapPlaceDataSource
    ): MapPlaceRepository {
        return MapPlaceRepository(naverMapPlaceDataSource)
    }
}
