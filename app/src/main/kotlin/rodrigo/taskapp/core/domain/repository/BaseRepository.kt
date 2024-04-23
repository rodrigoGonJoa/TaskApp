package rodrigo.taskapp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.RoomError

interface BaseRepository<MODEL> {
    suspend fun save(item: MODEL): Result<Long, RoomError>
    suspend fun update(item: MODEL): Result<Unit, RoomError>
    suspend fun delete(item: MODEL): Result<Unit, RoomError>
    suspend fun getById(itemId: Long): Result<MODEL?, RoomError>
    fun getAllFlow(): Flow<Result<List<MODEL>, RoomError>>
}
