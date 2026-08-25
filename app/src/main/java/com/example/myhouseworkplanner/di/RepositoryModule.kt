package com.example.myhouseworkplanner.di

import com.example.myhouseworkplanner.data.repositoryImpl.ItemRepositoryImpl
import com.example.myhouseworkplanner.data.repositoryImpl.RoomRepositoryImpl
import com.example.myhouseworkplanner.domain.repositories.ItemRepository
import com.example.myhouseworkplanner.domain.repositories.RoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindItemRepository(
        impl: ItemRepositoryImpl
    ): ItemRepository

    @Binds
    @Singleton
    abstract fun bindRoomRepository(
        impl: RoomRepositoryImpl
    ): RoomRepository
}