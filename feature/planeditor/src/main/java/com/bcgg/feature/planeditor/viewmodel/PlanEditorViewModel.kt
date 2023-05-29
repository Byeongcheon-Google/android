package com.bcgg.feature.planeditor.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bcgg.core.data.model.Location
import com.bcgg.core.data.model.chat.Chat
import com.bcgg.core.domain.mapper.toPlaceSearchResultWithId
import com.bcgg.core.domain.model.User
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import com.bcgg.core.domain.model.schedule.ScheduleDetail
import com.bcgg.core.domain.repository.ChatRepository
import com.bcgg.core.domain.repository.MapPlaceRepository
import com.bcgg.core.domain.repository.ScheduleRepository
import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.collectLatest
import com.bcgg.core.util.ext.collectLatestWithLoading
import com.bcgg.core.util.onFailure
import com.bcgg.core.util.onSuccess
import com.bcgg.feature.planeditor.compose.state.OptionsUiState
import com.bcgg.feature.planeditor.compose.state.PlanEditorMapUiState
import com.bcgg.feature.planeditor.compose.state.PlanEditorOptionsUiStatePerDate
import com.bcgg.feature.planeditor.compose.state.initialPlanEditorOptionsUiStatePerDate
import com.naver.maps.geometry.LatLng
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalTime
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class PlanEditorViewModel @AssistedInject constructor(
    private val mapPlaceRepository: MapPlaceRepository,
    private val userRepository: UserRepository,
    private val scheduleRepository: ScheduleRepository,
    private val chatRepositoryFactory: ChatRepository.Factory,
    @Assisted private val scheduleId: Int
) : BaseViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(scheduleId: Int): PlanEditorViewModel
    }

    private var chatRepository: ChatRepository = chatRepositoryFactory.create(viewModelScope, scheduleId)

    private var _user: User? = null

    private val _mapUiState = MutableStateFlow(PlanEditorMapUiState())
    val mapUiState = _mapUiState.asStateFlow()

    private val _optionsUiState = MutableStateFlow(OptionsUiState())
    val optionsUiState = _optionsUiState.asStateFlow()

    private val _optionsUiStatePerDate =
        MutableStateFlow(mapOf<LocalDate, PlanEditorOptionsUiStatePerDate>())
    val optionsUiStatePerDate = _optionsUiStatePerDate.asStateFlow()

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage = _errorMessage.asSharedFlow()

    private val _showConfirmDialogEvent = MutableSharedFlow<String>()
    val showConfirmDialogEvent = _showConfirmDialogEvent.asSharedFlow()

    private val _showInviteDialogEvent = MutableSharedFlow<Unit>()
    val showInviteeDialogEvent = _showInviteDialogEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            chatRepository.topic
                .catch {
                    Log.e("Stomp sub", it.message ?: "Unknown Error")
                }
                .collectLatest {
                    when (it) {
                        is Chat.ChatEndPlace -> {
                            val (date, endPlace) = it
                            val newValue =
                                _optionsUiStatePerDate.value[date.toJavaLocalDate()]
                                    ?: initialPlanEditorOptionsUiStatePerDate

                            val newMap = _optionsUiStatePerDate.value.toMutableMap()
                            newMap[date.toJavaLocalDate()] =
                                newValue.copy(endPlaceSearchResult = newValue.searchResultMaps.find { it == endPlace?.toPlaceSearchResultWithId() })

                            _optionsUiStatePerDate.value = newMap
                        }

                        is Chat.ChatEndTime -> {
                            val (date, endTime) = it
                            val newValue =
                                _optionsUiStatePerDate.value[date.toJavaLocalDate()]
                                    ?: initialPlanEditorOptionsUiStatePerDate
                            val newMap = _optionsUiStatePerDate.value.toMutableMap()
                            newMap[date.toJavaLocalDate()] = newValue.copy(endHopeTime = endTime.toJavaLocalTime())
                            _optionsUiStatePerDate.value = newMap
                        }

                        is Chat.ChatMapPosition -> {
                            val (userId, lat, lng) = it
                            val newMap = _mapUiState.value.otherMapPositions.toMutableMap()
                            newMap[userId] = Location(lat, lng)
                            _mapUiState.value = _mapUiState.value.copy(otherMapPositions = newMap)
                        }

                        is Chat.ChatMealTimes -> {
                            val (date, mealTimes) = it
                            val newValue =
                                _optionsUiStatePerDate.value[date.toJavaLocalDate()]
                                    ?: initialPlanEditorOptionsUiStatePerDate
                            val newMap = _optionsUiStatePerDate.value.toMutableMap()
                            newMap[date.toJavaLocalDate()] = newValue.copy(mealTimes = mealTimes.map { it.toJavaLocalTime() })

                            _optionsUiStatePerDate.value = newMap
                        }

                        is Chat.ChatPoints -> {
                            val (date, places) = it
                            val points = places.map { it.toPlaceSearchResultWithId() }
                            val newValue =
                                _optionsUiStatePerDate.value[date.toJavaLocalDate()]
                                    ?: initialPlanEditorOptionsUiStatePerDate

                            val newMap = _optionsUiStatePerDate.value.toMutableMap()
                            newMap[date.toJavaLocalDate()] = newValue.copy(
                                searchResultMaps = points.take(10),
                                startPlaceSearchResult = if (newValue.startPlaceSearchResult == null && points.isNotEmpty()) points.first() else newValue.startPlaceSearchResult,
                                endPlaceSearchResult = if (newValue.endPlaceSearchResult == null && points.isNotEmpty()) points.first() else newValue.endPlaceSearchResult
                            )

                            _optionsUiStatePerDate.value = newMap
                            _mapUiState.value = _mapUiState.value.copy(
                                addedSearchResults = newMap[date.toJavaLocalDate()]?.searchResultMaps?.map { it.placeSearchResult }
                                    ?: emptyList()
                            )
                        }

                        is Chat.ChatStartPlace -> {
                            val (date, startPlace) = it
                            val newValue =
                                _optionsUiStatePerDate.value[date.toJavaLocalDate()]
                                    ?: initialPlanEditorOptionsUiStatePerDate

                            val newMap = _optionsUiStatePerDate.value.toMutableMap()
                            newMap[date.toJavaLocalDate()] =
                                newValue.copy(startPlaceSearchResult = newValue.searchResultMaps.find { it == startPlace?.toPlaceSearchResultWithId() })

                            _optionsUiStatePerDate.value = newMap
                        }

                        is Chat.ChatStartTime -> {
                            val (date, startTime) = it
                            val newValue =
                                _optionsUiStatePerDate.value[date.toJavaLocalDate()]
                                    ?: initialPlanEditorOptionsUiStatePerDate
                            val newMap = _optionsUiStatePerDate.value.toMutableMap()
                            newMap[date.toJavaLocalDate()] = newValue.copy(startTime = startTime.toJavaLocalTime())
                            _optionsUiStatePerDate.value = newMap
                        }

                        is Chat.ChatTitle -> {
                            val (title) = it
                            _optionsUiState.value = _optionsUiState.value.copy(name = title)
                        }

                        is Chat.ChatCount -> {
                            val (count) = it
                            _optionsUiState.value = _optionsUiState.value.copy(activeUserCount = count)
                        }

                        else -> {}
                    }
                }
        }
    }

    fun newChatRepository() {
        this.chatRepository.close()
        this.chatRepository = chatRepositoryFactory.create(viewModelScope, scheduleId)
    }

    fun closeChat() {
        this.chatRepository.close()
    }

    fun initData(scheduleId: Int) = viewModelScope.launch {
        scheduleRepository.getInitialScheduleDetail(scheduleId)
            .collectLatestWithLoading(_isLoading) {
                onSuccess { scheduleDetail ->
                    var lastDate: LocalDate = LocalDate.now()

                    val map = scheduleDetail.schedulePerDates.map { perDate ->
                        lastDate = perDate.date.toJavaLocalDate()
                        fun ScheduleDetail.PerDate.Place.toPlaceSearchResultWithId() =
                            PlaceSearchResultWithId(
                                id = id,
                                placeSearchResult = PlaceSearchResult(
                                    kakaoPlaceId = this.kakaoPlaceId,
                                    name = this.name,
                                    address = this.address,
                                    lat = this.lat,
                                    lng = this.lng,
                                    classification = this.classification
                                ),
                                stayTimeHour = this.stayTimeHour
                            )

                        perDate.date.toJavaLocalDate() to PlanEditorOptionsUiStatePerDate(
                            searchResultMaps = perDate.places.map { it.toPlaceSearchResultWithId() },
                            startTime = perDate.startTime.toJavaLocalTime(),
                            endHopeTime = perDate.endHopeTime.toJavaLocalTime(),
                            mealTimes = perDate.mealTimes.map { it.toJavaLocalTime() },
                            startPlaceSearchResult = perDate.places.find { it.id == perDate.startPlaceId }
                                ?.toPlaceSearchResultWithId(),
                            endPlaceSearchResult = perDate.places.find { it.id == perDate.endPlaceId }
                                ?.toPlaceSearchResultWithId(),
                        )
                    }.toMap()

                    _optionsUiStatePerDate.value = map
                    _optionsUiState.value = _optionsUiState.value.copy(
                        name = scheduleDetail.title,
                        selectedDate = lastDate
                    )

                    _mapUiState.value = _mapUiState.value.copy(
                        addedSearchResults = map[lastDate]?.searchResultMaps?.map { it.placeSearchResult }
                            ?: emptyList()
                    )
                }
                onFailure {
                    _errorMessage.emit(it)
                }
            }
    }

    fun updateSearchText(text: String) {
        _mapUiState.value = _mapUiState.value.copy(search = text)
    }

    fun search(query: String, latLng: LatLng) {
        _mapUiState.value = _mapUiState.value.copy(isSearching = true)
        viewModelScope.launch {
            try {
                val mapSearchResult =
                    mapPlaceRepository.getPlace(query, latLng.longitude, latLng.latitude)
                _mapUiState.value = _mapUiState.value.copy(
                    placeSearchResult = mapSearchResult,
                    expanded = true
                )
            } catch (e: Exception) {
                _errorMessage.emit(e.message ?: "Unknown error")
            } finally {
                _mapUiState.value = _mapUiState.value.copy(isSearching = false)
            }
        }
    }

    fun selectSearchResult(position: Int) {
        _mapUiState.value = _mapUiState.value.copy(selectedSearchPosition = -1)
        _mapUiState.value = _mapUiState.value.copy(selectedSearchPosition = position)
    }

    fun expandSearchResult() {
        _mapUiState.value = _mapUiState.value.copy(expanded = true)
    }

    fun shrinkSearchResult() {
        _mapUiState.value = _mapUiState.value.copy(expanded = false)
    }

    fun changeSelectedDate(selectedDate: LocalDate) {
        _optionsUiState.value = _optionsUiState.value.copy(selectedDate = selectedDate)
        _mapUiState.value = _mapUiState.value.copy(
            selectedDate = selectedDate,
            addedSearchResults = _optionsUiStatePerDate
                .value[selectedDate]
                ?.searchResultMaps
                ?.map { it.placeSearchResult }
                ?: emptyList()
        )
    }

    fun setName(name: String) = viewModelScope.launch {
        _optionsUiState.value = _optionsUiState.value.copy(name = name)
        chatRepository.updateTitle(name)
    }

    fun updateStartTime(localDate: LocalDate, startTime: LocalTime) = viewModelScope.launch {
        val newValue =
            _optionsUiStatePerDate.value[localDate] ?: initialPlanEditorOptionsUiStatePerDate

        if (newValue.endHopeTime < startTime) {
            _errorMessage.emit(
                "종료 예정시간보다 늦은 시간을 선택할 수 없습니다."
            )
            return@launch
        }

        val newMap = _optionsUiStatePerDate.value.toMutableMap()
        newMap[localDate] = newValue.copy(startTime = startTime)

        _optionsUiStatePerDate.value = newMap
        chatRepository.updateStartTime(localDate, startTime)
    }

    fun updateEndHopeTime(localDate: LocalDate, endHopeTime: LocalTime) = viewModelScope.launch {
        val newValue =
            _optionsUiStatePerDate.value[localDate] ?: initialPlanEditorOptionsUiStatePerDate

        if (newValue.startTime > endHopeTime) {
            _errorMessage.emit(
                "시작 시간보다 빠른 시간을 선택할 수 없습니다."
            )
            return@launch
        }

        val newMap = _optionsUiStatePerDate.value.toMutableMap()
        newMap[localDate] = newValue.copy(endHopeTime = endHopeTime)

        _optionsUiStatePerDate.value = newMap
        chatRepository.updateEndTime(localDate, endHopeTime)
    }

    fun updateMealTimes(localDate: LocalDate, mealTimes: List<LocalTime>) = viewModelScope.launch {
        val newValue =
            _optionsUiStatePerDate.value[localDate] ?: initialPlanEditorOptionsUiStatePerDate

        mealTimes.forEach {
            if (newValue.startTime > it || newValue.endHopeTime < it) {
                _errorMessage.emit(
                    "식사 시간은 시작 시간과 종료 시간 사이로 설정해야 합니다."
                )
                return@launch
            }
        }

        val newMap = _optionsUiStatePerDate.value.toMutableMap()
        newMap[localDate] = newValue.copy(mealTimes = mealTimes)

        _optionsUiStatePerDate.value = newMap
        chatRepository.updateMealTimes(localDate, mealTimes)
    }

    fun updateStartPlace(localDate: LocalDate, startPlace: PlaceSearchResultWithId) =
        viewModelScope.launch {
            val newValue =
                _optionsUiStatePerDate.value[localDate] ?: initialPlanEditorOptionsUiStatePerDate

            val newMap = _optionsUiStatePerDate.value.toMutableMap()
            newMap[localDate] = newValue.copy(startPlaceSearchResult = startPlace)

            _optionsUiStatePerDate.value = newMap
            chatRepository.updateStartPlace(localDate, startPlace)
        }

    fun updateEndPlace(localDate: LocalDate, endPlace: PlaceSearchResultWithId) =
        viewModelScope.launch {
            val newValue =
                _optionsUiStatePerDate.value[localDate] ?: initialPlanEditorOptionsUiStatePerDate

            val newMap = _optionsUiStatePerDate.value.toMutableMap()
            newMap[localDate] = newValue.copy(endPlaceSearchResult = endPlace)

            _optionsUiStatePerDate.value = newMap
            chatRepository.updateEndPlace(localDate, endPlace)
        }

    fun addItem(placeSearchResult: PlaceSearchResult) =
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.getUser()
                .collectLatest(
                    onSuccess = { (userId) ->
                        val localDate = mapUiState.value.selectedDate
                        val newValue =
                            _optionsUiStatePerDate.value[localDate]
                                ?: initialPlanEditorOptionsUiStatePerDate

                        val newMap = _optionsUiStatePerDate.value.toMutableMap()
                        if (newValue.searchResultMaps.size >= 10) {
                            _errorMessage.emit("하루에 최대 10개의 여행지만 추가할 수 있습니다.")
                            return@collectLatest
                        }
                        chatRepository.addPoint(localDate, PlaceSearchResultWithId(-1, placeSearchResult, stayTimeHour = 1))
                    }, onFailure = {
                        _errorMessage.emit(it)
                    }
                )
            _isLoading.value = false
        }

    fun removeItem(placeSearchResult: PlaceSearchResultWithId) =
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.getUser()
                .collectLatest(
                    onSuccess = { userId ->
                        val localDate = mapUiState.value.selectedDate
                        chatRepository.removePoint(localDate, placeSearchResult)
                    },
                    onFailure = {
                        _errorMessage.emit(it)
                    }
                )
            _isLoading.value = false
        }

    fun changeStayTime(placeSearchResult: PlaceSearchResultWithId, stayTime: Int) =
        viewModelScope.launch {
            if (_optionsUiStatePerDate.value[_optionsUiState.value.selectedDate] == null) return@launch
            val newMap = _optionsUiStatePerDate.value.toMutableMap()
            val newList = newMap[_optionsUiState.value.selectedDate]!!.searchResultMaps.map {
                if (it.placeSearchResult == placeSearchResult.placeSearchResult)
                    return@map it.copy(stayTimeHour = stayTime)

                it
            }
            newMap[_optionsUiState.value.selectedDate] =
                newMap[_optionsUiState.value.selectedDate]!!.copy(
                    searchResultMaps = newList
                )
            _optionsUiStatePerDate.value = newMap
            chatRepository.updatePoints(_optionsUiState.value.selectedDate, newList)
        }

    fun showConfirmDialog() {
        val message = StringBuilder("\n")

        _optionsUiStatePerDate.value
            .filterValues {
                it.searchResultMaps.isNotEmpty()
            }
            .toList()
            .sortedBy {
                it.first
            }
            .forEach { (date, _) ->
                message.append(date.format(DateTimeFormatter.ISO_LOCAL_DATE))
                message.append("\n")
            }

        message.append("\n")
        message.append("해당 날짜의 여행 계획표를 만드려면 확인 버튼을 누르세요.")

        viewModelScope.launch {
            _showConfirmDialogEvent.emit(
                message.toString()
            )
        }
    }

    fun showInviteDialog() = viewModelScope.launch {
        _showInviteDialogEvent.emit(Unit)
    }

    fun addCollaborator(userId: String) = viewModelScope.launch {
        scheduleRepository.addCollaborator(scheduleId, userId)
            .collectLatestWithLoading(_isLoading) {
                onSuccess {
                    _errorMessage.emit("$userId 사용자를 추가하였습니다.")
                }
                onFailure {
                    _errorMessage.emit(it)
                }
            }
    }

    fun sendViewingPosition(lat: Double, lng: Double) = viewModelScope.launch {
        chatRepository.updateViewingPosition(lat, lng)
    }

    companion object {
        fun provideFactory(
            assistedFactory: PlanEditorViewModel.Factory,
            scheduleId: Int
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(scheduleId) as T
            }
        }
    }
}
