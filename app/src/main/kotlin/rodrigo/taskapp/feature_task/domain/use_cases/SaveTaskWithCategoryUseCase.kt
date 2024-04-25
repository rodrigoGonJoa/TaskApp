package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.TaskError
import rodrigo.taskapp.feature_category.domain.use_cases.SaveCategoryUseCase
import rodrigo.taskapp.feature_task.domain.TaskException
import rodrigo.taskapp.feature_task.domain.Task
import javax.inject.Inject

class SaveTaskWithCategoryUseCase @Inject constructor(
    private val transactionProvider: TransactionProvider,
    private val saveTaskUseCase: SaveTaskUseCase,
    private val saveCategoryUseCase: SaveCategoryUseCase
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(task: Task): Result<Task, TaskError> {
        return try {
            transactionProvider.runAsTransaction {
                val categoryId = if (task.category != null) {
                    when (val result = saveCategoryUseCase.invoke(task.category)) {
                        is Result.Error -> throw TaskException(TaskError.Category(result.error))
                        is Result.Success -> result.data.modelId
                    }
                } else null
                val resultTask = run {
                    when (val result = saveTaskUseCase.invoke(task.setCategoryId(categoryId))) {
                        is Result.Error -> throw TaskException(result.error)
                        is Result.Success -> result.data.modelId
                    }
                }
                logger.info {"✔ Success: Save task with category."}
                Result.Success(task.copy().setId(resultTask).setCategoryId(categoryId))
            }
        } catch (exception: TaskException) {
            Result.Error(exception.error, exception)
        }
    }
}


