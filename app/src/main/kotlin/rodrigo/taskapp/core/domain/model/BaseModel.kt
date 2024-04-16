package rodrigo.taskapp.core.domain.model

interface BaseModel<T> {
    val modelId: Long
    val modelModifiedAt: Long
    val modelCreatedAt: Long
    fun mapToEntity(): T
}
