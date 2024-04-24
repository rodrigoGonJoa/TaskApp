package rodrigo.taskapp.core.domain.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import rodrigo.taskapp.core.domain.utils.error.DateError
import rodrigo.taskapp.core.domain.utils.error.Error
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object DateTimeUtils {
    private val logger = KotlinLogging.logger {}

    fun millisToLocalDate(dateInMillis: Long?): Result<LocalDate, Error> {
        if (dateInMillis == null) {
            logger.error {"La marca de tiempo es nula. Se devuelve una fecha nula."}
            return Result.Error(DateError.NullLocalDate)
        }
        return try {
            val localDate = Instant.ofEpochMilli(dateInMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            logger.info {"Se convirtieron $dateInMillis milisegundos a $localDate"}
            Result.Success(localDate)
        } catch (e: Exception) {
            logger.error(
                throwable = e
            ) {"Error al convertir milisegundos a LocalDate: ${e.message}"}
            Result.Error(DateError.MillisToLocalDate)
        }
    }

    private fun localDateToStringWithPattern(
        date: LocalDate?,
        pattern: DateTimePattern = DateTimePattern.DATETIME_TOSAVE,
        locale: Locale = Locale.getDefault()
    ): Result<String, Error> {
        if (date == null) {
            logger.error {"La marca de tiempo es nula. Se devuelve una fecha nula."}
            return Result.Error(DateError.NullLocalDate)
        }
        return try {
            val dateFormatter = DateTimeFormatter.ofPattern(pattern.pattern, locale)
            val dateResult = dateFormatter.format(date)
            logger.info {"Fecha formateada: $dateResult con patrón: ${pattern.pattern}"}
            Result.Success(dateResult)
        } catch (e: IllegalArgumentException) {
            val message = "El patrón de formato de fecha '$pattern' es inválido."
            logger.error(throwable = e) {message}
            Result.Error(DateError.InvalidPattern)
        } catch (e: DateTimeException) {
            val message = "Error en el formateo de la fecha."
            logger.error(throwable = e) {message}
            Result.Error(DateError.DuringFormatting)
        } catch (e: Exception) {
            val message =
                "Error desconocido al formatear la fecha: $date con patrón: ${pattern.pattern}"
            logger.error(throwable = e) {message}
            Result.Error(DateError.UnknownFormattingWithPattern)
        }
    }

    fun localDateToLong(date: LocalDate, pattern: DateTimePattern): Result<Long, Error> {
        return when (val result = localDateToStringWithPattern(date, pattern)) {
            is Result.Error -> result
            is Result.Success -> {
                try {
                    if (isDateMatchSavePattern(date).isError()) {
                        Result.Error(DateError.UnsupportedPattern)
                    }
                    logger.info {"Fecha formateada a Long: ${result.data}"}
                    Result.Success(result.data.toLong())
                } catch (e: NumberFormatException) {
                    val errorMessage = "Error al convertir la fecha a Long: ${e.message}"
                    logger.error(throwable = e) {e.message ?: errorMessage}
                    Result.Error(DateError.FormattingToLong)
                }
            }
        }
    }


    private fun isDateMatchSavePattern(
        date: LocalDate,
        locale: Locale = Locale.getDefault()
    ): Result<Unit, Error> {
        val patterns = listOf(
            DateTimePattern.DATETIME_TOSAVE,
            DateTimePattern.TIME_TOSAVE,
            DateTimePattern.DATE_TOSAVE
        )
        val dateFormatterCache = mutableMapOf<DateTimePattern, DateTimeFormatter>()
        for (pattern in patterns) {
            try {
                val dateFormatter = dateFormatterCache.getOrPut(pattern) {
                    DateTimeFormatter.ofPattern(pattern.pattern, locale)
                }
                val formattedDate = dateFormatter.format(date)
                val parsedDate = LocalDate.parse(formattedDate, dateFormatter)
                if (parsedDate == date) {
                    logger.info {"La fecha coincide con el patrón: $pattern"}
                    return Result.Success(Unit)
                }
            } catch (e: DateTimeParseException) {
                continue
            } catch (e: DateTimeException) {
                logger.error(throwable = e) {"Error en el formateo de la fecha."}
                Result.Error(DateError.DuringFormatting)
            }
        }
        logger.warn {"La fecha no coincide con ningun patrón"}
        return Result.Error(DateError.UnknownFormattingWithPattern)
    }

    val nowOnDateSavePattern: Long = run {
        val pattern = DateTimeFormatter.ofPattern(DateTimePattern.DATE_TOSAVE.pattern)
        LocalDate.now().format(pattern).toLong()
    }

    val nowOnTimeSavePattern: Long = run {
        val pattern = DateTimeFormatter.ofPattern(DateTimePattern.TIME_TOSAVE.pattern)
        LocalTime.now().format(pattern).toLong()
    }

    val nowOnDateTimeSavePattern: Long = run {
        val pattern = DateTimeFormatter.ofPattern(DateTimePattern.DATETIME_TOSAVE.pattern)
        LocalDateTime.now().format(pattern).toLong()
    }


    sealed class DateTimePattern(val pattern: String) {
        // DateTime
        data object DATETIME_EXTENDED: DateTimePattern("EEEE, MMM dd, yyyy HH:mm:ss a")
        data object DATETIME_TOSAVE: DateTimePattern("yyyyMMddHHmmss")

        // Time
        data object TIME_TOSAVE: DateTimePattern("HHmmss")
        data object TIME_BASIC12: DateTimePattern("hh:mm a")
        data object TIME_BASIC24: DateTimePattern("HH:mm")

        // Date
        data object DATE_INTERNATIONAL: DateTimePattern("yyyy-MM-dd")
        data object DATE_EXTENDED: DateTimePattern("EEEE, dd MMMM, yyyy")
        data object DATE_TOSAVE: DateTimePattern("yyyyMMdd")
        data object DATE_BASIC: DateTimePattern("dd/MM/yyyy")
        data object DATE_BASICHYPHEN: DateTimePattern("dd-MMM-yyyy")
    }

    class DateTimeMyException(override val message: String? = null): Exception(message)

    const val dateRegex =
        "^(?:(?:19|20)\\d\\d(?:(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[13579][26])00)|(?:19|20)(?:[2468][048]|[13579][26])|(?:0[48]|[13579][26])00)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])$"
    const val timeRegex = "^([01][0-9]|2[0-3])[0-5][0-9][0-5][0-9]$"
    const val dateTimeRegex = "$dateRegex$timeRegex"
}

