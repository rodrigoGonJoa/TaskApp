package rodrigo.taskapp.feature_task.data

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.data.utils.OperationMapper.mapAddToResult
import rodrigo.taskapp.core.data.utils.OperationMapper.mapDeleteToResult
import rodrigo.taskapp.core.data.utils.OperationMapper.mapGetToResult
import rodrigo.taskapp.core.data.utils.OperationMapper.mapOperationToFlowResult
import rodrigo.taskapp.core.data.utils.OperationMapper.mapUpdateToResult
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.RoomError
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val transactionProvider: TransactionProvider
): TaskRepository {
    override suspend fun save(item: Task): Result<Long, RoomError> {
        return mapAddToResult(transactionProvider) {
            taskDao.saveTask(item.mapToEntity())
        }
    }

    override suspend fun update(item: Task): Result<Unit, RoomError> {
        return mapUpdateToResult(transactionProvider) {
            taskDao.updateTask(item.mapToEntity())
        }
    }

    override suspend fun delete(item: Task): Result<Unit, RoomError> {
        return mapDeleteToResult(transactionProvider) {
            taskDao.deleteTask(item.mapToEntity())
        }
    }

    override suspend fun getById(itemId: Long): Result<Task?, RoomError> {
        return mapGetToResult(transactionProvider) {
            taskDao.getTaskById(itemId)
        }
    }

    override fun getAllFlow(): Flow<Result<List<Task>, RoomError>> {
        return mapOperationToFlowResult {taskDao.getAllTasks()}
    }
}
