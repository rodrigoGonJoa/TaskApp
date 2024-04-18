package rodrigo.taskapp.feature_task.domain.use_cases

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskError
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class GetAllTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(): Flow<Result<List<Task>, Error>> {
        return taskRepository.getAllFlow().processReturn {result ->
            if (result.data.isEmpty()) {
                Result.Error(TaskError.EmptyTaskList)
            }
            Result.Success(result.data)
        }
    }
}
