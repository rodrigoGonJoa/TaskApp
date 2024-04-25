package rodrigo.taskapp.feature_task.domain

import rodrigo.taskapp.core.domain.repository.BaseRepository
import rodrigo.taskapp.core.domain.utils.Result
import rodrigo.taskapp.core.domain.utils.error.RoomError

interface TaskRepository: BaseRepository<Task> {
    suspend fun saveWithCategory(task: Task): Result<Task, RoomError>
}
