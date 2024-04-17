package rodrigo.taskapp.feature_periodictask.domain

import rodrigo.taskapp.core.domain.utils.Error

sealed class PeriodicTaskError: Error {
    data object ContentTooLong: PeriodicTaskError()
    data object EmptyContent: PeriodicTaskError()
    data object PeriodNotSet: PeriodicTaskError()
    data object MultiplePeriod: PeriodicTaskError()
    data object MultiplePeriodEnds: PeriodicTaskError()
    data object EndDateTooEarly: PeriodicTaskError()
    data object ZeroCountRepeats: PeriodicTaskError()
    data object EveryZeroDays: PeriodicTaskError()
    data object PeriodEndedPreviously: PeriodicTaskError()
    data object EndDateNotSet: PeriodicTaskError()
}
