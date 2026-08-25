package com.example.myhouseworkplanner.domain.model

data class Room(
    val id: Long = 0,
    val name: String,
    val icon: String,
    val sortOrder: Int = 0,
    val overdueTasksCount: Int = 0,
    val totalTasksCount: Int = 0
)