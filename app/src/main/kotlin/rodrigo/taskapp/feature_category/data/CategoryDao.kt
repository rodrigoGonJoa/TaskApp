package rodrigo.taskapp.feature_category.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCategory(category: CategoryEntity): Long?

    @Delete
    fun deleteCategory(category: CategoryEntity): Int

    @Query("SELECT * FROM category_table WHERE c_id == :categoryId")
    fun getCategoryById(categoryId: Long): CategoryEntity

    @Query("SELECT * FROM category_table")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}
