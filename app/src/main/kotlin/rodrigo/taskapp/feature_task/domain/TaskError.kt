package rodrigo.taskapp.feature_task.domain

import rodrigo.taskapp.core.domain.utils.Error

sealed class TaskError: Error {
    data object ContentTooLong: TaskError()
}
