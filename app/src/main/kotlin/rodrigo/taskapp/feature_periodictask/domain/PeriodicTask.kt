package rodrigo.taskapp.feature_periodictask.domain

import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_periodictask.data.PeriodicTaskEntity

data class PeriodicTask(
    override val modelId: Long,
    override val modelModifiedAt: Long = System.currentTimeMillis(),
    override val modelCreatedAt: Long = System.currentTimeMillis(),
    val content: String,
    val startedDate: Long,
    val scheduledTime: Long? = null,
    val scheduledDate: Long? = null,
    val weekDays: Long? = null,
    val everyXDays: Int? = null,
    val isPeriodEnds: Boolean = false,
    val countRepeats: Int? = null,
    val category: Category? = null
): BaseModel<PeriodicTaskEntity> {
    override fun mapToEntity() = PeriodicTaskEntity(
        entityId = modelId,
        entityCreatedAt = modelCreatedAt,
        entityModifiedAt = modelModifiedAt,
        ptContent = content,
        ptStartedDate = startedDate,
        ptScheduledDate = scheduledDate,
        ptScheduledTime = scheduledTime,
        ptWeekDays = weekDays,
        ptEveryXDays = everyXDays,
        ptIsPeriodEnds = isPeriodEnds,
        ptCountRepeats = countRepeats,
        ptCategory = category?.mapToEntity()
    )
}

enum class EnumWeekDays(val value: Int) {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7)
}

enum class WeekDaysSet(val value: Int) {
    WORKINGDAYS(12345),
    WEEKEND(67)
}
