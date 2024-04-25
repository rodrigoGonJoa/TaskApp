package rodrigo.taskapp.feature_task.domain.use_cases

import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.TaskError
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Inject

class SaveTaskWithCategoryUseCase @Inject constructor(
    private val transactionProvider: TransactionProvider,
    private val taskRepository: TaskRepository,
    private val categoryRepository: CategoryRepository
) {
    suspend operator fun invoke(task: Task): Result<Task, TaskError> {
        return transactionProvider.runAsTransaction {
            val categoryId = if (task.category != null) {
                when (val result = categoryRepository.save(task.category)) {
                    is Result.Error -> return@runAsTransaction Result.Error(
                            TaskError.Database(result.error)
                    )

                    is Result.Success -> result.data
                }
            } else null
            val taskId = when (val result = taskRepository.save(task)) {
                is Result.Error -> return@runAsTransaction Result.Error(
                    TaskError.Database(result.error)
                )

                is Result.Success -> result.data
            }
            return@runAsTransaction Result.Success(
                task.copy().setId(taskId).setCategoryId(categoryId)
            )
        }
    }
}


