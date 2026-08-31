package com.example.myhouseworkplanner.presentation.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.myhouseworkplanner.domain.model.CleanStatus
import com.example.myhouseworkplanner.domain.model.CleaningItem
import com.example.myhouseworkplanner.domain.repositories.ItemRepository
import com.example.myhouseworkplanner.domain.repositories.RoomRepository
import com.example.myhouseworkplanner.presentation.navigation.RoomDetailRoute
import com.example.myhouseworkplanner.presentation.states.RoomDetailUiEvent
import com.example.myhouseworkplanner.presentation.states.RoomDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class RoomDetailViewModel @Inject constructor(
    private val itemRepository: ItemRepository,
    private val roomRepository: RoomRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = savedStateHandle.toRoute<RoomDetailRoute>()
    val roomId: Long = route.roomId

    private val _uiState = MutableStateFlow(RoomDetailUiState())
    val uiState: StateFlow<RoomDetailUiState> = _uiState.asStateFlow()

    init {
        loadRoomData()
    }

    private fun loadRoomData() {
        viewModelScope.launch {
            val room = roomRepository.getRoomById(roomId)
            _uiState.update { it.copy(room = room) }

            itemRepository.getItemsByRoom(roomId)
                .catch { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = throwable.localizedMessage ?: "Ошибка загрузки списка"
                        )
                    }
                }
                .collect { items ->
                    _uiState.update { state ->
                        state.copy(
                            items = items,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun onEvent(event: RoomDetailUiEvent) {
        when (event) {
            is RoomDetailUiEvent.OpenAddItemDialog -> {
                _uiState.update { it.copy(isAddItemDialogOpen = true) }
            }
            is RoomDetailUiEvent.DismissAddItemDialog -> {
                _uiState.update { it.copy(isAddItemDialogOpen = false) }
            }
            is RoomDetailUiEvent.AddItem -> {
                viewModelScope.launch {
                    runCatching {
                        val newItem = CleaningItem(
                            roomId = roomId,
                            name = event.name,
                            icon = event.icon,
                            intervalValue = event.intervalValue,
                            intervalUnit = event.intervalUnit,
                            lastCleanDate = LocalDateTime.now(),
                            status = CleanStatus.CLEAN
                        )
                        itemRepository.insertItem(newItem)
                    }.onSuccess {
                        _uiState.update { it.copy(isAddItemDialogOpen = false) }
                    }.onFailure { throwable ->
                        _uiState.update { it.copy(errorMessage = "Ошибка добавления: ${throwable.localizedMessage}") }
                    }
                }
            }
            is RoomDetailUiEvent.MarkAsCleaned -> {
                viewModelScope.launch {
                    runCatching {
                        itemRepository.markAsCleaned(event.itemId, LocalDateTime.now())
                    }.onFailure { throwable ->
                        _uiState.update { it.copy(errorMessage = "Ошибка обновления: ${throwable.localizedMessage}") }
                    }
                }
            }
            is RoomDetailUiEvent.DeleteItem -> {
                viewModelScope.launch {
                    runCatching {
                        itemRepository.deleteItem(event.item)
                    }.onFailure { throwable ->
                        _uiState.update { it.copy(errorMessage = "Ошибка удаления: ${throwable.localizedMessage}") }
                    }
                }
            }
            is RoomDetailUiEvent.ClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }
}