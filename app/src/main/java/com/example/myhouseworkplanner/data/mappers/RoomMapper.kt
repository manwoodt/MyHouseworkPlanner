package com.example.myhouseworkplanner.data.mappers

import com.example.myhouseworkplanner.data.db.entities.RoomEntity
import com.example.myhouseworkplanner.domain.model.Room

fun RoomEntity.toDomain(overdueCount: Int = 0, totalCount: Int = 0): Room {
    return Room(
        id = id,
        name = name,
        icon = icon,
        sortOrder = sortOrder,
        overdueTasksCount = overdueCount,
        totalTasksCount = totalCount
    )
}

fun Room.toEntity(): RoomEntity {
    return RoomEntity(
        id = id,
        name = name,
        icon = icon,
        sortOrder = sortOrder
    )
}