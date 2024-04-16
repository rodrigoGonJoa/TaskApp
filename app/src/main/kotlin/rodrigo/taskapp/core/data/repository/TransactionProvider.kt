package rodrigo.taskapp.core.data.repository

import androidx.room.withTransaction
import rodrigo.taskapp.core.data.source.BaseDatabase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionProvider @Inject constructor(
    private val db: BaseDatabase
) {
    suspend fun <R> runAsTransaction(block: suspend () -> R): R {
        return db.withTransaction(block)
    }

    suspend fun <R: Flow<*>> runAsTransactionFlow(block: () -> R): R {
        return db.withTransaction { block() }
    }
}
