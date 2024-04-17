package rodrigo.taskapp.feature_periodictask.domain.use_cases

import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.DateTimeUtils
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_periodictask.domain.PeriodicTask
import rodrigo.taskapp.feature_periodictask.domain.PeriodicTaskError

class PeriodicTaskVerification: ModelVerification<PeriodicTask, PeriodicTaskError>() {
    override val verifications = listOf(::contentLength, ::period, ::periodEnds)

    private fun contentLength(periodicTask: PeriodicTask): Result<Unit, PeriodicTaskError> {
        if (periodicTask.content.length >= 1023) {
            return Result.Error(PeriodicTaskError.ContentTooLong)
        }
        if (periodicTask.content.isBlank()) {
            return Result.Error(PeriodicTaskError.EmptyContent)
        }
        return Result.Success(Unit)
    }

    private fun period(periodicTask: PeriodicTask): Result<Unit, PeriodicTaskError>{
        if(periodicTask.weekDays == null && periodicTask.everyXDays == null){
            return Result.Error(PeriodicTaskError.PeriodNotSet)
        }
        if(periodicTask.weekDays != null && periodicTask.everyXDays != null){
            return Result.Error(PeriodicTaskError.MultiplePeriod)
        }
        periodicTask.everyXDays?.let {everyXDays ->
            if(everyXDays == 0){
                return Result.Error(PeriodicTaskError.EveryZeroDays)
            }
        }
        return Result.Success(Unit)
    }

    private fun periodEnds(periodicTask: PeriodicTask): Result<Unit, PeriodicTaskError>{
        if(periodicTask.endDate != null && periodicTask.countRepeats != null){
            return Result.Error(PeriodicTaskError.MultiplePeriodEnds)
        }
        if(periodicTask.countRepeats == 0){
            return Result.Error(PeriodicTaskError.ZeroCountRepeats)
        }
        if(periodicTask.isPeriodEnds && periodicTask.endDate == null){
            return Result.Error(PeriodicTaskError.EndDateNotSet)
        }
        periodicTask.endDate?.let {endDate ->
            if(endDate < periodicTask.startDate){
                return Result.Error(PeriodicTaskError.EndDateTooEarly)
            }
            DateTimeUtils.nowOnDateSavePattern()
            if(endDate <= DateTimeUtils.nowOnDateSavePattern() && !periodicTask.isPeriodEnds){
                return Result.Error(PeriodicTaskError.PeriodEndedPreviously)
            }
        }
        return Result.Success(Unit)
    }
}
