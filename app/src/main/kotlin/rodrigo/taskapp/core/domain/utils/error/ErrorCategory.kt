package rodrigo.taskapp.core.domain.utils.error

sealed interface ErrorCategory: Error {
    data class Database(val errorRoom: ErrorRoom): ErrorCategory
    enum class Domain: ErrorCategory { EMPTY_CATEGORY_LIST, NULL_CATEGORY_ID, NULL_CATEGORY}
    enum class Verification: ErrorCategory { TITLE_TOO_LONG, EMPTY_TITLE }
}
