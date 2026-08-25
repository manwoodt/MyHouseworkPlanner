package com.example.myhouseworkplanner.di

import android.content.Context
import androidx.room.Room
import com.example.myhouseworkplanner.data.db.AppDatabase
import com.example.myhouseworkplanner.data.db.dao.ItemDao
import com.example.myhouseworkplanner.data.db.dao.RoomDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "housework_planner.db"
        ).build()
    }

    @Provides
    fun provideRoomDao(db: AppDatabase): RoomDao = db.roomDao

    @Provides
    fun provideItemDao(db: AppDatabase): ItemDao = db.itemDao
}