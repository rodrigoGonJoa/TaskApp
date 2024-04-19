package rodrigo.taskapp.core.domain.utils.error

sealed interface ErrorTask: Error {
    data object TASK_ID_NULL: ErrorTask
    data object NULL_TASK: ErrorTask
    data object EMPTY_TASK_LIST: ErrorTask
}
