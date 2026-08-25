package com.example.myhouseworkplanner.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rooms")
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val icon: String,
    val sortOrder: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)