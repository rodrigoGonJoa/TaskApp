package rodrigo.taskapp.core.domain.utils.error

sealed interface PeriodicTaskError: Error {
    enum class Verification: PeriodicTaskError {
        CONTENT_TOO_LONG,
        EMPTY_CONTENT,
        PERIOD_NOT_SET,
        MULTIPLE_PERIOD,
        MULTIPLE_PERIOD_ENDS,
        END_DATE_TOO_EARLY,
        ZERO_COUNT_REPEATS,
        EVERY_ZERO_DAYS,
        PERIOD_ENDED_PREVIOUSLY,
        END_DATE_NOT_SET
    }
}
