package rodrigo.taskapp.core.domain.utils.error

enum class DateError: Error {
    NullLocalDate,
    MillisToLocalDate,
    InvalidPattern,
    UnknownFormattingWithPattern,
    DuringFormatting,
    FormattingToLong,
    UnsupportedPattern
}
