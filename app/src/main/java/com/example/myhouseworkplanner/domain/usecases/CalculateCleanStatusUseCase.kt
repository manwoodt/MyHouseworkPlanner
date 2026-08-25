package com.example.myhouseworkplanner.domain.usecases

import com.example.myhouseworkplanner.domain.model.CleanStatus
import com.example.myhouseworkplanner.domain.model.IntervalUnit
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import javax.inject.Inject

class CalculateCleanStatusUseCase @Inject constructor() {

    operator fun invoke(
        lastCleanDate: LocalDateTime,
        intervalValue: Int,
        intervalUnit: IntervalUnit,
        currentDateTime: LocalDateTime = LocalDateTime.now()
    ): CleanStatus {
        val nextDueDate = when (intervalUnit) {
            IntervalUnit.HOURS -> lastCleanDate.plusHours(intervalValue.toLong())
            IntervalUnit.DAYS -> lastCleanDate.plusDays(intervalValue.toLong())
            IntervalUnit.WEEKS -> lastCleanDate.plusWeeks(intervalValue.toLong())
            IntervalUnit.MONTHS -> lastCleanDate.plusMonths(intervalValue.toLong())
        }

        // Считаем интервал и прошедшее время в секундах для максимальной точности
        val totalIntervalSeconds =
            ChronoUnit.SECONDS.between(lastCleanDate, nextDueDate).coerceAtLeast(1)
        val secondsPassed =
            ChronoUnit.SECONDS.between(lastCleanDate, currentDateTime).coerceAtLeast(0)

        val progress = secondsPassed.toDouble() / totalIntervalSeconds

        return when {
            progress > 1.0 -> CleanStatus.OVERDUE           // Сильно просрочено (> 100%)
            progress >= 0.75 -> CleanStatus.NEEDS_CLEANING  // Пора убрать (75% - 100%)
            progress >= 0.50 -> CleanStatus.DUE_SOON        // Скоро пора (50% - 75%)
            else -> CleanStatus.CLEAN                       // Чисто (< 50%)
        }
    }
}