package rodrigo.taskapp.feature_task.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.feature_category.data.CategoryEntity
import rodrigo.taskapp.feature_task.domain.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "t_id")
    val tId: Long?,
    @ColumnInfo(name = "t_modified_at")
    val tModifiedAt: Long,
    @ColumnInfo(name = "t_created_at")
    val tCreatedAt: Long,
    @ColumnInfo(name = "t_content")
    val tContent: String,
    @ColumnInfo(name = "t_scheduled_time")
    val tScheduledTime: Long? = null,
    @ColumnInfo(name = "t_scheduled_date")
    val tScheduledDate: Long? = null,
    @ColumnInfo(name = "t_is_finished_successfully")
    val tIsFinishedSuccessfully: Boolean? = null,
    @ColumnInfo(name = "t_completed_at")
    val tCompletedAt: Long? = null,
    @ColumnInfo(name = "t_category_id")
    val tCategoryId: Long? = null
): BaseEntity<Task> {
    override fun mapToModel() = Task(
        modelId = tId,
        modelModifiedAt = tModifiedAt,
        modelCreatedAt = tCreatedAt,
        content = tContent,
        scheduledTime = tScheduledTime,
        scheduledDate = tScheduledDate,
        isFinishedSuccessfully = tIsFinishedSuccessfully,
        completedAt = tCompletedAt
    )
}

data class TaskAndCategory(
    @Embedded val taskEntity: TaskEntity,
    @Relation(parentColumn = "t_category_id", entityColumn = "c_id")
    val tCategory: CategoryEntity?
): BaseEntity<Task> {
    override fun mapToModel() = taskEntity.mapToModel().setCategory(tCategory?.mapToModel())
}
