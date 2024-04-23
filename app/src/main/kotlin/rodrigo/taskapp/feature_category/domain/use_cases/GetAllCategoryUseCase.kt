package rodrigo.taskapp.feature_category.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorCategory
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    operator fun invoke(): Flow<Result<List<Category>, ErrorCategory>> {
        return categoryRepository.getAllFlow().map {flowResult ->
            when (flowResult) {
                is Result.Error -> {
                    logger.error {"✘ Error: Getting a category list."}
                    Result.Error(ErrorCategory.Database(flowResult.error))
                }
                is Result.Success -> {
                    if (flowResult.data.isEmpty()) {
                        logger.warn {"✘ Error: The gotten list is empty."}
                        Result.Error(ErrorCategory.Domain.EMPTY_CATEGORY_LIST)
                    }
                    logger.info {"✔ Success: Getting a category list."}
                    Result.Success(flowResult.data)
                }
            }
        }
    }
}
