package rodrigo.taskapp.feature_periodictask.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.data.source.TaskDatabase
import rodrigo.taskapp.feature_periodictask.domain.PeriodicTaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PeriodicTaskDataModule {

    @Provides
    @Singleton
    fun providesPeriodicTaskDao(database: TaskDatabase): PeriodicTaskDao {
        return database.getPeriodicTaskDao()
    }

    @Provides
    @Singleton
    fun providesPeriodicTaskRepository(
        periodicTaskDao: PeriodicTaskDao,
        transactionProvider: TransactionProvider
    ): PeriodicTaskRepository{
        return PeriodicTaskRepositoryImpl(periodicTaskDao, transactionProvider)
    }
}
