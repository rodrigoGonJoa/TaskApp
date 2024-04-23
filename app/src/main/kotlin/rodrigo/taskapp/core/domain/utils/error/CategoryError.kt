package rodrigo.taskapp.core.domain.utils.error

sealed interface CategoryError: Error {
    data class Database(val roomError: RoomError): CategoryError
    enum class Domain: CategoryError { EMPTY_CATEGORY_LIST, NULL_CATEGORY_ID, NULL_CATEGORY}
    enum class Verification: CategoryError { TITLE_TOO_LONG, EMPTY_TITLE }
}
