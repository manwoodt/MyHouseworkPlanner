package com.example.myhouseworkplanner.domain.repositories

import com.example.myhouseworkplanner.domain.model.CleaningItem
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

interface ItemRepository {
    fun getItemsByRoom(roomId: Long): Flow<List<CleaningItem>>
    fun getAllItems(): Flow<List<CleaningItem>>
    suspend fun insertItem(item: CleaningItem): Long
    suspend fun updateItem(item: CleaningItem)
    suspend fun deleteItem(item: CleaningItem)
    suspend fun markAsCleaned(itemId: Long, date: LocalDateTime = LocalDateTime.now())
}