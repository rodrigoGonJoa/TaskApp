package rodrigo.taskapp.feature_task.domain

import rodrigo.taskapp.core.domain.utils.error.TaskError

class TaskException(val error: TaskError): Exception()
