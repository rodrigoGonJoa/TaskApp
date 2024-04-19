package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.domain.utils.error.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.core.domain.utils.error.ErrorTask
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class GetAllTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    operator fun invoke(): Flow<Result<List<Task>, Error>> {
        return taskRepository.getAllFlow().processReturn {result ->
            if (result.data.isEmpty()) {
                logger.warn {"✘ Error: the gotten list is empty."}
                Result.Error(ErrorTask.Domain.EMPTY_TASK_LIST)
            }
            logger.info {"✔ Success: getting a list of task."}
            Result.Success(result.data)
        }
    }
}
