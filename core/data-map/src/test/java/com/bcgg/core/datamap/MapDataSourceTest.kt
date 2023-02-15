package com.bcgg.core.datamap

import com.bcgg.core.datamap.source.fake.FakeNaverMapPlaceDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class MapDataSourceTest {

    @Test
    fun `getPlace response 테스트`() = runBlocking {
        val fakeDataSource = FakeNaverMapPlaceDataSource()
        val response = fakeDataSource.getPlace("한기대", 5)

        Assert.assertEquals(5, response.items.size)
        Assert.assertEquals("한국기술교육대학교 제1캠퍼스", response.items[0].title)
    }
}
