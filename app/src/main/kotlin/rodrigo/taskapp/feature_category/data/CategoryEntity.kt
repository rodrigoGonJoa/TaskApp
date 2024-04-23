package rodrigo.taskapp.feature_category.data

import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.feature_category.domain.Category

data class CategoryEntity(
    override val entityId: Long?,
    override val entityModifiedAt: Long,
    override val entityCreatedAt: Long,
    val cTitle: String,
    val cColor: Long,
    val cIconId: Long?
): BaseEntity<Category> {
    override fun mapToModel() = Category(
        modelId = entityId,
        modelCreatedAt = entityCreatedAt,
        modelModifiedAt = entityModifiedAt,
        title = cTitle,
        color = cColor,
        iconId = cIconId
    )
}
