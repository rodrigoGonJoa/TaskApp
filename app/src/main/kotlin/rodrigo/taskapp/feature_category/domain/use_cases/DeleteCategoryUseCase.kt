package rodrigo.taskapp.feature_category.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorCategory
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(category: Category): Result<Unit, ErrorCategory> {
        return when (val result = categoryRepository.delete(category)) {
            is Result.Error -> {
                logger.error {"✘ Error: Deleting a category."}
                Result.Error(ErrorCategory.Database(result.error))
            }

            is Result.Success -> {
                logger.info {"✔ Success: Deleting a category."}
                Result.Success(Unit)
            }
        }
    }
}
