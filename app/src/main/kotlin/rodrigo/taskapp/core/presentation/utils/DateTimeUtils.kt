package rodrigo.taskapp.core.presentation.utils

import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.ErrorDate
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.process
import rodrigo.taskapp.core.domain.utils.processReturn
import io.github.oshai.kotlinlogging.KotlinLogging
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object DateTimeUtils {
    private val logger = KotlinLogging.logger {}

    fun millisToLocalDate(dateInMillis: Long?): Result<LocalDate, Error> {
        if (dateInMillis == null) {
            logger.error {"La marca de tiempo es nula. Se devuelve una fecha nula."}
            return Result.Error(ErrorDate.NullLocalDate)
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
            Result.Error(ErrorDate.MillisToLocalDate)
        }
    }

    fun localDateToStringWithPattern(
        date: LocalDate?,
        pattern: DateTimePattern = DateTimePattern.DATETIME_TOSAVE,
        locale: Locale = Locale.getDefault()
    ): Result<String, Error> {
        if (date == null) {
            logger.error {"La marca de tiempo es nula. Se devuelve una fecha nula."}
            return Result.Error(ErrorDate.NullLocalDate)
        }
        return try {
            val dateFormatter = DateTimeFormatter.ofPattern(pattern.pattern, locale)
            val dateResult = dateFormatter.format(date)
            logger.info {"Fecha formateada: $dateResult con patrón: ${pattern.pattern}"}
            Result.Success(dateResult)
        } catch (e: IllegalArgumentException) {
            val message = "El patrón de formato de fecha '$pattern' es inválido."
            logger.error(throwable = e) {message}
            Result.Error(ErrorDate.InvalidPattern)
        } catch (e: DateTimeException) {
            val message = "Error en el formateo de la fecha."
            logger.error(throwable = e) {message}
            Result.Error(ErrorDate.DuringFormatting)
        } catch (e: Exception) {
            val message =
                "Error desconocido al formatear la fecha: $date con patrón: ${pattern.pattern}"
            logger.error(throwable = e) {message}
            Result.Error(ErrorDate.UnknownFormattingWithPattern)
        }
    }

    fun localDateToLong(date: LocalDate, pattern: DateTimePattern): Result<Long, Error> {
        fun isDateMatchSavePattern() = isDateMatchSavePattern(date).process(
            onSuccess = {result -> if (!result.data) throw DateTimeMyException()})

        return localDateToStringWithPattern(date, pattern).processReturn(
            onSuccess = {process ->
                try {
                    isDateMatchSavePattern()
                    logger.info {"Fecha formateada a Long: ${process.data}"}
                    Result.Success(process.data.toLong())
                } catch (e: NumberFormatException) {
                    val errorMessage = "Error al convertir la fecha a Long: ${e.message}"
                    logger.error(throwable = e) {e.message ?: errorMessage}
                    Result.Error(ErrorDate.FormattingToLong)
                } catch (e: DateTimeMyException) {
                    val errorMessage = "El patrón '$pattern' no es compatible"
                    logger.error(throwable = e) {errorMessage}
                    Result.Error(ErrorDate.UnsupportedPattern)
                }
            }
        )
    }


    private fun isDateMatchSavePattern(
        date: LocalDate,
        locale: Locale = Locale.getDefault()
    ): Result<Boolean, Error> {
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
                    return Result.Success(true)
                }
            } catch (e: DateTimeParseException) {
                continue
            } catch (e: DateTimeException) {
                val message = "Error en el formateo de la fecha."
                logger.error(throwable = e) {message}
                Result.Error(ErrorDate.DuringFormatting)
            } catch (e: Exception) {
                val message =
                    "Error desconocido al formatear la fecha: $date con patrón: ${pattern.pattern}"
                logger.error(throwable = e) {message}
                Result.Error(ErrorDate.UnknownFormattingWithPattern)
            }
        }
        logger.warn {"La fecha no coincide con ningun patrón"}
        return Result.Success(false)
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
}

