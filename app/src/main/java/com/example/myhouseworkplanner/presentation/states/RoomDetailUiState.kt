package com.example.myhouseworkplanner.presentation.states

import com.example.myhouseworkplanner.domain.model.CleaningItem
import com.example.myhouseworkplanner.domain.model.Room

data class RoomDetailUiState(
    val room: Room? = null,
    val items: List<CleaningItem> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val isAddItemDialogOpen: Boolean = false
)