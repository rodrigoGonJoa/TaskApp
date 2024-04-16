package rodrigo.taskapp.core.domain.repository

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result

interface BaseRepository<MODEL> {
    fun save(item: MODEL): Result<Long, Error>
    fun update(item: MODEL): Result<Unit, Error>
    fun delete(item: MODEL): Result<Unit, Error>
    fun getById(itemId: Long): Result<MODEL, Error>
    fun getAllFlow(itemId: Long): Flow<Result<List<MODEL>, Error>>
}
