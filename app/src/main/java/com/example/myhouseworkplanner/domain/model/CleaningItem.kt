package com.example.myhouseworkplanner.domain.model

import java.time.LocalDate
import java.time.LocalDateTime

data class CleaningItem(
    val id: Long = 0,
    val roomId: Long,
    val name: String,
    val icon: String,
    val cleanIntervalDays: Int,
    val lastCleanDate: LocalDateTime,
    val status: CleanStatus,
    val isEnabled: Boolean = true
)