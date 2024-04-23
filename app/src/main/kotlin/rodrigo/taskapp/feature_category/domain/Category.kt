package rodrigo.taskapp.feature_category.domain

import androidx.compose.ui.graphics.Color
import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.core.domain.utils.DateTimeUtils
import rodrigo.taskapp.feature_category.data.CategoryEntity

data class Category(
    override val modelId: Long? = null,
    override val modelModifiedAt: Long = System.currentTimeMillis(),
    override val modelCreatedAt: Long = System.currentTimeMillis(),
    val title: String,
    val color: Long = Color.White.value.toLong(),
    val iconId: Long? = null
): BaseModel<CategoryEntity> {
    override fun mapToEntity() = CategoryEntity(
        entityId = modelId,
        entityCreatedAt = modelCreatedAt,
        entityModifiedAt = modelModifiedAt,
        cTitle = title,
        cColor = color,
        cIconId = iconId
    )
    override fun updateDateTimeFields() = copy(
        modelCreatedAt = DateTimeUtils.nowOnDateTimeSavePattern,
        modelModifiedAt = DateTimeUtils.nowOnDateTimeSavePattern
    )
    override fun modified() = copy(modelModifiedAt = DateTimeUtils.nowOnDateTimeSavePattern)
    override fun setId(modelId: Long) = copy(modelId = modelId)
}

object CategoryData {
    const val MAX_TITLE_LENGTH = 50
}
