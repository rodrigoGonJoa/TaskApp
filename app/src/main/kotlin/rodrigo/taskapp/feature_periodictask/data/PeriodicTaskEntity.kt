package rodrigo.taskapp.feature_periodictask.data

import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.feature_category.data.CategoryEntity
import rodrigo.taskapp.feature_periodictask.domain.PeriodicTask

data class PeriodicTaskEntity(
    override val entityId: Long,
    override val entityModifiedAt: Long,
    override val entityCreatedAt: Long,
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
        modelId = entityId,
        modelCreatedAt = entityCreatedAt,
        modelModifiedAt = entityModifiedAt,
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
