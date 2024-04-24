package rodrigo.taskapp.feature_periodic_task.data

import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.feature_category.data.CategoryEntity
import rodrigo.taskapp.feature_periodic_task.domain.PeriodicTask

data class PeriodicTaskEntity(
    val ptId: Long?,
    val ptModifiedAt: Long,
    val ptCreatedAt: Long,
    val ptContent: String,
    val ptStartDate: Long,
    val ptScheduledTime: Long?,
    val ptWeekDays: Long?,
    val ptEveryXDays: Int?,
    val ptIsPeriodEnds: Boolean,
    val ptEndDate: Long?,
    val ptCountRepeats: Int?,
    val ptCategory: CategoryEntity?
): BaseEntity<PeriodicTask> {
    override fun mapToModel() = PeriodicTask(
        modelId = ptId,
        modelCreatedAt = ptCreatedAt,
        modelModifiedAt = ptModifiedAt,
        content = ptContent,
        startDate = ptStartDate,
        scheduledTime = ptScheduledTime,
        weekDays = ptWeekDays,
        everyXDays = ptEveryXDays,
        isPeriodEnds = ptIsPeriodEnds,
        endDate = ptEndDate,
        countRepeats = ptCountRepeats,
        category = ptCategory?.mapToModel()
    )
}
