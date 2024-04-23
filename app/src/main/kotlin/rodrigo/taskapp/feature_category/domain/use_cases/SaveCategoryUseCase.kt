package rodrigo.taskapp.feature_category.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.CategoryError
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import javax.inject.Inject

class SaveCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val categoryVerification: CategoryVerification
) {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    suspend operator fun invoke(category: Category): Result<Category, CategoryError>{
        return when (val resultVerification = categoryVerification.invoke(category)) {
            is Result.Error -> {
                logger.error {"✘ Error: Category verification."}
                Result.Error(resultVerification.error)
            }
            is Result.Success -> {
                logger.info {"✔ Success: Category verification."}
                saveCategory(category)
            }
        }
    }
    private suspend fun saveCategory(category: Category): Result<Category, CategoryError> {
        val updatedCategory = category.updateDateTimeFields()
        return when (val resultDatabase = categoryRepository.save(updatedCategory)) {
            is Result.Error -> {
                logger.error {"✘ Error: Save category."}
                Result.Error(CategoryError.Database(resultDatabase.error))
            }
            is Result.Success -> {
                logger.info {"✔ Success: Save category."}
                Result.Success(updatedCategory.setId(resultDatabase.data))
            }
        }
    }
}
