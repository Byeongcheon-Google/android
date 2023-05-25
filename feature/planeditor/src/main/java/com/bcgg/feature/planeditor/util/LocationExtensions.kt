package com.bcgg.feature.planeditor.util

import com.bcgg.core.data.model.Location
import com.naver.maps.geometry.LatLng

fun Location.toLatLng() = LatLng(lat, lng)