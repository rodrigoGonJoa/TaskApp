package rodrigo.taskapp.feature_task.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.feature_category.domain.CategoryRepository
import rodrigo.taskapp.feature_task.domain.use_cases.DeleteTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.GetAllTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.GetByIdTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.SaveTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.SaveTaskWithCategoryUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.TaskVerification
import rodrigo.taskapp.feature_task.domain.use_cases.UpdateTaskUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TaskDomainModule {

    @Provides
    @Singleton
    fun providesTaskVerification(): TaskVerification = TaskVerification()

    @Provides
    @Singleton
    fun providesDeleteTaskUseCase(
        taskRepository: TaskRepository
    ): DeleteTaskUseCase = DeleteTaskUseCase(taskRepository)

    @Provides
    @Singleton
    fun providesGetByIdTaskUseCase(
        taskRepository: TaskRepository
    ): GetByIdTaskUseCase = GetByIdTaskUseCase(taskRepository)

    @Provides
    @Singleton
    fun providesUpdateTaskUseCase(
        taskRepository: TaskRepository,
        taskVerification: TaskVerification
    ): UpdateTaskUseCase = UpdateTaskUseCase(taskRepository, taskVerification)

    @Provides
    @Singleton
    fun providesSaveTaskUseCase(
        taskRepository: TaskRepository,
        taskVerification: TaskVerification
    ): SaveTaskUseCase = SaveTaskUseCase(taskRepository, taskVerification)

    @Provides
    @Singleton
    fun providesGetAllTaskUseCase(
        taskRepository: TaskRepository
    ): GetAllTaskUseCase = GetAllTaskUseCase(taskRepository)

    @Provides
    @Singleton
    fun providesSaveTaskWithCategoryUseCase(
        transactionProvider: TransactionProvider,
        taskRepository: TaskRepository,
        categoryRepository: CategoryRepository
    ): SaveTaskWithCategoryUseCase = SaveTaskWithCategoryUseCase(
        transactionProvider, taskRepository, categoryRepository
    )

}
