package com.example.myhouseworkplanner.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myhouseworkplanner.data.db.entities.ItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM items WHERE isEnabled = 1 ORDER BY id ASC")
    fun getAllItems(): Flow<List<ItemEntity>>

    @Query("SELECT * FROM items WHERE roomId = :roomId AND isEnabled = 1")
    fun getItemsByRoom(roomId: Long): Flow<List<ItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: ItemEntity): Long

    @Update
    suspend fun updateItem(item: ItemEntity)

    @Delete
    suspend fun deleteItem(item: ItemEntity)

    @Query("UPDATE items SET lastCleanDate = :timestamp WHERE id = :itemId")
    suspend fun markAsCleaned(itemId: Long, timestamp: Long = System.currentTimeMillis())
}