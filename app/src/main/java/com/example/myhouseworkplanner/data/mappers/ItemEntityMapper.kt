package com.example.myhouseworkplanner.data.mappers

import com.example.myhouseworkplanner.data.db.entities.ItemEntity
import com.example.myhouseworkplanner.domain.model.CleaningItem
import com.example.myhouseworkplanner.domain.model.IntervalUnit
import com.example.myhouseworkplanner.domain.usecases.CalculateCleanStatusUseCase
import com.example.myhouseworkplanner.сore.util.toLocalDateTime

fun ItemEntity.toDomain(calculateCleanStatus: CalculateCleanStatusUseCase): CleaningItem {
    val localDateTime = lastCleanDate.toLocalDateTime()

    val unit = runCatching {
        IntervalUnit.valueOf(intervalUnit)
    }.getOrDefault(IntervalUnit.DAYS)

    return CleaningItem(
        id = id,
        roomId = roomId,
        name = name,
        icon = icon,
        intervalValue = intervalValue,
        intervalUnit = unit,
        lastCleanDate = localDateTime,
        status = calculateCleanStatus(localDateTime, intervalValue, unit),
        isEnabled = isEnabled
    )
}