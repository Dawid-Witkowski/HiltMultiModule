package winged.example.core_data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import winged.example.core_data.utils.Constants

@Entity(tableName = Constants.user_table_name)
data class LoginCredentials(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val mail: String,
    val password: String
)