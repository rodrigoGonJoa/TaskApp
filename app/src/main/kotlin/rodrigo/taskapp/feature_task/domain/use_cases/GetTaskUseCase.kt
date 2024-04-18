package rodrigo.taskapp.feature_task.domain.use_cases

import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.TaskError
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class GetTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(taskId: Long?): Result<Task, Error> {
        if (taskId == null) {
            return Result.Error(TaskError.TaskIdNull)
        }
        return taskRepository.getById(taskId).processReturn {result ->
            if(result.data == null){
                Result.Error(TaskError.NullTask)
            }
            Result.Success(result.data!!)
        }
    }
}
