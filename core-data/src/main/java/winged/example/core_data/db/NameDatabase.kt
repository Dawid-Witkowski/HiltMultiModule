package winged.example.core_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import winged.example.core_data.db.dao.LoginDao
import winged.example.core_data.db.entity.LoginCredentials


@Database(entities = [LoginCredentials::class], version = 1, exportSchema = false)
abstract class NameDatabase: RoomDatabase() {
    abstract fun getNameDao(): LoginDao
}