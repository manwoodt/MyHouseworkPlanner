package com.example.myhouseworkplanner.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhouseworkplanner.domain.model.Room
import com.example.myhouseworkplanner.domain.repositories.RoomRepository
import com.example.myhouseworkplanner.presentation.HomeUiEvent
import com.example.myhouseworkplanner.presentation.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val roomRepository: RoomRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private var observeRoomsJob: Job? = null

    init {
        observeRooms()
    }

    private fun observeRooms() {
        observeRoomsJob?.cancel()
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        observeRoomsJob = viewModelScope.launch {
            roomRepository.getAllRooms()
                .catch { throwable ->
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = throwable.localizedMessage ?: "Не удалось загрузить комнаты"
                        )
                    }
                }
                .collect { rooms ->
                    _uiState.update { state ->
                        state.copy(
                            rooms = rooms,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.OpenAddRoomDialog -> {
                _uiState.update { it.copy(isAddRoomDialogOpen = true) }
            }
            is HomeUiEvent.DismissAddRoomDialog -> {
                _uiState.update { it.copy(isAddRoomDialogOpen = false) }
            }
            is HomeUiEvent.AddRoom -> {
                viewModelScope.launch {
                    runCatching {
                        val newRoom = Room(name = event.name, icon = event.icon)
                        roomRepository.insertRoom(newRoom)
                    }.onSuccess {
                        _uiState.update { it.copy(isAddRoomDialogOpen = false) }
                    }.onFailure { throwable ->
                        _uiState.update { it.copy(errorMessage = "Ошибка при создании комнаты: ${throwable.localizedMessage}") }
                    }
                }
            }
            is HomeUiEvent.DeleteRoom -> {
                viewModelScope.launch {
                    runCatching {
                        roomRepository.deleteRoom(event.room)
                    }.onFailure { throwable ->
                        _uiState.update { it.copy(errorMessage = "Ошибка при удалении комнаты: ${throwable.localizedMessage}") }
                    }
                }
            }
            is HomeUiEvent.RetryLoading -> {
                observeRooms()
            }
            is HomeUiEvent.ClearError -> {
                _uiState.update { it.copy(errorMessage = null) }
            }
        }
    }
}