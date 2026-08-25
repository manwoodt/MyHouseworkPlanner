package com.example.myhouseworkplanner.domain.model

enum class CleanStatus {
    CLEAN,           // Чисто (прошло < 50% интервала)
    DUE_SOON,        // Скоро пора (50% - 75%)
    NEEDS_CLEANING,  // Пора убрать (75% - 100%)
    OVERDUE          // Сильно просрочено (> 100%)
}