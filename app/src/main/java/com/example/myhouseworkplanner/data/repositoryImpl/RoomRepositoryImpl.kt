package com.example.myhouseworkplanner.data.repositoryImpl

import com.example.myhouseworkplanner.data.db.dao.ItemDao
import com.example.myhouseworkplanner.data.db.dao.RoomDao
import com.example.myhouseworkplanner.data.mappers.toDomain
import com.example.myhouseworkplanner.data.mappers.toEntity
import com.example.myhouseworkplanner.domain.model.CleanStatus
import com.example.myhouseworkplanner.domain.model.Room
import com.example.myhouseworkplanner.domain.repositories.RoomRepository
import com.example.myhouseworkplanner.domain.usecases.CalculateCleanStatusUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: RoomDao,
    private val itemDao: ItemDao,
    private val calculateCleanStatus: CalculateCleanStatusUseCase
) : RoomRepository {

    override fun getAllRooms(): Flow<List<Room>> {
        return combine(
            roomDao.getAllRooms(),
            itemDao.getAllItems()
        ) { roomEntities, itemEntities ->
            val domainItems = itemEntities.map { it.toDomain(calculateCleanStatus) }

            roomEntities.map { roomEntity ->
                val roomItems = domainItems.filter { it.roomId == roomEntity.id }
                val overdueCount = roomItems.count {
                    it.status == CleanStatus.OVERDUE || it.status == CleanStatus.NEEDS_CLEANING
                }
                roomEntity.toDomain(
                    overdueCount = overdueCount,
                    totalCount = roomItems.size
                )
            }
        }
    }

    override suspend fun getRoomById(id: Long): Room? {
        return roomDao.getRoomById(id)?.toDomain()
    }

    override suspend fun insertRoom(room: Room): Long {
        return roomDao.insertRoom(room.toEntity())
    }

    override suspend fun updateRoom(room: Room) {
        roomDao.updateRoom(room.toEntity())
    }

    override suspend fun deleteRoom(room: Room) {
        roomDao.deleteRoom(room.toEntity())
    }
}