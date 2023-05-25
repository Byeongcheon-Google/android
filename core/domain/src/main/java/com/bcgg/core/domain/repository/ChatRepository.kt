package com.bcgg.core.domain.repository

import com.bcgg.core.data.model.Location
import com.bcgg.core.data.response.chat.ChatMessageCommand
import com.bcgg.core.data.source.chat.ChatRemoteDataSource
import com.bcgg.core.domain.model.editor.chat.ChatEndPlace
import com.bcgg.core.domain.model.editor.chat.ChatEndTime
import com.bcgg.core.domain.model.editor.chat.ChatMealTimes
import com.bcgg.core.domain.model.editor.chat.ChatPoints
import com.bcgg.core.domain.model.editor.chat.ChatStartPlace
import com.bcgg.core.domain.model.editor.chat.ChatStartTime
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalTime
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatRemoteDataSource: ChatRemoteDataSource
) {
    val topic = chatRemoteDataSource.topic
    private val format = DateTimeFormatter.ofPattern("HH:mm")
    private val json = Json {
        ignoreUnknownKeys = true
    }

    fun updateTitle(newTitle: String) {
        chatRemoteDataSource.sendMessage(ChatMessageCommand.TITLE, newTitle)
    }

    fun updateViewingPosition(lat: Double, lng: Double) {
        if(lat !in -90.0..90.0 || lng !in -180.0..180.0) return
        chatRemoteDataSource.sendMessage(
            ChatMessageCommand.VIEWINGLOCATION,
            json.encodeToString(Location(lat, lng))
        )
    }

    suspend fun updateStartTime(date: LocalDate, startTime: LocalTime) {
        chatRemoteDataSource.sendMessageWithoutBackPressure(
            ChatMessageCommand.STARTTIME,
            json.encodeToString(
                ChatStartTime(date.toKotlinLocalDate(), startTime.toKotlinLocalTime())
            )
        )
    }

    suspend fun updateEndTime(date: LocalDate, endTime: LocalTime) {
        chatRemoteDataSource.sendMessageWithoutBackPressure(
            ChatMessageCommand.ENDTIME,
            json.encodeToString(
                ChatEndTime(date.toKotlinLocalDate(), endTime.toKotlinLocalTime())
            )
        )
    }

    suspend fun updateMealTimes(date: LocalDate, mealTimes: List<LocalTime>) {
        chatRemoteDataSource.sendMessageWithoutBackPressure(
            ChatMessageCommand.MEALTIMES,
            json.encodeToString(
                ChatMealTimes(date.toKotlinLocalDate(), mealTimes.map { it.toKotlinLocalTime() })
            )
        )
    }

    suspend fun updatePoints(date: LocalDate, points: List<PlaceSearchResultWithId>) {
        chatRemoteDataSource.sendMessageWithoutBackPressure(
            ChatMessageCommand.POINTS,
            json.encodeToString(
                ChatPoints(date.toKotlinLocalDate(), points)
            )
        )
    }

    suspend fun updateStartPlace(date: LocalDate, startPlace: PlaceSearchResultWithId) {
        chatRemoteDataSource.sendMessageWithoutBackPressure(
            ChatMessageCommand.STARTPLACE,
            json.encodeToString(ChatStartPlace(date.toKotlinLocalDate(), startPlace))
        )
    }

    suspend fun updateEndPlace(date: LocalDate, endPlace: PlaceSearchResultWithId) {
        chatRemoteDataSource.sendMessageWithoutBackPressure(
            ChatMessageCommand.ENDPLACE,
            json.encodeToString(ChatEndPlace(date.toKotlinLocalDate(), endPlace))
        )
    }
}