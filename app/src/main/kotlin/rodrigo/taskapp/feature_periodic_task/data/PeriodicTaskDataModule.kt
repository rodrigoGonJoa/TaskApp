package rodrigo.taskapp.feature_periodic_task.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.data.source.TaskDatabase
import rodrigo.taskapp.feature_periodic_task.domain.PeriodicTaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PeriodicTaskDataModule {

    @Provides
    @Singleton
    fun providesPeriodicTaskDao(database: TaskDatabase): PeriodicTaskDao =
        database.getPeriodicTaskDao()


    @Provides
    @Singleton
    fun providesPeriodicTaskRepository(
        periodicTaskDao: PeriodicTaskDao,
        transactionProvider: TransactionProvider
    ): PeriodicTaskRepository = PeriodicTaskRepositoryImpl(periodicTaskDao, transactionProvider)

}
