package rodrigo.taskapp.core.data.source

import androidx.room.Database
import rodrigo.taskapp.core.data.source.BaseDatabase
import rodrigo.taskapp.feature_category.data.CategoryDao
import rodrigo.taskapp.feature_category.data.CategoryEntity
import rodrigo.taskapp.feature_periodictask.data.PeriodicTaskDao
import rodrigo.taskapp.feature_periodictask.data.PeriodicTaskEntity
import rodrigo.taskapp.feature_task.data.TaskDao
import rodrigo.taskapp.feature_task.data.TaskEntity

@Database(
    entities = [TaskEntity::class, CategoryEntity::class, PeriodicTaskEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase: BaseDatabase() {
    abstract fun getTaskDao(): TaskDao
    abstract fun getPeriodicTaskDao(): PeriodicTaskDao
    abstract fun getCategoryDao(): CategoryDao
}
