package rodrigo.taskapp.core.domain.utils.error

enum class ErrorRoom: Error {
    Unknown,
    ErrorOnAdd,
    ErrorOnDelete,
    ErrorOnUpdate,
    ErrorOnGet,
    ErrorOnGetGroup
}
