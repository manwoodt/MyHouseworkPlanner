package com.example.myhouseworkplanner.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myhouseworkplanner.data.db.dao.ItemDao
import com.example.myhouseworkplanner.data.db.dao.RoomDao
import com.example.myhouseworkplanner.data.db.entities.ItemEntity
import com.example.myhouseworkplanner.data.db.entities.RoomEntity

@Database(
    entities = [RoomEntity::class, ItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract val roomDao: RoomDao
    abstract val itemDao: ItemDao
}