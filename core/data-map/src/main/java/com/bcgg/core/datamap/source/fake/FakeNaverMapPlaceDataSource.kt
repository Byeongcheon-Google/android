package com.bcgg.core.datamap.source.fake

import com.bcgg.core.datamap.response.NaverMapPlaceResponse
import com.bcgg.core.datamap.source.NaverMapPlaceDataSource
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class FakeNaverMapPlaceDataSource : NaverMapPlaceDataSource {
    override suspend fun getPlace(query: String, display: Int): NaverMapPlaceResponse {

        return Json.decodeFromString(FakeValues.fakeNaverMapPlaceResponse)
    }
}
