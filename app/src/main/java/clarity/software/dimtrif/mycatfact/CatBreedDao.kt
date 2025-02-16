package clarity.software.dimtrif.mycatfact

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CatBreedDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(catBreeds: List<CatBreed>)

    @Query("SELECT * FROM cat_breeds")
    suspend fun getAllBreeds(): List<CatBreed>

    @Query("DELETE FROM cat_breeds")
    suspend fun deleteAll()
}