package rodrigo.taskapp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorRoom

interface BaseRepository<MODEL> {
    suspend fun save(item: MODEL): Result<Long, ErrorRoom>
    suspend fun update(item: MODEL): Result<Unit, ErrorRoom>
    suspend fun delete(item: MODEL): Result<Unit, ErrorRoom>
    suspend fun getById(itemId: Long): Result<MODEL?, ErrorRoom>
    fun getAllFlow(): Flow<Result<List<MODEL>, ErrorRoom>>
}
