package rodrigo.taskapp.feature_periodic_task.domain.use_cases

import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.DateTimeUtils
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.feature_periodic_task.domain.PeriodicTask
import rodrigo.taskapp.core.domain.utils.error.ErrorPeriodicTaskVerification

class PeriodicTaskVerification: ModelVerification<PeriodicTask, ErrorPeriodicTaskVerification>() {
    override val verifications = listOf(::contentLength, ::period, ::periodEnds)

    private fun contentLength(periodicTask: PeriodicTask): Result<Unit, ErrorPeriodicTaskVerification> {
        if (periodicTask.content.length >= 1023) {
            return Result.Error(ErrorPeriodicTaskVerification.ContentTooLong)
        }
        if (periodicTask.content.isBlank()) {
            return Result.Error(ErrorPeriodicTaskVerification.EmptyContent)
        }
        return Result.Success(Unit)
    }

    private fun period(periodicTask: PeriodicTask): Result<Unit, ErrorPeriodicTaskVerification>{
        if(periodicTask.weekDays == null && periodicTask.everyXDays == null){
            return Result.Error(ErrorPeriodicTaskVerification.PeriodNotSet)
        }
        if(periodicTask.weekDays != null && periodicTask.everyXDays != null){
            return Result.Error(ErrorPeriodicTaskVerification.MultiplePeriod)
        }
        periodicTask.everyXDays?.let {everyXDays ->
            if(everyXDays == 0){
                return Result.Error(ErrorPeriodicTaskVerification.EveryZeroDays)
            }
        }
        return Result.Success(Unit)
    }

    private fun periodEnds(periodicTask: PeriodicTask): Result<Unit, ErrorPeriodicTaskVerification>{
        if(periodicTask.endDate != null && periodicTask.countRepeats != null){
            return Result.Error(ErrorPeriodicTaskVerification.MultiplePeriodEnds)
        }
        if(periodicTask.countRepeats == 0){
            return Result.Error(ErrorPeriodicTaskVerification.ZeroCountRepeats)
        }
        if(periodicTask.isPeriodEnds && periodicTask.endDate == null){
            return Result.Error(ErrorPeriodicTaskVerification.EndDateNotSet)
        }
        periodicTask.endDate?.let {endDate ->
            if(endDate < periodicTask.startDate){
                return Result.Error(ErrorPeriodicTaskVerification.EndDateTooEarly)
            }
            DateTimeUtils.nowOnDateSavePattern()
            if(endDate <= DateTimeUtils.nowOnDateSavePattern() && !periodicTask.isPeriodEnds){
                return Result.Error(ErrorPeriodicTaskVerification.PeriodEndedPreviously)
            }
        }
        return Result.Success(Unit)
    }
}
