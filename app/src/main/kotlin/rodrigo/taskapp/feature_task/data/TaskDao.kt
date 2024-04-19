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
    fun saveTask(task: TaskEntity): Long?

    @Update
    fun updateTask(task: TaskEntity): Int

    @Delete
    fun deleteTask(task: TaskEntity): Int

    @Query("SELECT * FROM task_table WHERE task_id == :taskId")
    fun getTaskById(taskId: Long): TaskEntity

    @Query("SELECT * FROM task_table")
    fun getAllTasks(): Flow<List<TaskEntity>>
}
