package rodrigo.taskapp.core.domain.utils


sealed class ErrorRoom: Error {
    data object Unknown: ErrorRoom()
    data object ErrorOnAdd: ErrorRoom()
    data object ErrorOnDelete: ErrorRoom()
    data object ErrorOnUpdate: ErrorRoom()
    data object ErrorOnGet: ErrorRoom()
    data object ErrorOnGetGroup: ErrorRoom()
}
