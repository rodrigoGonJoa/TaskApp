package rodrigo.taskapp.core.domain.utils.error

sealed interface ErrorTask: Error {
    enum class Domain: ErrorTask { TASK_ID_NULL, NULL_TASK, EMPTY_TASK_LIST }
    data class Database(val errorRoom: ErrorRoom): ErrorTask
    enum class Verification: ErrorTask {
        CONTENT_TOO_LONG,
        EMPTY_CONTENT,
        DATETIME_COMPLETION_IS_NULL,
        COMPLETION_FLAG_IS_NULL,
        TIME_SET_WITHOUT_DATE
    }
}
