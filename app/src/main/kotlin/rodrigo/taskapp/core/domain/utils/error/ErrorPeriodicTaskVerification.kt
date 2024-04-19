package rodrigo.taskapp.core.domain.utils.error

enum class ErrorPeriodicTaskVerification: ErrorPeriodicTask {
    ContentTooLong,
    EmptyContent,
    PeriodNotSet,
    MultiplePeriod,
    MultiplePeriodEnds,
    EndDateTooEarly,
    ZeroCountRepeats,
    EveryZeroDays,
    PeriodEndedPreviously,
    EndDateNotSet

}
