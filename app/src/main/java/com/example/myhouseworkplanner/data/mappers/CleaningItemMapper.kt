package com.example.myhouseworkplanner.data.mappers

import com.example.myhouseworkplanner.data.db.entities.ItemEntity
import com.example.myhouseworkplanner.domain.model.CleaningItem
import com.example.myhouseworkplanner.сore.util.toEpochMillis

fun CleaningItem.toEntity(): ItemEntity {
    return ItemEntity(
        id = id,
        roomId = roomId,
        name = name,
        icon = icon,
        intervalValue = intervalValue,
        intervalUnit = intervalUnit.name,
        lastCleanDate = lastCleanDate.toEpochMillis(),
        isEnabled = isEnabled
    )
}