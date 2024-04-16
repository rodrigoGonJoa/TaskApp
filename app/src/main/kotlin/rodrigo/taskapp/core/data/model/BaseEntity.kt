package rodrigo.taskapp.core.data.model

interface BaseEntity<T> {
    val entityId: Long
    val entityModifiedAt: Long
    val entityCreatedAt: Long
    fun mapToModel(): T
}
