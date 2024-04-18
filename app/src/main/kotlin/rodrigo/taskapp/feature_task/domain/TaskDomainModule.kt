package rodrigo.taskapp.feature_task.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.feature_task.domain.use_cases.DeleteTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.GetAllTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.GetByIdTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.SaveTaskUseCase
import rodrigo.taskapp.feature_task.domain.use_cases.TaskVerification
import rodrigo.taskapp.feature_task.domain.use_cases.UpdateTaskUseCase
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TaskDomainModule {


    @Provides
    @Singleton
    fun providesTaskVerification(taskRepository: TaskRepository) = TaskVerification()

    @Provides
    @Singleton
    fun providesDeleteTaskUseCase(taskRepository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun providesGetByIdTaskUseCase(taskRepository: TaskRepository): GetByIdTaskUseCase {
        return GetByIdTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun providesUpdateTaskUseCase(
        taskRepository: TaskRepository,
        taskVerification: TaskVerification
    ): UpdateTaskUseCase {
        return UpdateTaskUseCase(taskRepository, taskVerification)
    }

    @Provides
    @Singleton
    fun providesSaveTaskUseCase(
        taskRepository: TaskRepository,
        taskVerification: TaskVerification
    ): SaveTaskUseCase {
        return SaveTaskUseCase(taskRepository, taskVerification)
    }

    @Provides
    @Singleton
    fun providesGetAllTasksUseCase(
        taskRepository: TaskRepository
    ): GetAllTaskUseCase {
        return GetAllTaskUseCase(taskRepository)
    }

}
