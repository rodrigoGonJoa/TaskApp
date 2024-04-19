package rodrigo.taskapp.feature_category.domain

import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorCategory

class CategoryVerification: ModelVerification<Category, ErrorCategory>() {
    override val verifications = listOf(::titleLength)

    private fun titleLength(category: Category): Result<Unit, ErrorCategory> {
        if (category.title.length >= 50) {
            return Result.Error(ErrorCategory.TitleTooLong)
        }
        if (category.title.isBlank()) {
            return Result.Error(ErrorCategory.EmptyTitle)
        }
        return Result.Success(Unit)
    }
}
