package rodrigo.taskapp.feature_task.data

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val transactionProvider: TransactionProvider
): TaskRepository {
    override suspend fun save(item: Task): Result<Long, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Task): Result<Unit, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Task): Result<Unit, Error> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(itemId: Long): Result<Task?, Error> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(): Flow<Result<List<Task>, Error>> {
        TODO("Not yet implemented")
    }
}
