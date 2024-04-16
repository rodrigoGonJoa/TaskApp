package rodrigo.taskapp.feature_task.domain

import rodrigo.taskapp.core.domain.utils.Error

sealed class TaskError: Error {
    data object ContentTooLong: TaskError()
    data object DateTimeCompletionIsNull: TaskError()
    data object CompletionFlagIsNull: TaskError()
    data object TimeSetWithoutDate: TaskError()
}
