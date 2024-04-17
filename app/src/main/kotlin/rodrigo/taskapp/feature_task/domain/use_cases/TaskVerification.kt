package rodrigo.taskapp.feature_task.domain.use_cases

import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskError

class TaskVerification: ModelVerification<Task, TaskError>() {
    override val verifications = listOf(::contentLength, ::isCompleted, ::scheduledTime)

    private fun contentLength(task: Task): Result<Unit, TaskError> {
        if (task.content.length >= 1023) {
            return Result.Error(TaskError.ContentTooLong)
        }
        if (task.content.isBlank()) {
            return Result.Error(TaskError.EmptyContent)
        }
        return Result.Success(Unit)
    }

    private fun isCompleted(task: Task): Result<Unit, TaskError> {
        if (task.isFinishedSuccessfully != null && task.completedAt == null) {
            return Result.Error(TaskError.DateTimeCompletionIsNull)
        }
        if (task.isFinishedSuccessfully == null && task.completedAt != null) {
            return Result.Error(TaskError.CompletionFlagIsNull)
        }
        return Result.Success(Unit)
    }

    private fun scheduledTime(task: Task): Result<Unit, TaskError> {
        if (task.scheduledDate == null && task.scheduledTime != null) {
            return Result.Error(TaskError.TimeSetWithoutDate)
        }
        return Result.Success(Unit)
    }
}
