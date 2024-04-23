package rodrigo.taskapp.core.domain.utils.error

sealed interface TaskError: Error {
    enum class Domain: TaskError { NULL_TASK_ID, NULL_TASK, EMPTY_TASK_LIST }
    data class Database(val roomError: RoomError): TaskError
    enum class Verification: TaskError {
        CONTENT_TOO_LONG,
        EMPTY_CONTENT,
        DATETIME_COMPLETION_IS_NULL,
        COMPLETION_FLAG_IS_NULL,
        TIME_SET_WITHOUT_DATE
    }
}
