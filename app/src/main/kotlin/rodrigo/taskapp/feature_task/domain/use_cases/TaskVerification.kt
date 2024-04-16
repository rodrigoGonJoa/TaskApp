package rodrigo.taskapp.feature_task.domain.use_cases

import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskError

class TaskVerification {
    operator fun invoke(task: Task): Result<Unit, Error> {
        if(task.content.length >= 1023){
            return Result.Error(TaskError.ContentTooLong)
        }
        return Result.Success(Unit)
    }
}
