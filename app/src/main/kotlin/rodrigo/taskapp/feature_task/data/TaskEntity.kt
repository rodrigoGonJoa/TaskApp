package rodrigo.taskapp.feature_task.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.feature_category.data.CategoryEntity
import rodrigo.taskapp.feature_task.domain.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "task_id")
    override val entityId: Long?,
    override val entityModifiedAt: Long,
    override val entityCreatedAt: Long,
    val tContent: String,
    val tScheduledTime: Long? = null,
    val tScheduledDate: Long? = null,
    val tIsFinishedSuccessfully: Boolean? = null,
    val tCompletedAt: Long? = null,
    val tCategory: CategoryEntity? = null
): BaseEntity<Task> {
    override fun mapToModel() = Task(
        modelId = entityId,
        modelModifiedAt = entityModifiedAt,
        modelCreatedAt = entityCreatedAt,
        content = tContent,
        scheduledTime = tScheduledTime,
        scheduledDate = tScheduledDate,
        isFinishedSuccessfully = tIsFinishedSuccessfully,
        completedAt = tCompletedAt,
        category = tCategory?.mapToModel()
    )
}
