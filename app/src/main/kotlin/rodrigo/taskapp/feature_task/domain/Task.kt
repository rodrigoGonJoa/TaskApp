package rodrigo.taskapp.feature_task.domain

import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_task.data.TaskEntity

data class Task(
    override val modelId: Long,
    override val modelModifiedAt: Long = System.currentTimeMillis(),
    override val modelCreatedAt: Long = System.currentTimeMillis(),
    val content: String,
    val scheduledTime: Long? = null,
    val scheduledDate: Long? = null,
    val isCompleted: Boolean? = null,
    val completedAt: Long? = null,
    val category: Category? = null
): BaseModel<TaskEntity> {
    override fun mapToEntity() = TaskEntity(
        entityId = modelId,
        entityModifiedAt = modelModifiedAt,
        entityCreatedAt = modelCreatedAt,
        tContent = content,
        tScheduledTime = scheduledTime,
        tScheduledDate = scheduledDate,
        tIsCompleted = isCompleted,
        tCompletedAt = completedAt,
        tCategory = category?.mapToEntity()
    )
}
