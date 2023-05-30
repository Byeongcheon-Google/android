package com.bcgg.core.domain.repository

import com.bcgg.core.data.source.recommend.RecommendDataSource
import com.bcgg.core.util.PlaceSearchResult
import com.bcgg.core.util.Classification
import com.bcgg.core.util.Result
import com.bcgg.core.util.toFailure
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class RecommendRepository @Inject constructor(
    private val recommendDataSource: RecommendDataSource
) {
    fun getRecommendPlacesByKeyword(keyword: String) = flow<Result<List<PlaceSearchResult>>> {
        try {
            emit(
                Result.Success(
                    recommendDataSource.getRecommendPlacesWithDescription(
                        "${keyword}${System.currentTimeMillis()}".toRequestBody("text/plain".toMediaTypeOrNull())
                    )
                .mapIndexed { i, it ->
                        PlaceSearchResult(
                            kakaoPlaceId = "K${i}",
                            name = it.name,
                            address = it.address,
                            lat = it.latitude,
                            lng = it.longitude,
                            classification = when (it.type) {
                                "숙박" -> Classification.House
                                "음식점" -> Classification.Food
                                else -> Classification.Travel
                            }
                        )
                    }
                )
            )
        } catch (e: Exception) {
            emit(e.toFailure())
        }
    }

    fun getRecommendPlacesByAddress(addresses: List<String>) = flow<Result<List<PlaceSearchResult>>> {
        try {
            if(addresses.isEmpty()) {
                emit(Result.Success(emptyList()))
                return@flow
            }
            emit(
                Result.Success(
                    recommendDataSource.getRecommendPlacesWithDescription(
                        "${addresses.joinToString("")}${System.currentTimeMillis()}".toRequestBody("text/plain".toMediaTypeOrNull())
                    ).mapIndexed { i, it ->
                        PlaceSearchResult(
                            kakaoPlaceId = "A${i}",
                            name = it.name,
                            address = it.address,
                            lat = it.latitude,
                            lng = it.longitude,
                            classification = when (it.type) {
                                "숙박" -> Classification.House
                                "음식점" -> Classification.Food
                                else -> Classification.Travel
                            }
                        )
                    }
                )
            )
        } catch (e: Exception) {
            emit(e.toFailure())
        }
    }
}