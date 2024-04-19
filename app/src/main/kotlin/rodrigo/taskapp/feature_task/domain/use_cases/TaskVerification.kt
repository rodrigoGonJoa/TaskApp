package rodrigo.taskapp.feature_task.domain.use_cases

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorTask
import rodrigo.taskapp.feature_task.domain.Task

class TaskVerification: ModelVerification<Task, ErrorTask.Verification>() {
    private val logger = KotlinLogging.logger(this.javaClass.simpleName)
    override val verifications = listOf(::contentLength, ::isCompleted, ::scheduledTime)

    private fun contentLength(task: Task): Result<Unit, ErrorTask.Verification> {
        if (task.content.length >= 1023) {
            logger.warn {"✘ Error: the task content is too long."}
            return Result.Error(ErrorTask.Verification.CONTENT_TOO_LONG)
        }
        if (task.content.isBlank()) {
            logger.warn {"✘ Error: the task content is blank."}
            return Result.Error(ErrorTask.Verification.EMPTY_CONTENT)
        }
        return Result.Success(Unit)
    }

    private fun isCompleted(task: Task): Result<Unit, ErrorTask.Verification> {
        if (task.isFinishedSuccessfully != null && task.completedAt == null) {
            logger.warn {"✘ Error: have a finished boolean but completed is null."}
            return Result.Error(ErrorTask.Verification.DATETIME_COMPLETION_IS_NULL)
        }
        if (task.isFinishedSuccessfully == null && task.completedAt != null) {
            logger.warn {"✘ Error: is finished but completedAt is null."}
            return Result.Error(ErrorTask.Verification.COMPLETION_FLAG_IS_NULL)
        }
        return Result.Success(Unit)
    }

    private fun scheduledTime(task: Task): Result<Unit, ErrorTask.Verification> {
        if (task.scheduledDate == null && task.scheduledTime != null) {
            logger.warn {"✘ Error: there is a schedule hour without date."}
            return Result.Error(ErrorTask.Verification.TIME_SET_WITHOUT_DATE)
        }
        return Result.Success(Unit)
    }
}
