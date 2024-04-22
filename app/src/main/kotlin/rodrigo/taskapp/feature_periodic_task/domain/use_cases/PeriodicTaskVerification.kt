package rodrigo.taskapp.feature_periodic_task.domain.use_cases

import rodrigo.taskapp.core.domain.use_cases.ModelVerification
import rodrigo.taskapp.core.domain.utils.DateTimeUtils
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorPeriodicTask
import rodrigo.taskapp.feature_periodic_task.domain.PeriodicTask

class PeriodicTaskVerification: ModelVerification<PeriodicTask, ErrorPeriodicTask.Verification>() {
    override val verifications = listOf(::contentLength, ::period, ::periodEnds)

    private fun contentLength(periodicTask: PeriodicTask): Result<Unit, ErrorPeriodicTask.Verification> {
        if (periodicTask.content.length >= 1023) {
            return Result.Error(ErrorPeriodicTask.Verification.CONTENT_TOO_LONG)
        }
        if (periodicTask.content.isBlank()) {
            return Result.Error(ErrorPeriodicTask.Verification.EMPTY_CONTENT)
        }
        return Result.Success(Unit)
    }

    private fun period(periodicTask: PeriodicTask): Result<Unit, ErrorPeriodicTask.Verification>{
        if(periodicTask.weekDays == null && periodicTask.everyXDays == null){
            return Result.Error(ErrorPeriodicTask.Verification.PERIOD_NOT_SET)
        }
        if(periodicTask.weekDays != null && periodicTask.everyXDays != null){
            return Result.Error(ErrorPeriodicTask.Verification.MULTIPLE_PERIOD)
        }
        periodicTask.everyXDays?.let {everyXDays ->
            if(everyXDays == 0){
                return Result.Error(ErrorPeriodicTask.Verification.EVERY_ZERO_DAYS)
            }
        }
        return Result.Success(Unit)
    }

    private fun periodEnds(periodicTask: PeriodicTask): Result<Unit, ErrorPeriodicTask.Verification>{
        if(periodicTask.endDate != null && periodicTask.countRepeats != null){
            return Result.Error(ErrorPeriodicTask.Verification.MULTIPLE_PERIOD_ENDS)
        }
        if(periodicTask.countRepeats == 0){
            return Result.Error(ErrorPeriodicTask.Verification.ZERO_COUNT_REPEATS)
        }
        if(periodicTask.isPeriodEnds && periodicTask.endDate == null){
            return Result.Error(ErrorPeriodicTask.Verification.END_DATE_NOT_SET)
        }
        periodicTask.endDate?.let {endDate ->
            if(endDate < periodicTask.startDate){
                return Result.Error(ErrorPeriodicTask.Verification.END_DATE_TOO_EARLY)
            }
            DateTimeUtils.nowOnDateSavePattern
            if(endDate <= DateTimeUtils.nowOnDateSavePattern && !periodicTask.isPeriodEnds){
                return Result.Error(ErrorPeriodicTask.Verification.PERIOD_ENDED_PREVIOUSLY)
            }
        }
        return Result.Success(Unit)
    }
}
