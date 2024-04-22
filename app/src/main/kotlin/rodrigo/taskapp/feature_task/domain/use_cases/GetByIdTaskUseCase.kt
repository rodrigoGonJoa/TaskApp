package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorTask
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class GetByIdTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(taskId: Long?): Result<Task, ErrorTask> {
        if (taskId == null) {
            logger.warn {"✘ Error: task id is null."}
            return Result.Error(ErrorTask.Domain.TASK_ID_NULL)
        }
        return when (val result = taskRepository.getById(taskId)) {
            is Result.Error -> {
                logger.error {"✘ Error: On getting a task by id."}
                Result.Error(ErrorTask.Database(result.error))
            }
            is Result.Success -> {
                if (result.data == null) {
                    logger.warn {"✘ Error: The gotten task is null."}
                    Result.Error(ErrorTask.Domain.NULL_TASK)
                }
                logger.info {"✔ Success: On getting a task by id."}
                Result.Success(result.data!!)
            }
        }
    }
}
