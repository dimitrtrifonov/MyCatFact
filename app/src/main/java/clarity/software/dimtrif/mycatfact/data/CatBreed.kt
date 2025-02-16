package clarity.software.dimtrif.mycatfact.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cat_breeds")
data class CatBreed(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val breed: String,
    val country: String,
    val origin: String,
    val coat: String,
    val pattern: String
)
