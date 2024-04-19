package rodrigo.taskapp.core.domain.utils.error

enum class ErrorDate: Error {
    NullLocalDate,
    MillisToLocalDate,
    InvalidPattern,
    UnknownFormattingWithPattern,
    DuringFormatting,
    FormattingToLong,
    UnsupportedPattern
}
