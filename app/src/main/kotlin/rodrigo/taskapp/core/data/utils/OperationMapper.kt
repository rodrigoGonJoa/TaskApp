package rodrigo.taskapp.core.data.utils

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.ErrorRoom

object OperationMapper {

    val logger = KotlinLogging.logger(this.javaClass.simpleName)

    suspend inline fun <IN, OUT> mapOperationToResult(
        crossinline operation: suspend () -> IN?,
        crossinline validationOverResult: (IN) -> Boolean = {false},
        crossinline isNotValid: () -> Boolean = {false},
        crossinline resultMapper: (IN) -> OUT = {Unit as OUT},
        error: ErrorRoom,
        transactionProvider: TransactionProvider
    ): Result<OUT, ErrorRoom> = try {
        transactionProvider.runAsTransaction {
            val result = operation() ?: throw NullPointerException()
            if (isNotValid()) throw ExceptionRoom()
            if (validationOverResult(result)) throw ExceptionRoom()
            val resultMapped = resultMapper(result)
            logger.info {"✔ Success: Getting item list"}
            Result.Success(resultMapped)
        }
    } catch (exception: ExceptionRoom) {
        logger.error(exception) {"✘ ExceptionRoom: $error."}
        Result.Error(error, exception)
    } catch (exception: NullPointerException) {
        logger.error(exception) {"✘ NullPointerException: The result was null."}
        Result.Error(ErrorRoom.NULL, exception)
    } catch (exception: Exception) {
        logger.error(exception) {"✘ Exception: ${exception.cause?.javaClass?.simpleName}."}
        Result.Error(ErrorRoom.UNKNOWN, exception)
    }

    suspend inline fun mapDeleteToResult(
        transactionProvider: TransactionProvider,
        crossinline isNotValid: () -> Boolean = {false},
        crossinline operation: suspend () -> Int,
    ): Result<Unit, ErrorRoom> = mapOperationToResult(
        operation = operation,
        error = ErrorRoom.DELETE,
        validationOverResult = {result -> result != 1},
        transactionProvider = transactionProvider,
        isNotValid = isNotValid
    )

    suspend inline fun mapUpdateToResult(
        transactionProvider: TransactionProvider,
        crossinline operation: suspend () -> Int
    ): Result<Unit, ErrorRoom> = mapOperationToResult(
        operation = operation,
        error = ErrorRoom.UPDATE,
        validationOverResult = {result -> result != 1},
        transactionProvider = transactionProvider
    )

    suspend inline fun mapAddToResult(
        transactionProvider: TransactionProvider,
        crossinline operation: suspend () -> Long?
    ): Result<Long, ErrorRoom> = mapOperationToResult(
        operation = operation,
        error = ErrorRoom.ADD,
        transactionProvider = transactionProvider,
        resultMapper = {it}
    )

    suspend inline fun <ENTITY: BaseEntity<*>, MODEL: BaseModel<*>> mapGetToResult(
        transactionProvider: TransactionProvider,
        crossinline operation: suspend () -> ENTITY,
    ): Result<MODEL, ErrorRoom> = mapOperationToResult(
        operation = operation,
        error = ErrorRoom.GET,
        resultMapper = {it.mapToModel() as MODEL},
        transactionProvider = transactionProvider
    )

    inline fun <ENTITY: BaseEntity<*>, MODEL: BaseModel<*>> mapOperationToFlowResult(
        crossinline operation: () -> Flow<List<ENTITY>>
    ): Flow<Result<List<MODEL>, ErrorRoom>> {
        return operation().map {list ->
            if (list.isEmpty()) throw ExceptionRoom()
            logger.info {"✔ Success: Getting item list"}
            Result.Success(list.map {data -> data.mapToModel()}) as Result<List<MODEL>, ErrorRoom>
        }.catch {exception ->
            logger.error {"✘ Error: Getting item list"}
            emit(
                when (exception) {
                    is ExceptionRoom -> Result.Error(ErrorRoom.GET_GROUP, exception)
                    else -> Result.Error(ErrorRoom.UNKNOWN, exception)
                }
            )
        }
    }
}
