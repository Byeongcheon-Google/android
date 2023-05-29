package com.bcgg.core.domain.repository

import com.bcgg.core.data.model.chat.Chat
import com.bcgg.core.data.source.chat.KtorWebSocketDataSource
import com.bcgg.core.data.source.user.UserAuthDataSource
import com.bcgg.core.domain.mapper.toPlace
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import kotlinx.coroutines.CoroutineScope
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val ktorWebSocketDataSource: KtorWebSocketDataSource,
    private val userAuthDataSource: UserAuthDataSource
) {
    interface Factory {
        fun create(scope: CoroutineScope, scheduleId: Int): ChatRepository
    }

    val topic = ktorWebSocketDataSource.topic

    private val format = DateTimeFormatter.ofPattern("HH:mm")
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun updateTitle(newTitle: String) {
        ktorWebSocketDataSource.sendMessage(Chat.ChatTitle(newTitle))
    }

    suspend fun updateViewingPosition(lat: Double, lng: Double) {
        if (lat !in -90.0..90.0 || lng !in -180.0..180.0) return
        ktorWebSocketDataSource.sendMessage(Chat.ChatMapPosition(userAuthDataSource.getUserId(), lat, lng))
    }

    fun updateStartTime(date: LocalDate, startTime: LocalTime) {
        ktorWebSocketDataSource.sendMessageImmediately(
            Chat.ChatStartTime(
                date.toKotlinLocalDate(),
                startTime.toKotlinLocalTime()
            )
        )
    }

    fun updateEndTime(date: LocalDate, endTime: LocalTime) {
        ktorWebSocketDataSource.sendMessageImmediately(
            Chat.ChatEndTime(
                date.toKotlinLocalDate(),
                endTime.toKotlinLocalTime()
            )
        )
    }

    fun updateMealTimes(date: LocalDate, mealTimes: List<LocalTime>) {
        ktorWebSocketDataSource.sendMessageImmediately(
            Chat.ChatMealTimes(
                date.toKotlinLocalDate(),
                mealTimes.map { it.toKotlinLocalTime() })
        )
    }

    fun updatePoints(date: LocalDate, points: List<PlaceSearchResultWithId>) {
        ktorWebSocketDataSource.sendMessage(Chat.ChatPoints(
            date.toKotlinLocalDate(),
            points.map {
                it.toPlace(
                    ktorWebSocketDataSource.scheduleId,
                    date.toKotlinLocalDate()
                )
            }
        ))
    }

    fun updatePointsImmediately(date: LocalDate, points: List<PlaceSearchResultWithId>) {
        ktorWebSocketDataSource.sendMessageImmediately(Chat.ChatPoints(
            date.toKotlinLocalDate(),
            points.map {
                it.toPlace(
                    ktorWebSocketDataSource.scheduleId,
                    date.toKotlinLocalDate()
                )
            }
        ))
    }

    fun addPoint(date: LocalDate, point: PlaceSearchResultWithId) {
        ktorWebSocketDataSource.sendMessageImmediately(
            Chat.ChatAddPoint(
                date.toKotlinLocalDate(),
                point.toPlace(
                    ktorWebSocketDataSource.scheduleId,
                    date.toKotlinLocalDate()
                )
            )
        )
    }

    fun removePoint(date: LocalDate, point: PlaceSearchResultWithId) {
        ktorWebSocketDataSource.sendMessageImmediately(
            Chat.ChatRemovePoint(
                date.toKotlinLocalDate(),
                point.toPlace(
                    ktorWebSocketDataSource.scheduleId,
                    date.toKotlinLocalDate()
                )
            )
        )
    }

    fun updateStartPlace(date: LocalDate, startPlace: PlaceSearchResultWithId?) {
        ktorWebSocketDataSource.sendMessageImmediately(
            Chat.ChatStartPlace(
                date.toKotlinLocalDate(),
                startPlace?.toPlace(ktorWebSocketDataSource.scheduleId, date.toKotlinLocalDate())
            )
        )
    }

    fun updateEndPlace(date: LocalDate, endPlace: PlaceSearchResultWithId?) {
        ktorWebSocketDataSource.sendMessageImmediately(
            Chat.ChatEndPlace(
                date.toKotlinLocalDate(),
                endPlace?.toPlace(ktorWebSocketDataSource.scheduleId, date.toKotlinLocalDate())
            )
        )
    }

    fun close() = ktorWebSocketDataSource.close()
}