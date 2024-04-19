package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_task.domain.Task
import rodrigo.taskapp.feature_task.domain.TaskError

class TaskVerification: ModelVerification<Task, TaskError>() {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    override val verifications = listOf(::contentLength, ::isCompleted, ::scheduledTime)

    private fun contentLength(task: Task): Result<Unit, TaskError> {
        if (task.content.length >= 1023) {
            logger.warn {"✘ Error: the task content is too long."}
            return Result.Error(TaskError.ContentTooLong)
        }
        if (task.content.isBlank()) {
            logger.warn {"✘ Error: the task content is blank."}
            return Result.Error(TaskError.EmptyContent)
        }
        logger.info {"✔ Success: contentLength verification."}
        return Result.Success(Unit)
    }

    private fun isCompleted(task: Task): Result<Unit, TaskError> {
        if (task.isFinishedSuccessfully != null && task.completedAt == null) {
            logger.warn {"✘ Error: have a finished boolean but completed is null."}
            return Result.Error(TaskError.DateTimeCompletionIsNull)
        }
        if (task.isFinishedSuccessfully == null && task.completedAt != null) {
            logger.warn {"✘ Error: is finished but completedAt is null."}
            return Result.Error(TaskError.CompletionFlagIsNull)
        }
        logger.info {"✔ Success: isCompleted verification."}
        return Result.Success(Unit)
    }

    private fun scheduledTime(task: Task): Result<Unit, TaskError> {
        if (task.scheduledDate == null && task.scheduledTime != null) {
            logger.warn {"✘ Error: there is a schedule hour without date."}
            return Result.Error(TaskError.TimeSetWithoutDate)
        }
        logger.info {"✔ Success: scheduledTime verification."}
        return Result.Success(Unit)
    }
}
