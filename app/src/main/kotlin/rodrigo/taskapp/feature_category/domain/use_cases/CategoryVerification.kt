package rodrigo.taskapp.feature_category.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.CategoryError
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_category.domain.CategoryData

class CategoryVerification: ModelVerification<Category, CategoryError.Verification>() {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    override val verifications = listOf(::titleLength)

    private fun titleLength(category: Category): Result<Unit, CategoryError.Verification> {
        if (category.title.length >= CategoryData.MAX_TITLE_LENGTH) {
            logger.warn {"✘ Error: the category title is too long."}
            return Result.Error(CategoryError.Verification.TITLE_TOO_LONG)
        }
        if (category.title.isBlank()) {
            logger.warn {"✘ Error: the category title is empty."}
            return Result.Error(CategoryError.Verification.EMPTY_TITLE)
        }
        return Result.Success(Unit)
    }
}
