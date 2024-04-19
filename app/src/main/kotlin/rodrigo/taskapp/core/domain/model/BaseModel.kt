package rodrigo.taskapp.core.domain.model

interface BaseModel<ENTITY> {
    val modelId: Long?
    val modelModifiedAt: Long
    val modelCreatedAt: Long
    fun mapToEntity(): ENTITY
}
