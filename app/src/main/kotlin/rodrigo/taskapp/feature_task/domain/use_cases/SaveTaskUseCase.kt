package rodrigo.taskapp.feature_task.domain.use_cases

import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.processReturn
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import rodrigo.taskapp.feature_task.domain.setTaskId
import rodrigo.taskapp.feature_task.domain.updateDateTimeFields
import javax.inject.Inject

class SaveTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskVerification: TaskVerification
) {
    operator fun invoke(task: Task): Result<Task, Error> {
        return taskVerification.invoke(task).processReturn {
            val updatedFieldsTask = task.updateDateTimeFields()
            taskRepository.save(updatedFieldsTask).processReturn {result ->
                Result.Success(updatedFieldsTask.setTaskId(result.data))
            }
        }
    }
}

