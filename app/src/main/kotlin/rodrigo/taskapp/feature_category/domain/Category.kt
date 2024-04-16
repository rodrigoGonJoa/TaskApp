package rodrigo.taskapp.feature_category.domain

import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.feature_category.data.CategoryEntity

data class Category(
    override val modelId: Long,
    override val modelModifiedAt: Long = System.currentTimeMillis(),
    override val modelCreatedAt: Long = System.currentTimeMillis(),
    val title: String,
    val color: Long = 0xFFFFFFFF,
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
}
