package com.bcgg.feature.planeditor.util

import com.bcgg.core.data.model.Location
import com.bcgg.core.data.model.ChatMessage
import com.bcgg.core.data.model.ChatMessageCommand
import com.bcgg.core.domain.model.User
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