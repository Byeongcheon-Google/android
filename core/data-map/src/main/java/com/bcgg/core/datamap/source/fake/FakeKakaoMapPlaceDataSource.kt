package com.bcgg.core.datamap.source.fake

import com.bcgg.core.datamap.response.KakaoMapPlaceResponse
import com.bcgg.core.datamap.source.KakaoMapPlaceDataSource
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FakeKakaoMapPlaceDataSource : KakaoMapPlaceDataSource {

    override suspend fun getPlace(
        query: String,
        lng: String,
        lat: String,
        radius: Int,
        page: Int
    ): KakaoMapPlaceResponse {
        delay(FakeValues.FAKE_DELAY)
        return Json.decodeFromString(FakeValues.fakeKakaoMapPlaceResponse)
    }
}
