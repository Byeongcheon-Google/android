package com.bcgg.core.datamap

import com.bcgg.core.datamap.source.fake.FakeKakaoMapPlaceDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MapDataSourceTest {

    @Test
    fun `getPlace response 테스트`() = runBlocking {
        val fakeDataSource = FakeKakaoMapPlaceDataSource()
        val response = fakeDataSource.getPlace(
            query = "asdf",
            lng = "",
            lat = "",
            page = 1
        )

        Assert.assertEquals(15, response.items.size)
        Assert.assertEquals("한국기술교육대학교 제2캠퍼스", response.items[0].name)
    }
}
