package winged.example.core_data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import winged.example.core_data.db.entity.LoginCredentials


@Dao
interface LoginDao {
    @Insert
    suspend fun saveUser(loginCredentials: LoginCredentials)

    @Delete
    suspend fun deleteUser(loginCredentials: LoginCredentials)

    // This just checks whether an user with given mail and login exists,
    // if so, return 1, if not, return 0
    @Query("SELECT COUNT(*) FROM usertable WHERE mail = :mail AND password = :password")
    suspend fun logIn(mail: String, password: String): Int
}