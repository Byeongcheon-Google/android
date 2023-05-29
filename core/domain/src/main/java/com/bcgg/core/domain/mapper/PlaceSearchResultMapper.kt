package com.bcgg.core.domain.mapper

import com.bcgg.core.data.model.schedule.Place
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import kotlinx.datetime.LocalDate

fun PlaceSearchResultWithId.toPlace(scheduleId: Int, date: LocalDate): Place = Place(
    id = id,
    scheduleId = scheduleId,
    date = date,
    kakaoPlaceId = placeSearchResult.kakaoPlaceId,
    name = placeSearchResult.name,
    address = placeSearchResult.address,
    lat = placeSearchResult.lat,
    lng = placeSearchResult.lng,
    classification = placeSearchResult.classification,
    stayTimeHour = stayTimeHour
)

fun Place.toPlaceSearchResultWithId() = PlaceSearchResultWithId(
    id = id,
    placeSearchResult = PlaceSearchResult(
        kakaoPlaceId, name, address, lat, lng, classification
    ),
    stayTimeHour = stayTimeHour
)