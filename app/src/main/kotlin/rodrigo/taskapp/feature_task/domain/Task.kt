package rodrigo.taskapp.feature_task.domain

import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.core.domain.utils.DateTimeUtils.nowOnDateTimeSavePattern
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_task.data.TaskAndCategory
import rodrigo.taskapp.feature_task.data.TaskEntity

data class Task(
    override val modelId: Long? = null,
    override val modelModifiedAt: Long = System.currentTimeMillis(),
    override val modelCreatedAt: Long = System.currentTimeMillis(),
    val content: String,
    val scheduledTime: Long? = null,
    val scheduledDate: Long? = null,
    val isFinishedSuccessfully: Boolean? = null,
    val completedAt: Long? = null,
    val category: Category? = null
): BaseModel<TaskAndCategory> {
    override fun mapToEntity() = TaskAndCategory(
        taskEntity = TaskEntity(
            tId = modelId,
            tModifiedAt = modelModifiedAt,
            tCreatedAt = modelCreatedAt,
            tContent = content,
            tScheduledTime = scheduledTime,
            tScheduledDate = scheduledDate,
            tIsFinishedSuccessfully = isFinishedSuccessfully,
            tCompletedAt = completedAt,
            tCategoryId = category?.modelId
        ),
        tCategory = category?.mapToEntity()
    )

    override fun updateDateTimeFields() = copy(
        modelCreatedAt = nowOnDateTimeSavePattern,
        modelModifiedAt = nowOnDateTimeSavePattern
    )

    override fun modified() = copy(modelModifiedAt = nowOnDateTimeSavePattern)
    override fun setId(modelId: Long?) = copy(modelId = modelId)
    fun setCategory(category: Category?) = copy(category = category)
    fun setCategoryId(categoryId: Long?) = setCategory(this.category?.copy(modelId = categoryId))

}
