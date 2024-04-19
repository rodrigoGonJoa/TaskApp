package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.TaskError
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class GetByIdTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(taskId: Long?): Result<Task, Error> {
        if (taskId == null) {
            logger.warn {"✘ Error: task id is null."}
            return Result.Error(TaskError.TaskIdNull)
        }
        return taskRepository.getById(taskId).processReturn {result ->
            if (result.data == null) {
                logger.warn {"✘ Error: the gotten task is null."}
                Result.Error(TaskError.NullTask)
            }
            logger.info {"✔ Success: getting a task by id."}
            Result.Success(result.data!!)
        }
    }
}
