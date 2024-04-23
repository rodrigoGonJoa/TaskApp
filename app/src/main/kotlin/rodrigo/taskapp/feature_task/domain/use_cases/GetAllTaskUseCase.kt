package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorTask
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class GetAllTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    operator fun invoke(): Flow<Result<List<Task>, ErrorTask>> {
        return taskRepository.getAllFlow().map {flowResult ->
            when (flowResult) {
                is Result.Error -> {
                    logger.error {"✘ Error: Getting a task list."}
                    Result.Error(ErrorTask.Database(flowResult.error))
                }
                is Result.Success -> {
                    if (flowResult.data.isEmpty()) {
                        logger.warn {"✘ Error: The gotten list is empty."}
                        Result.Error(ErrorTask.Domain.EMPTY_TASK_LIST)
                    }
                    logger.info {"✔ Success: Getting a task list."}
                    Result.Success(flowResult.data)
                }
            }
        }
    }
}
