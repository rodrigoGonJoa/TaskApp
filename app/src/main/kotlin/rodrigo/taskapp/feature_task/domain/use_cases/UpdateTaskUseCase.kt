package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.TaskError
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val taskVerification: TaskVerification
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(task: Task): Result<Task, TaskError> {
        return when (val resultVerification = taskVerification.invoke(task)) {
            is Result.Error -> {
                logger.error {"✘ Error: Task verification."}
                Result.Error(resultVerification.error)
            }
            is Result.Success -> {
                logger.info {"✔ Success: Task verification."}
                updateTask(task)
            }
        }
    }
    private suspend fun updateTask(task: Task): Result<Task, TaskError> {
        val updatedTask = task.modified()
        return when (val resultDatabase = taskRepository.update(updatedTask)) {
            is Result.Error -> {
                logger.error {"✘ Error: Update task."}
                Result.Error(TaskError.Database(resultDatabase.error))
            }
            is Result.Success -> {
                logger.info {"✔ Success: Update task."}
                Result.Success(updatedTask)
            }
        }
    }
}
