package rodrigo.taskapp.core.data.model

interface BaseEntity<MODEL> {
    val entityId: Long?
    val entityModifiedAt: Long
    val entityCreatedAt: Long
    fun mapToModel(): MODEL
}
