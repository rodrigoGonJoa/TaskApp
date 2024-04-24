package rodrigo.taskapp.core.data.model

interface BaseEntity<MODEL> {
    fun mapToModel(): MODEL
}
