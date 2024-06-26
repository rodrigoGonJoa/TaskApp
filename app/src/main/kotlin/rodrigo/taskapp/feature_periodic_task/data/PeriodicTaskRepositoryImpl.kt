package rodrigo.taskapp.feature_periodic_task.data

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.RoomError
import rodrigo.taskapp.feature_periodic_task.domain.PeriodicTask
import rodrigo.taskapp.feature_periodic_task.domain.PeriodicTaskRepository
import javax.inject.Inject

class PeriodicTaskRepositoryImpl @Inject constructor(
    private val periodicTaskDao: PeriodicTaskDao,
    private val transactionProvider: TransactionProvider
): PeriodicTaskRepository {
    override suspend fun save(item: PeriodicTask): Result<Long, RoomError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: PeriodicTask): Result<Unit, RoomError> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: PeriodicTask): Result<Unit, RoomError> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(itemId: Long): Result<PeriodicTask?, RoomError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(): Flow<Result<List<PeriodicTask>, RoomError>> {
        TODO("Not yet implemented")
    }
}
