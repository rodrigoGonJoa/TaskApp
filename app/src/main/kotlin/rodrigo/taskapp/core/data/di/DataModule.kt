package rodrigo.taskapp.core.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rodrigo.taskapp.core.data.repository.TransactionProvider
import rodrigo.taskapp.core.data.source.TaskDatabase
import rodrigo.taskapp.core.data.utils.DataConstants
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext appContext: Context): TaskDatabase {
        return Room.databaseBuilder(
            context = appContext,
            TaskDatabase::class.java,
            DataConstants.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providesTransactionProvider(database: TaskDatabase) = TransactionProvider(database)
}
