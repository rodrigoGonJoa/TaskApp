package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository

class DeleteTaskUseCase(
    private val taskRepository: TaskRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(task: Task): Result<Unit, Error>{
        return taskRepository.delete(task).processReturn {
            logger.info {"✔ Success: deleting a task."}
            Result.Success(Unit)
        }
    }
}
