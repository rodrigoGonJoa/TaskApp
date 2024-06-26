package rodrigo.taskapp.core.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import rodrigo.taskapp.core.domain.utils.error.Error

// https://www.youtube.com/watch?v=MiLN2vs2Oe0

typealias RootError = Error

/**
 * Represents the result of an operation that can be either a success, an error, or a loading state.
 * @param DATA The type of data returned on success.
 * @param ERROR The type of error returned in case of failure.
 */
sealed class Result<out DATA, out ERROR: RootError> {
    /**
     * Represents a successful result with the returned data.
     * @param D The type of data returned.
     * @property data The data returned on success.
     */
    data class Success<out DATA>(val data: DATA): Result<DATA, Nothing>()

    /**
     * Represents an error result with the error information.
     * @param E The type of error.
     * @property error The error information.
     */
    data class Error<out ERROR: RootError>(
        val error: ERROR,
        val throwable: Throwable? = null
    ): Result<Nothing, ERROR>()
}

// ------------------- Extension Functions: Check Result Type -------------------

/**
 * Checks if the result represents a successful state.
 * @return true if the result is a success, false otherwise.
 * ```
 * val result: Result<String, CustomError> = ...
 * if (result.isSuccess()) {
 *     println("Success")
 * } else {
 *     println("Not successful")
 * }
 * ```
 */
fun <DATA, ERROR: Error> Result<DATA, ERROR>.isSuccess(): Boolean = this is Result.Success

/**
 * Checks if the result represents an error state.
 * @return true if the result is an error, false otherwise.
 * ```
 * val result: Result<String, CustomError> = ...
 * if (result.isError()) {
 *     println("Error")
 * } else {
 *     println("No error")
 * }
 * ```
 */
fun <DATA, ERROR: Error> Result<DATA, ERROR>.isError(): Boolean = this is Result.Error

// ------------------- Extension Functions: Get Result Data -------------------

fun <DATA, ERROR: Error> Result<DATA, ERROR>.get(): Result<DATA, ERROR> {
    return when (this) {
        is Result.Success -> this
        is Result.Error -> this
    }
}

/**
 * Returns the data if the result is success, otherwise returns the default value.
 * @param defaultValue The default value to return in case of error or loading state.
 * @return The data on success, or the default value otherwise.
 * ```
 * val result: Result<String, CustomError> = ...
 * val data: String = result.getDataOrElse("Default value")
 * ```
 */
fun <DATA, ERROR: Error> Result<DATA, ERROR>.getDataOrElse(defaultValue: () -> DATA): DATA {
    return when (this) {
        is Result.Success -> this.data
        else -> defaultValue()
    }
}

/**
 * Returns the data if the result is error, otherwise returns the default value.
 * @param defaultValue The default value to return in case of success or loading state.
 * @return The data on error, or the default value otherwise.
 * ```
 * val result: Result<String, CustomError> = ...
 * val error: RootError = result.getErrorOrElse(Error.UNKNOWN)
 * ```
 */
fun <DATA, ERROR: Error> Result<DATA, ERROR>.getErrorOrElse(defaultValue: ERROR): ERROR {
    return when (this) {
        is Result.Error -> this.error
        else -> defaultValue
    }
}

// ------------------- Extension Functions: Actions By Type -------------------

/**
 * Performs an action when the result is an error.
 * @param action The action to perform on error.
 * @return The result itself.
 * ```
 * val result: Result<String, CustomError> = ...
 * result.doOnError { error ->
 *     ...
 * }
 * ```
 */
inline fun <DATA, ERROR: Error> Result<DATA, ERROR>.onError(action: (value: Error) -> Unit): Result<DATA, ERROR> {
    if (this is Result.Error) action(error)
    return this
}

/**
 * Performs an action when the result is a success.
 * @param action The action to perform on success.
 * @return The result itself.
 * ```
 * val result: Result<String, CustomError> = ...
 * result.doOnSuccess { success ->
 *     ...
 * }
 * ```
 */
inline fun <DATA, ERROR: Error> Result<DATA, ERROR>.onSuccess(action: (value: DATA) -> Unit): Result<DATA, ERROR> {
    if (this is Result.Success) action(data)
    return this
}

// ------------------- Extension Functions: Process Result -------------------

/**
 * Processes the result and performs corresponding actions based on its type.
 * @param onError Action to perform on error.
 * @param onSuccess Action to perform on success.
 * ```
 * val flow: Flow<Result<String, CustomError>> = ...
 * flow.process(
 *     onError = { error -> ... },
 *     onSuccess = { success -> ... }
 * )
 * ```
 */
