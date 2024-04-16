package rodrigo.taskapp.feature_periodictask.data

import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.feature_category.data.CategoryEntity
import rodrigo.taskapp.feature_periodictask.domain.PeriodicTask

data class PeriodicTaskEntity(
    override val entityId: Long,
    override val entityModifiedAt: Long,
    override val entityCreatedAt: Long,
    val ptContent: String,
    val ptStartedDate: Long,
    val ptScheduledTime: Long?,
    val ptScheduledDate: Long?,
    val ptWeekDays: Long?,
    val ptEveryXDays: Int?,
    val ptIsPeriodEnds: Boolean,
    val ptCountRepeats: Int?,
    val ptCategory: CategoryEntity?
): BaseEntity<PeriodicTask> {
    override fun mapToModel() = PeriodicTask(
        modelId = entityId,
        modelCreatedAt = entityCreatedAt,
        modelModifiedAt = entityModifiedAt,
        content = ptContent,
        startedDate = ptStartedDate,
        scheduledDate = ptScheduledDate,
        scheduledTime = ptScheduledTime,
        weekDays = ptWeekDays,
        everyXDays = ptEveryXDays,
        isPeriodEnds = ptIsPeriodEnds,
        countRepeats = ptCountRepeats,
        category = ptCategory?.mapToModel()
    )
}
