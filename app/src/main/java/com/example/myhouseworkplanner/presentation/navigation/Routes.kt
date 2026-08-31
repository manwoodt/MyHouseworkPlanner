package com.example.myhouseworkplanner.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

// Экран комнаты (с обязательным аргументом roomId — data class)
@Serializable
data class RoomDetailRoute(val roomId: Long)
