package com.bcgg.feature.planeditor.util

import com.bcgg.core.data.model.Location
import com.bcgg.core.data.response.chat.ChatMessage
import com.bcgg.core.data.response.chat.ChatMessageCommand
import com.bcgg.core.domain.model.User
import com.bcgg.core.domain.model.editor.chat.ChatEndPlace
import com.bcgg.core.domain.model.editor.chat.ChatEndTime
import com.bcgg.core.domain.model.editor.chat.ChatMealTimes
import com.bcgg.core.domain.model.editor.chat.ChatPoints
import com.bcgg.core.domain.model.editor.chat.ChatStartPlace
import com.bcgg.core.domain.model.editor.chat.ChatStartTime
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.time.LocalDate
import java.time.LocalTime

val json = Json {
    ignoreUnknownKeys = true
}

inline fun Flow<ChatMessage>.collectTitle(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, value: String) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.TITLE && user.userId != chatMessage.sender)

                action(
                    chatMessage.sender,
                    chatMessage.message
                )
        }
    }
    return this
}

inline fun Flow<ChatMessage>.collectViewingPosition(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, position: Location) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.VIEWINGLOCATION && user.userId != chatMessage.sender)

                action(
                    chatMessage.sender,
                    json.decodeFromString(chatMessage.message)
                )
        }
    }

    return this
}

inline fun Flow<ChatMessage>.collectStartTime(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, date: LocalDate, startTime: LocalTime) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.STARTTIME && user.userId != chatMessage.sender) {
                val chatStartTime = json.decodeFromString<ChatStartTime>(chatMessage.message)

                action(
                    chatMessage.sender,
                    chatStartTime.date.toJavaLocalDate(),
                    chatStartTime.startTime.toJavaLocalTime()
                )
            }
        }
    }

    return this
}

inline fun Flow<ChatMessage>.collectEndTime(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, date: LocalDate, endTime: LocalTime) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.ENDTIME && user.userId != chatMessage.sender) {

                val chatEndTime = json.decodeFromString<ChatEndTime>(chatMessage.message)

                action(
                    chatMessage.sender,
                    chatEndTime.date.toJavaLocalDate(),
                    chatEndTime.endTime.toJavaLocalTime()
                )
            }
        }
    }

    return this
}

inline fun Flow<ChatMessage>.collectMealTimes(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, date: LocalDate, mealTimes: List<LocalTime>) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.MEALTIMES && user.userId != chatMessage.sender) {

                val chatMealTimes = json.decodeFromString<ChatMealTimes>(chatMessage.message)

                action(
                    chatMessage.sender,
                    chatMealTimes.date.toJavaLocalDate(),
                    chatMealTimes.mealTimes.map { it.toJavaLocalTime() }
                )
            }
        }
    }

    return this
}

inline fun Flow<ChatMessage>.collectPoints(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, date: LocalDate, points: List<PlaceSearchResultWithId>) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.POINTS && user.userId != chatMessage.sender) {

                val chatPoints = json.decodeFromString<ChatPoints>(chatMessage.message)

                action(
                    chatMessage.sender,
                    chatPoints.date.toJavaLocalDate(),
                    chatPoints.points
                )
            }
        }
    }

    return this
}


inline fun Flow<ChatMessage>.collectStartPlace(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, date: LocalDate, startPlace: PlaceSearchResultWithId) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.STARTPLACE && user.userId != chatMessage.sender) {

                val chatStartPlace = json.decodeFromString<ChatStartPlace>(chatMessage.message)

                action(
                    chatMessage.sender,
                    chatStartPlace.date.toJavaLocalDate(),
                    chatStartPlace.startPlace
                )
            }
        }
    }

    return this
}

inline fun Flow<ChatMessage>.collectEndPlace(
    user: User,
    coroutineScope: CoroutineScope,
    crossinline action: suspend (userId: String, date: LocalDate, endPlace: PlaceSearchResultWithId) -> Unit
): Flow<ChatMessage> {
    coroutineScope.launch {
        collectLatest { chatMessage ->
            if (chatMessage.command == ChatMessageCommand.ENDPLACE && user.userId != chatMessage.sender) {

                val chatEndPlace = json.decodeFromString<ChatEndPlace>(chatMessage.message)

                action(
                    chatMessage.sender,
                    chatEndPlace.date.toJavaLocalDate(),
                    chatEndPlace.endPlace
                )
            }
        }
    }

    return this
}