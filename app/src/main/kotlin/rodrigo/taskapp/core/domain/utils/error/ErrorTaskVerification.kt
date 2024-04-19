package rodrigo.taskapp.core.domain.utils.error

enum class ErrorTaskVerification: ErrorTask {
    CONTENT_TOO_LONG,
    EMPTY_CONTENT,
    DATETIME_COMPLETION_IS_NULL,
    COMPLETION_FLAG_IS_NULL,
    TIME_SET_WITHOUT_DATE
}
