package winged.example.core_data.db.repository

import winged.example.core_data.db.NameDatabase
import winged.example.core_data.db.entity.LoginCredentials

import javax.inject.Inject

class UserRepository @Inject constructor(database: NameDatabase) {
    private val dao = database.getNameDao()

    suspend fun saveUser(cred: LoginCredentials) {
        dao.saveUser(cred)
    }

    suspend fun deleteUser(cred: LoginCredentials) {
        dao.deleteUser(cred)
    }

    suspend fun logIn(cred: LoginCredentials): Int {
        return dao.logIn(cred.mail, cred.password)
    }
}