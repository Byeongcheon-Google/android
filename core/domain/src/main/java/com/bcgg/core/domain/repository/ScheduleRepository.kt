package com.bcgg.core.domain.repository

import com.bcgg.core.data.source.schedule.ScheduleAuthDataSource
import com.bcgg.core.domain.model.schedule.Schedule
import com.bcgg.core.domain.model.schedule.ScheduleDetail
import com.bcgg.core.util.Result
import com.bcgg.core.util.ext.toDbString
import com.bcgg.core.util.toFailure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleAuthDataSource: ScheduleAuthDataSource
) {
    fun getSchedules(): Flow<Result<List<Schedule>>> = flow {
        try {
            val schedule = scheduleAuthDataSource.listSchedules()
            emit(Result.Success(
                schedule.map {
                    Schedule(
                        it.id,
                        it.title,
                        it.modifiedDateTime,
                        it.dateCount,
                        it.destinations,
                        it.collaboratorUserIds
                    )
                }
            ))
        } catch (e: Exception) {
            emit(e.toFailure())
        }
    }

    fun newSchedule(): Flow<Result<Int>> = flow {
        try {
            emit(
                Result.Success(scheduleAuthDataSource.addSchedule(com.bcgg.core.data.model.schedule.Schedule(name = "새 여행 계획")))
            )
        } catch (e: Exception) {
            emit(e.toFailure())
        }
    }

    fun getInitialScheduleDetail(scheduleId: Int): Flow<Result<ScheduleDetail>> = flow {
        try {
            val scheduleDetailResponse = scheduleAuthDataSource.getScheduleDetail(scheduleId)

            val schedulePerDateDetailResponses = scheduleDetailResponse.schedulePerDates.map {
                scheduleAuthDataSource.getPerDateScheduleDetail(scheduleId, it.date.toDbString())
            }

            emit(Result.Success(
                ScheduleDetail(
                    id = scheduleDetailResponse.schedule.id,
                    title = scheduleDetailResponse.schedule.name,
                    ownerId = scheduleDetailResponse.schedule.ownerId,
                    schedulePerDates = schedulePerDateDetailResponses.map { perDate ->
                        ScheduleDetail.PerDate(
                            id = perDate.id,
                            date = perDate.date,
                            startTime = perDate.startTime,
                            endHopeTime = perDate.endHopeTime,
                            mealTimes = perDate.mealTimes,
                            startPlaceId = perDate.startPlaceId,
                            endPlaceId = perDate.endPlaceId,
                            places = perDate.places.map {
                                ScheduleDetail.PerDate.Place(
                                    id = it.id,
                                    scheduleId = it.scheduleId,
                                    date = it.date,
                                    kakaoPlaceId = it.kakaoPlaceId,
                                    name = it.name,
                                    address = it.address,
                                    lat = it.lat,
                                    lng = it.lng,
                                    classification = it.classification,
                                    stayTimeHour = it.stayTimeHour
                                )
                            }
                        )
                    }
                )
            ))
        } catch (e: Exception) {
            emit(e.toFailure())
        }
    }

    fun addCollaborator(scheduleId: Int, userId: String): Flow<Result<Unit>> = flow {
        try {
            scheduleAuthDataSource.addCollaborator(scheduleId, userId)
            emit(Result.Success(Unit))
        } catch (e: Exception) {
            emit(e.toFailure())
        }
    }
}