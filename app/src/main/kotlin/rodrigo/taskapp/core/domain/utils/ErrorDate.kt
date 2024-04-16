package rodrigo.taskapp.core.domain.utils

sealed class ErrorDate: Error {
    data object NullLocalDate: ErrorDate()
    data object MillisToLocalDate: ErrorDate()
    data object InvalidPattern: ErrorDate()
    data object UnknownFormattingWithPattern: ErrorDate()
    data object DuringFormatting: ErrorDate()
    data object FormattingToLong: ErrorDate()
    data object UnsupportedPattern: ErrorDate()
}
