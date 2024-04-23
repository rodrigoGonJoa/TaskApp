package rodrigo.taskapp.core.domain.model

interface BaseModel<ENTITY> {
    val modelId: Long?
    val modelModifiedAt: Long
    val modelCreatedAt: Long
    fun mapToEntity(): ENTITY
    fun updateDateTimeFields(): BaseModel<ENTITY>
    fun modified(): BaseModel<ENTITY>
    fun setId(modelId: Long): BaseModel<ENTITY>
}
