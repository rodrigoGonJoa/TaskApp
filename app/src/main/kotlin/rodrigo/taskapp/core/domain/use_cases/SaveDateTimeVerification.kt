package rodrigo.taskapp.core.domain.use_cases

import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.ErrorDate
import rodrigo.taskapp.core.domain.utils.Result

const val dateRegex =
    "^(?:(?:19|20)\\d\\d(?:(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[13579][26])00)|(?:19|20)(?:[2468][048]|[13579][26])|(?:0[48]|[13579][26])00)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$"
const val timeRegex = "^([01][0-9]|2[0-3])[0-5][0-9][0-5][0-9]$"
const val dateTimeRegex = "$dateRegex$timeRegex"

enum class SaveDateTimeType {
    DATE, TIME, DATETIME
}

class SaveDateTimeVerification {
    operator fun invoke(date: Long, type: SaveDateTimeType): Result<Unit, Error> {
        return when (type) {
            SaveDateTimeType.DATE -> verifyDateTime(date, dateRegex)
            SaveDateTimeType.TIME -> verifyDateTime(date, timeRegex)
            SaveDateTimeType.DATETIME -> verifyDateTime(date, dateTimeRegex)
        }
    }

    private fun verifyDateTime(date: Long, regexPattern: String): Result<Unit, Error> {
        if (!date.toString().matches(Regex(regexPattern))) {
            return Result.Error(ErrorDate.InvalidPattern)
        }
        return Result.Success(Unit)
    }
}
