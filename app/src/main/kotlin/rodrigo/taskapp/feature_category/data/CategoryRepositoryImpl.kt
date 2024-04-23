package rodrigo.taskapp.feature_category.data

import kotlinx.coroutines.flow.Flow
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.RoomError
import rodrigo.taskapp.feature_category.domain.Category
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val transactionProvider: TransactionProvider
): CategoryRepository {
    override suspend fun save(item: Category): Result<Long, RoomError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Category): Result<Unit, RoomError> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(item: Category): Result<Unit, RoomError> {
        TODO("Not yet implemented")
    }

    override suspend fun getById(itemId: Long): Result<Category?, RoomError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(): Flow<Result<List<Category>, RoomError>> {
        TODO("Not yet implemented")
    }
}
