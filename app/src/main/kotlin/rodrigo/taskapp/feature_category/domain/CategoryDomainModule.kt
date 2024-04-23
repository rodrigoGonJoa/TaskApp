package rodrigo.taskapp.feature_category.domain

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.feature_category.domain.use_cases.CategoryVerification
import rodrigo.taskapp.feature_category.domain.use_cases.DeleteCategoryUseCase
import rodrigo.taskapp.feature_category.domain.use_cases.GetAllCategoryUseCase
import rodrigo.taskapp.feature_category.domain.use_cases.GetByIdCategoryUseCase
import rodrigo.taskapp.feature_category.domain.use_cases.SaveCategoryUseCase
import rodrigo.taskapp.feature_category.domain.use_cases.UpdateCategoryUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoryDomainModule {

    @Provides
    @Singleton
    fun providesCategoryVerification(): CategoryVerification = CategoryVerification()

    @Provides
    @Singleton
    fun providesDeleteCategoryUseCase(
        categoryRepository: CategoryRepository
    ): DeleteCategoryUseCase = DeleteCategoryUseCase(categoryRepository)

    @Provides
    @Singleton
    fun providesGetByIdCategoryUseCase(
        categoryRepository: CategoryRepository
    ): GetByIdCategoryUseCase = GetByIdCategoryUseCase(categoryRepository)

    @Provides
    @Singleton
    fun providesUpdateCategoryUseCase(
        categoryRepository: CategoryRepository,
        categoryVerification: CategoryVerification
    ): UpdateCategoryUseCase = UpdateCategoryUseCase(categoryRepository, categoryVerification)

    @Provides
    @Singleton
    fun providesSaveCategoryUseCase(
        categoryRepository: CategoryRepository,
        categoryVerification: CategoryVerification
    ): SaveCategoryUseCase = SaveCategoryUseCase(categoryRepository, categoryVerification)

    @Provides
    @Singleton
    fun providesGetAllCategoryUseCase(
        categoryRepository: CategoryRepository
    ): GetAllCategoryUseCase = GetAllCategoryUseCase(categoryRepository)

}
