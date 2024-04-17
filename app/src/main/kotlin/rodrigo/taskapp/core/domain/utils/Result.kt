package rodrigo.taskapp.core.domain.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

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
fun <DATA> Result<DATA, Error>.isSuccess(): Boolean = this is Result.Success

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
fun <DATA> Result<DATA, Error>.isError(): Boolean = this is Result.Error

// ------------------- Extension Functions: Get Result Data -------------------

fun <DATA> Result<DATA, Error>.get(): Result<DATA, Error> {
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
fun <DATA> Result<DATA, Error>.getDataOrElse(defaultValue: () -> DATA): DATA{
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
fun <DATA> Result<DATA, Error>.getErrorOrElse(defaultValue: Error): Error {
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
inline fun <DATA> Result<DATA, Error>.onError(action: (value: Error) -> Unit): Result<DATA, Error> {
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
inline fun <DATA> Result<DATA, Error>.onSuccess(action: (value: DATA) -> Unit): Result<DATA, Error> {
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
inline fun <DATA_IN, DATA_OUT> Result<DATA_IN, Error>.processReturn(
    onError: (Result.Error<Error>) -> Result<DATA_OUT, Error> = {it},
    onSuccess: (Result.Success<DATA_IN>) -> Result<DATA_OUT, Error>
): Result<DATA_OUT, Error> {
    return when (this) {
        is Result.Error -> onError(this)
        is Result.Success -> onSuccess(this)
    }
}

inline fun <DATA> Result<DATA, Error>.process(
    onError: ((Result.Error<Error>) -> Unit),
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
suspend inline fun <DATA> Flow<Result<DATA, Error>>.process(
    crossinline onError: (Result.Error<Error>) -> Unit,
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
fun <DATA_IN, DATA_OUT> Result<DATA_IN, Error>.map(
    dataTransform: ((DATA_IN) -> DATA_OUT)? = null,
    errorTransform: ((Error) -> Error)? = null
): Result<DATA_OUT, Error> {
    return when (this) {
        is Result.Success -> {
            val data = dataTransform?.invoke(this.data) ?: this.data as DATA_OUT
            Result.Success(data)
        }

        is Result.Error -> {
            val error = errorTransform?.invoke(this.error) ?: this.error
            Result.Error(error)
        }
    }
}


/**
 * Returns the flow modifying the error, the success object, or both of the result content.
 * If no modification is set it will return the original flow.
 *
 * @param dataTransform Function to transform success data. If `null`, the original success data is used.
 * @param errorTransform Function to transform error. If `null`, the original error is used.
 * @return A new [Result] instance with transformed data or error.
 * ```
 * val result: Flow<Result<String, CustomError>> = ...
 * val mappedResult = result.map(
 *     dataTransform = { data -> ... },
 *     errorTransform = { error -> ... }
 * )
 * ```
 */
fun <DATA_IN, DATA_OUT> Flow<Result<DATA_IN, Error>>.map(
    dataTransform: ((DATA_IN) -> DATA_OUT)? = null,
    errorTransform: ((Error) -> Error)? = null
): Flow<Result<DATA_OUT, Error>> {
    return this.map {result ->
        when (result) {
            is Result.Success -> {
                val data = dataTransform?.invoke(result.data) ?: result.data as DATA_OUT
                Result.Success(data)
            }

            is Result.Error -> {
                val error = errorTransform?.invoke(result.error) ?: result.error
                Result.Error(error)
            }
        }
    }
}
