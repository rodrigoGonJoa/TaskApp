package rodrigo.taskapp.feature_task.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert
    fun saveTask(task: TaskAndCategory): Long?

    @Update
    fun updateTask(task: TaskAndCategory): Int

    @Delete
    fun deleteTask(task: TaskAndCategory): Int

    @Query("SELECT * FROM task_table WHERE t_id == :taskId")
    fun getTaskById(taskId: Long): TaskAndCategory

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskAndCategory>>
}
