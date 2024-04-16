package rodrigo.taskapp.feature_task.domain.use_cases

import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskError

class TaskVerification {
    operator fun invoke(task: Task): Result<Unit, Error> {
        val verifications = listOf(::contentLenght, ::isCompleted, ::scheduledTime)
        return executeVerifications(verifications, task)
    }

    private fun executeVerifications(
        verifications: List<(Task) -> Result<Unit, Error>>,
        task: Task
    ): Result<Unit, Error> = verifications.find {verification ->
        verification(task) is Result.Error
    }?.invoke(task) ?: Result.Success(Unit)


    private fun contentLenght(task: Task): Result<Unit, Error> {
        if (task.content.length >= 1023) {
            return Result.Error(TaskError.ContentTooLong)
        }
        return Result.Success(Unit)
    }

    private fun isCompleted(task: Task): Result<Unit, Error> {
        if (task.isCompleted != null && task.completedAt == null) {
            return Result.Error(TaskError.DateTimeCompletionIsNull)
        }
        if (task.isCompleted == null && task.completedAt != null) {
            return Result.Error(TaskError.CompletionFlagIsNull)
        }
        return Result.Success(Unit)
    }

    private fun scheduledTime(task: Task): Result<Unit, Error> {
        if (task.scheduledDate == null && task.scheduledTime != null) {
            return Result.Error(TaskError.TimeSetWithoutDate)
        }
        return Result.Success(Unit)
    }
}
