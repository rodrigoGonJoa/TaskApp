package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.Error
import rodrigo.taskapp.core.domain.utils.error.ErrorTask
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(task: Task): Result<Unit, Error>{
        return when(val result = taskRepository.delete(task)) {
            is Result.Error -> {
                logger.error {"✘ Error: On deleting a task."}
                Result.Error(ErrorTask.Database(result.error))
            }
            is Result.Success -> {
                logger.info {"✔ Success: On deleting a task."}
                Result.Success(Unit)
            }
        }
    }
}
