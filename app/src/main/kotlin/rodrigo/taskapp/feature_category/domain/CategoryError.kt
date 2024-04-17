package rodrigo.taskapp.feature_category.domain

import rodrigo.taskapp.core.domain.utils.Error

sealed class CategoryError: Error {
    data object TitleTooLong: CategoryError()
    data object EmptyTitle: CategoryError()
}

