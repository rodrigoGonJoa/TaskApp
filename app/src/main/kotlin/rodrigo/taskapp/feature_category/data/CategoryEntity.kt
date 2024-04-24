package rodrigo.taskapp.feature_category.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.feature_category.domain.Category

@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "c_id")
    val cId: Long? = null,
    @ColumnInfo(name = "c_modified_at")
    val cModifiedAt: Long,
    @ColumnInfo(name = "c_created_at")
    val cCreatedAt: Long,
    @ColumnInfo(name = "c_title")
    val cTitle: String,
    @ColumnInfo(name = "c_color")
    val cColor: Long,
    @ColumnInfo(name = "c_icon_id")
    val cIconId: Long?
): BaseEntity<Category> {
    override fun mapToModel() = Category(
        modelId = cId,
        modelCreatedAt = cCreatedAt,
        modelModifiedAt = cModifiedAt,
        title = cTitle,
        color = cColor,
        iconId = cIconId
    )
}
