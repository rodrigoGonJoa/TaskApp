package rodrigo.taskapp.core.di

import android.content.Context
import rodrigo.taskapp.MainApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): MainApplication {
        return app as MainApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: MainApplication): Context {
        return application.applicationContext
    }

}
