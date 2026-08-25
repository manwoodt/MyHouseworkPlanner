package com.example.myhouseworkplanner.presentation

import com.example.myhouseworkplanner.domain.model.Room

sealed interface HomeUiEvent {
    data object OpenAddRoomDialog : HomeUiEvent
    data object DismissAddRoomDialog : HomeUiEvent
    data class AddRoom(val name: String, val icon: String) : HomeUiEvent
    data class DeleteRoom(val room: Room) : HomeUiEvent
    data object RetryLoading : HomeUiEvent
    data object ClearError : HomeUiEvent
}