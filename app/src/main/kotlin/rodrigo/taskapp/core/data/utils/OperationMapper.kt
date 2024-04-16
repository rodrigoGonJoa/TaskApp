package rodrigo.taskapp.core.data.utils

import rodrigo.taskapp.core.data.model.BaseEntity
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.model.BaseModel
import rodrigo.taskapp.core.domain.utils.Error
import rodrigo.taskapp.core.domain.utils.ErrorRoom
import rodrigo.taskapp.core.domain.utils.Result
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

object OperationMapper {

    val logger = KotlinLogging.logger("OperationMapper")

    suspend inline fun <IN, OUT> mapOperationToResult(
        crossinline operation: suspend () -> IN,
        crossinline validationOverResult: (IN) -> Boolean = {false},
        crossinline isNotValid: () -> Boolean = {false},
        crossinline resultMapper: (IN) -> OUT = {Unit as OUT},
        error: Error,
        transactionProvider: TransactionProvider
    ): Result<OUT, Error> {
        return try {
            transactionProvider.runAsTransaction {
                val result = operation()
                if (isNotValid()) throw ExceptionRoom()
                if (validationOverResult(result)) throw ExceptionRoom()
                Result.Success(resultMapper(result))
            }
        } catch (exception: ExceptionRoom) {
            logger.error(exception) {"Error ExceptionRoom-$error"}
            Result.Error(error, exception)
        } catch (exception: Exception) {
            logger.error(
                exception
            ) {"Error Exception-${exception.cause?.javaClass?.simpleName}"}
            Result.Error(ErrorRoom.Unknown, exception)
        }
    }


    suspend inline fun mapDeleteToResult(
        transactionProvider: TransactionProvider,
        crossinline isNotValid: () -> Boolean = {false},
        crossinline operation: suspend () -> Int,
    ): Result<Unit, Error> {
        return mapOperationToResult(
            operation = operation,
            error = ErrorRoom.ErrorOnDelete,
            validationOverResult = {result -> result != 1},
            transactionProvider = transactionProvider,
            isNotValid = isNotValid
        )
    }

    suspend inline fun mapUpdateToResult(
        transactionProvider: TransactionProvider,
        crossinline operation: suspend () -> Int
    ): Result<Unit, Error> {
        return mapOperationToResult(
            operation = operation,
            error = ErrorRoom.ErrorOnUpdate,
            validationOverResult = {result -> result != 1},
            transactionProvider = transactionProvider
        )
    }

    suspend inline fun mapAddToResult(
        transactionProvider: TransactionProvider,
        crossinline operation: suspend () -> Long
    ): Result<Long, Error> {
        return mapOperationToResult(
            operation = operation,
            error = ErrorRoom.ErrorOnAdd,
            transactionProvider = transactionProvider,
            resultMapper = {it}
        )
    }

    suspend inline fun <T: BaseEntity<*>, R: BaseModel<*>> mapGetToResult(
        transactionProvider: TransactionProvider,
        crossinline operation: suspend () -> T
    ): Result<R, Error> {
        return mapOperationToResult(
            operation = operation,
            error = ErrorRoom.ErrorOnGet,
            resultMapper = {it.mapToModel() as R},
            transactionProvider = transactionProvider
        )
    }

    inline fun <IN: BaseEntity<*>, OUT: BaseModel<*>> mapOperationToFlowResult(
        crossinline operation: () -> Flow<List<IN>>,
        crossinline dataMapper: (IN) -> OUT
    ): Flow<Result<List<OUT>, Error>> {
        return operation().map {list ->
            if (list.isEmpty()) throw ExceptionRoom()
            logger.info {"Success Getting Item List"}
            Result.Success(list.map {data -> dataMapper(data)}) as Result<List<OUT>, Error>
        }.catch {exception ->
            logger.error {"Error Getting Item List"}
            emit(
                when (exception) {
                    is ExceptionRoom -> Result.Error(ErrorRoom.ErrorOnGetGroup, exception)
                    else -> Result.Error(ErrorRoom.Unknown, exception)
                }
            )
        }
    }
}


