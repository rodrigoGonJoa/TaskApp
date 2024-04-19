package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.error.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskVerification: TaskVerification
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(task: Task): Result<Task, Error> {
        return taskVerification.invoke(task).processReturn{
            logger.info {"✔ Success: Verifying."}
            val updatedFieldsTask = task.updateDateTimeFields()
            taskRepository.save(updatedFieldsTask).processReturn {result ->
                logger.info {"✔ Success: Saving."}
                Result.Success(updatedFieldsTask.setTaskId(result.data))
            }
        }
    }
}

