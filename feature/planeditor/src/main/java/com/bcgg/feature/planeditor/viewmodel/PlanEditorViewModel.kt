package com.bcgg.feature.planeditor.viewmodel

import androidx.lifecycle.viewModelScope
import com.bcgg.core.data.response.chat.ChatMessageType
import com.bcgg.core.domain.model.Classification
import com.bcgg.core.domain.model.User
import com.bcgg.core.domain.model.editor.map.PlaceSearchResult
import com.bcgg.core.domain.model.editor.map.PlaceSearchResultWithId
import com.bcgg.core.domain.repository.ChatRepository
import com.bcgg.core.domain.repository.MapPlaceRepository
import com.bcgg.core.domain.repository.UserRepository
import com.bcgg.core.ui.viewmodel.BaseViewModel
import com.bcgg.core.util.ext.collectOnFailure
import com.bcgg.core.util.ext.collectOnSuccess
import com.bcgg.feature.planeditor.compose.state.OptionsUiState
import com.bcgg.feature.planeditor.compose.state.PlanEditorMapUiState
import com.bcgg.feature.planeditor.compose.state.PlanEditorOptionsUiStatePerDate
import com.bcgg.feature.planeditor.compose.state.initialPlanEditorOptionsUiStatePerDate
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.StringBuilder
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class PlanEditorViewModel @Inject constructor(
    private val mapPlaceRepository: MapPlaceRepository,
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) : BaseViewModel() {

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
            chatRepository.topic.collectLatest {
                if (it.sender == getUser().userId) return@collectLatest
                val value = it.getValue() ?: return@collectLatest

                when (it.getChatMessageType()) {
                    ChatMessageType.Title -> {
                        _optionsUiState.value = _optionsUiState.value.copy(name = value)
                    }
                    ChatMessageType.ViewingLocation -> {
                        val newMap = _mapUiState.value.otherMapPositions.toMutableMap()
                        newMap[it.sender] = Json.decodeFromString(value)
                        _mapUiState.value = _mapUiState.value.copy(otherMapPositions = newMap)
                    }
                }
            }
        }

        viewModelScope.launch {
            userRepository.getUser().collectOnSuccess {
                _optionsUiState.value = _optionsUiState.value.copy(activeUsers = listOf(it))
            }.collectOnFailure {
                _errorMessage.emit("사용자 정보를 가져오지 못했습니다")
            }
        }
    }

    private suspend fun getUser(): User {
        if (_user == null) userRepository.getUser().collectOnSuccess { _user = it }
            .collectOnFailure { _errorMessage.emit("사용자 정보를 가져오는 중 오류가 발생했습니다.") }
        return _user!!
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
                mapSearchResult.collectLatest {
                    _mapUiState.value = _mapUiState.value.copy(
                        isSearching = false
                    )
                    return@collectLatest
                }
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
    }

    fun updateStartPlace(localDate: LocalDate, startPlace: PlaceSearchResultWithId) {
        val newValue =
            _optionsUiStatePerDate.value[localDate] ?: initialPlanEditorOptionsUiStatePerDate

        val newMap = _optionsUiStatePerDate.value.toMutableMap()
        newMap[localDate] = newValue.copy(startPlaceSearchResult = startPlace)

        _optionsUiStatePerDate.value = newMap
    }

    fun updateEndPlace(localDate: LocalDate, endPlace: PlaceSearchResultWithId) {
        val newValue =
            _optionsUiStatePerDate.value[localDate] ?: initialPlanEditorOptionsUiStatePerDate

        val newMap = _optionsUiStatePerDate.value.toMutableMap()
        newMap[localDate] = newValue.copy(endPlaceSearchResult = endPlace)

        _optionsUiStatePerDate.value = newMap
    }

    fun addItem(placeSearchResult: PlaceSearchResult) =
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.getUser()
                .collectOnSuccess { (userId) ->
                    val localDate = mapUiState.value.selectedDate
                    val newValue =
                        _optionsUiStatePerDate.value[localDate]
                            ?: initialPlanEditorOptionsUiStatePerDate

                    val newMap = _optionsUiStatePerDate.value.toMutableMap()
                    if (newValue.searchResultMaps.size >= 10) {
                        _errorMessage.emit("하루에 최대 10개의 여행지만 추가할 수 있습니다.")
                        return@collectOnSuccess
                    }
                    val newList = newValue.searchResultMaps.plusElement(
                        PlaceSearchResultWithId(userId, placeSearchResult)
                    )
                    newMap[localDate] = newValue.copy(
                        searchResultMaps = newList,
                        startPlaceSearchResult = if (newValue.startPlaceSearchResult == null && newList.isNotEmpty()) newList.first() else newValue.startPlaceSearchResult,
                        endPlaceSearchResult = if (newValue.endPlaceSearchResult == null && newList.isNotEmpty()) newList.first() else newValue.endPlaceSearchResult
                    )

                    _optionsUiStatePerDate.value = newMap
                    _mapUiState.value = _mapUiState.value.copy(
                        addedSearchResults = newMap[localDate]?.searchResultMaps?.map { it.placeSearchResult }
                            ?: emptyList()
                    )
                }
                .collectOnFailure {
                    _errorMessage.emit(it)
                }
            _isLoading.value = false
        }

    fun removeItem(placeSearchResult: PlaceSearchResultWithId) =
        viewModelScope.launch {
            _isLoading.value = true
            userRepository.getUser()
                .collectOnSuccess { userId ->
                    val localDate = mapUiState.value.selectedDate
                    val newValue =
                        _optionsUiStatePerDate.value[localDate]
                            ?: initialPlanEditorOptionsUiStatePerDate

                    val newMap = _optionsUiStatePerDate.value.toMutableMap()
                    val newList = newValue.searchResultMaps.filter {
                        it.placeSearchResult != placeSearchResult.placeSearchResult
                    }
                    val newStartPlace = when {
                        newList.contains(newValue.startPlaceSearchResult) -> newValue.startPlaceSearchResult
                        newList.isNotEmpty() -> newList.first()
                        else -> null
                    }
                    val newEndPlace = when {
                        newList.contains(newValue.endPlaceSearchResult) -> newValue.endPlaceSearchResult
                        newList.isNotEmpty() -> newList.last()
                        else -> null
                    }
                    newMap[localDate] = newValue.copy(
                        searchResultMaps = newList,
                        startPlaceSearchResult = newStartPlace,
                        endPlaceSearchResult = newEndPlace
                    )

                    _optionsUiStatePerDate.value = newMap
                    _mapUiState.value = _mapUiState.value.copy(
                        addedSearchResults = newMap[localDate]?.searchResultMaps?.map { it.placeSearchResult }
                            ?: emptyList()
                    )
                }
                .collectOnFailure {
                    _errorMessage.emit(it)
                }
            _isLoading.value = false
        }

    fun changeStayTime(placeSearchResult: PlaceSearchResultWithId, stayTime: Int) {
        if (_optionsUiStatePerDate.value[_optionsUiState.value.selectedDate] == null) return
        val newMap = _optionsUiStatePerDate.value.toMutableMap()
        newMap[_optionsUiState.value.selectedDate] =
            newMap[_optionsUiState.value.selectedDate]!!.copy(
                searchResultMaps = newMap[_optionsUiState.value.selectedDate]!!.searchResultMaps.map {
                    if (it.placeSearchResult == placeSearchResult.placeSearchResult)
                        return@map it.copy(stayTimeHour = stayTime)

                    it
                }
            )
        _optionsUiStatePerDate.value = newMap
    }

    fun changeClassification(
        placeSearchResult: PlaceSearchResultWithId,
        classification: Classification
    ) {
        if (_optionsUiStatePerDate.value[_optionsUiState.value.selectedDate] == null) return
        val newMap = _optionsUiStatePerDate.value.toMutableMap()
        newMap[_optionsUiState.value.selectedDate] =
            newMap[_optionsUiState.value.selectedDate]!!.copy(
                searchResultMaps = newMap[_optionsUiState.value.selectedDate]!!.searchResultMaps.map {
                    if (it.placeSearchResult == placeSearchResult.placeSearchResult)
                        return@map it.copy(classification = classification)

                    it
                }
            )
        _optionsUiStatePerDate.value = newMap
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

    fun sendViewingPosition(lat: Double, lng: Double) = viewModelScope.launch {
        chatRepository.updateViewingPosition(lat, lng)
    }
}
