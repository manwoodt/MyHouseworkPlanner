package com.example.myhouseworkplanner.data.repositoryImpl
import com.example.myhouseworkplanner.data.db.dao.ItemDao
import com.example.myhouseworkplanner.data.mappers.toDomain
import com.example.myhouseworkplanner.data.mappers.toEntity
import com.example.myhouseworkplanner.domain.repositories.ItemRepository
import com.example.myhouseworkplanner.domain.model.CleaningItem
import com.example.myhouseworkplanner.domain.usecases.CalculateCleanStatusUseCase
import com.example.myhouseworkplanner.сore.util.toEpochMillis
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(
    private val itemDao: ItemDao,
    private val calculateCleanStatus: CalculateCleanStatusUseCase
) : ItemRepository {

    override fun getItemsByRoom(roomId: Long): Flow<List<CleaningItem>> {
        return itemDao.getItemsByRoom(roomId).map { list ->
            list.map { it.toDomain(calculateCleanStatus) }
        }
    }

    override fun getAllItems(): Flow<List<CleaningItem>> {
        return itemDao.getAllItems().map { list ->
            list.map { it.toDomain(calculateCleanStatus) }
        }
    }

    override suspend fun insertItem(item: CleaningItem): Long {
        return itemDao.insertItem(item.toEntity())
    }

    override suspend fun updateItem(item: CleaningItem) {
        itemDao.updateItem(item.toEntity())
    }

    override suspend fun deleteItem(item: CleaningItem) {
        itemDao.deleteItem(item.toEntity())
    }

    override suspend fun markAsCleaned(itemId: Long, date: LocalDateTime) {
        val millis = date.toEpochMillis()
        itemDao.markAsCleaned(itemId, millis)
    }
}