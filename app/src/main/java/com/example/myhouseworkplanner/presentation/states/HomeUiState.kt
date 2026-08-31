package com.example.myhouseworkplanner.presentation.states

import com.example.myhouseworkplanner.domain.model.Room

data class HomeUiState(
    val rooms: List<Room> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isAddRoomDialogOpen: Boolean = false
)