package com.bcgg.core.domain.repository

import com.bcgg.core.util.PlanResultResponse
import com.bcgg.core.data.source.find.PathFinderDataSource
import com.bcgg.core.util.Result
import com.bcgg.core.util.toFailure
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class PathFinderRepository @Inject constructor(
    private val pathFinderDataSource: PathFinderDataSource
) {
    fun findPath(scheduleId: Int) = flow {
        try {
            val json = pathFinderDataSource.findPath(scheduleId)
            emit(Result.Success(json))
        } catch (e: Exception) {
            emit(e.toFailure())
        }
    }
}