package com.example.myhouseworkplanner.presentation.states

import com.example.myhouseworkplanner.domain.model.CleaningItem
import com.example.myhouseworkplanner.domain.model.IntervalUnit

sealed interface RoomDetailUiEvent {
    data object OpenAddItemDialog : RoomDetailUiEvent
    data object DismissAddItemDialog : RoomDetailUiEvent
    data class AddItem(
        val name: String,
        val icon: String,
        val intervalValue: Int,
        val intervalUnit: IntervalUnit
    ) : RoomDetailUiEvent
    data class MarkAsCleaned(val itemId: Long) : RoomDetailUiEvent
    data class DeleteItem(val item: CleaningItem) : RoomDetailUiEvent
    data object ClearError : RoomDetailUiEvent
}