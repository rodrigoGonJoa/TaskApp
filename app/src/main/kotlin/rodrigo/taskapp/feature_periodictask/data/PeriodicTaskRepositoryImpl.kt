package rodrigo.taskapp.feature_periodictask.data

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_periodictask.domain.PeriodicTask
import rodrigo.taskapp.feature_periodictask.domain.PeriodicTaskRepository
import javax.inject.Inject

class PeriodicTaskRepositoryImpl @Inject constructor(
    private val periodicTaskDao: PeriodicTaskDao,
    private val transactionProvider: TransactionProvider
): PeriodicTaskRepository {
    override suspend fun save(item: PeriodicTask): Result<Long, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: PeriodicTask): Result<Unit, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: PeriodicTask): Result<Unit, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(itemId: Long): Result<PeriodicTask?, Error> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(): Flow<Result<List<PeriodicTask>, Error>> {
        TODO("Not yet implemented")
    }
}