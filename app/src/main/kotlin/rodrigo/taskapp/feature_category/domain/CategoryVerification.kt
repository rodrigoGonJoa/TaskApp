package rodrigo.taskapp.feature_category.domain

import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.Result

class CategoryVerification: ModelVerification<Category, CategoryError>() {
    override val verifications = listOf(::titleLength)

    private fun titleLength(category: Category): Result<Unit, CategoryError> {
        if (category.title.length >= 50) {
            return Result.Error(CategoryError.TitleTooLong)
        }
        if (category.title.isBlank()) {
            return Result.Error(CategoryError.EmptyTitle)
        }
        return Result.Success(Unit)
    }
}
