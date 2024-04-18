package rodrigo.taskapp.feature_task.domain.use_cases

import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskVerification: TaskVerification
) {
    suspend operator fun invoke(task: Task): Result<Task, Error>{
        return taskVerification.invoke(task).processReturn {
            val modifiedTask = task.modified()
            taskRepository.update(modifiedTask).processReturn {
                Result.Success(modifiedTask)
            }
        }
    }
}
