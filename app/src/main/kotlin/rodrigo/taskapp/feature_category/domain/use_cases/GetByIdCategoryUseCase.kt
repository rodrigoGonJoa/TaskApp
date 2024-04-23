package rodrigo.taskapp.feature_category.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.CategoryError
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import javax.inject.Inject

class GetByIdCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(categoryId: Long?): Result<Category, CategoryError> {
        if (categoryId == null) {
            logger.warn {"✘ Error: The task id is null."}
            return Result.Error(CategoryError.Domain.NULL_CATEGORY_ID)
        }
        return when (val result = categoryRepository.getById(categoryId)) {
            is Result.Error -> {
                logger.error {"✘ Error: Getting a task by id."}
                Result.Error(CategoryError.Database(result.error))
            }
            is Result.Success -> {
                if (result.data == null) {
                    logger.warn {"✘ Error: The gotten task is null."}
                    Result.Error(CategoryError.Domain.NULL_CATEGORY)
                }
                logger.info {"✔ Success: Getting a task by id."}
                Result.Success(result.data!!)
            }
        }
    }
}