inline fun <DATA_IN, reified DATA_OUT, ERROR_IN: Error, reified ERROR_OUT: Error> Result<DATA_IN, ERROR_IN>.processReturn(
    crossinline onError: (Result.Error<ERROR_IN>) -> Result<DATA_OUT, ERROR_OUT> = {
        Result.Error(it.error as ERROR_OUT)
    },
    crossinline onSuccess: (Result.Success<DATA_IN>) -> Result<DATA_OUT, ERROR_OUT>
): Result<DATA_OUT, ERROR_OUT> {
    return when (this) {
        is Result.Error -> onError(this)
        is Result.Success -> onSuccess(this)
    }
}

suspend inline fun <DATA_IN, reified DATA_OUT, ERROR_IN: Error, reified ERROR_OUT: Error> Result<DATA_IN, ERROR_IN>.processReturn(
    crossinline onError: (Result.Error<ERROR_IN>) -> Result<DATA_OUT, ERROR_OUT> = {
        Result.Error(it.error as ERROR_OUT)
    },
    crossinline onSuccess: suspend (Result.Success<DATA_IN>) -> Result<DATA_OUT, ERROR_OUT>
): Result<DATA_OUT, ERROR_OUT> {
    return when (this) {
        is Result.Error -> onError(this)
        is Result.Success -> onSuccess(this)
    }
}

inline fun <DATA_IN, DATA_OUT, ERROR_IN: Error, reified ERROR_OUT: Error> Flow<Result<DATA_IN, ERROR_IN>>.processReturn(
    crossinline onError: (Result.Error<ERROR_IN>) -> Result<DATA_OUT, ERROR_OUT> = {
        Result.Error(it.error as ERROR_OUT)
    },
    crossinline onSuccess: (Result.Success<DATA_IN>) -> Result<DATA_OUT, ERROR_OUT>
): Flow<Result<DATA_OUT, ERROR_OUT>> {
    return this.map {value ->
        when (value) {
            is Result.Error -> onError(value)
            is Result.Success -> onSuccess(value)
        }
    }
}


inline fun <DATA, ERROR: Error> Result<DATA, ERROR>.process(
    onError: (Result.Error<ERROR>) -> Unit,
    onSuccess: (Result.Success<DATA>) -> Unit
) {
    when (this) {
        is Result.Error -> onError(this)
        is Result.Success -> onSuccess(this)
    }
}


/**
 * Processes the flow of results asynchronously and performs corresponding actions based on its type.
 * @param onError Action to perform on error.
 * @param onSuccess Action to perform on success.
 * ```
 * val flow: Flow<Result<String, CustomError>> = ...
 * flow.process(
 *     onError = { error -> ... },
 *     onSuccess = { success -> ... }
 * )
 * ```
 */
suspend inline fun <DATA, ERROR: Error> Flow<Result<DATA, ERROR>>.process(
    crossinline onError: (Result.Error<ERROR>) -> Unit,
    crossinline onSuccess: (Result.Success<DATA>) -> Unit
) {
    this.collect {value ->
        when (value) {
            is Result.Error -> onError(value)
            is Result.Success -> onSuccess(value)
        }
    }
}

// ------------------- Extension Functions: Transform -------------------

/**
 * Maps the result to a new result with a potentially different data type and error type.
 * Allows transforming both success data and error in a single call.
 *
 * @param dataTransform Function to transform success data. If `null`, the original success data is used.
 * @param errorTransform Function to transform error. If `null`, the original error is used.
 * @return A new [Result] instance with transformed data or error.
 * ```
 * val result: Result<String, CustomError> = ...
 * val mappedResult = result.map(
 *     dataTransform = { data -> ... },
 *     errorTransform = { error -> ... }
 * )
 * ```
 */
inline fun <DATA_IN, reified DATA_OUT, ERROR_IN: Error, reified ERROR_OUT: Error> Result<DATA_IN, ERROR_IN>.map(
    crossinline dataTransform: (DATA_IN) -> DATA_OUT = {it as DATA_OUT},
    crossinline errorTransform: (ERROR_IN) -> ERROR_OUT = {it as ERROR_OUT}
): Result<DATA_OUT, ERROR_OUT> {
    return when (this) {
        is Result.Success -> Result.Success(dataTransform(this.data))
        is Result.Error -> Result.Error(errorTransform(this.error))
    }
}
