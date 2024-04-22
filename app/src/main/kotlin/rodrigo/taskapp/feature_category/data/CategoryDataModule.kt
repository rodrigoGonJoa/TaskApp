package rodrigo.taskapp.feature_category.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.data.source.TaskDatabase
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryDataModule {

    @Provides
    @Singleton
    fun providesCategoryDao(database: TaskDatabase): CategoryDao = database.getCategoryDao()

    @Provides
    @Singleton
    fun providesCategoryRepository(
        categoryDao: CategoryDao,
        transactionProvider: TransactionProvider
    ): CategoryRepository = CategoryRepositoryImpl(categoryDao, transactionProvider)
}
