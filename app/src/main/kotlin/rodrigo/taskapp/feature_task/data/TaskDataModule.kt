package rodrigo.taskapp.feature_task.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.data.source.TaskDatabase
import rodrigo.taskapp.feature_task.domain.TaskRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskDataModule {

    @Provides
    @Singleton
    fun providesTaskDao(taskDatabase: TaskDatabase): TaskDao {
        return taskDatabase.getTaskDao()
    }

    @Provides
    @Singleton
    fun providesTaskRepository(
        taskDao: TaskDao,
        transactionProvider: TransactionProvider
    ): TaskRepository {
        return TaskRepositoryImpl(taskDao, transactionProvider)
    }

}
