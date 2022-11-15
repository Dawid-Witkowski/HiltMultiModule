package winged.example.feature_login.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import winged.example.core_data.db.entity.LoginCredentials
import winged.example.core_data.db.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    val registerEvent = MutableLiveData<Result<Int>>()
    fun saveUser(cred: LoginCredentials) {
        viewModelScope.launch {
            repository.saveUser(cred)
            // probably should return something more useful, but that's not the point of this project
            // I'm just presenting how to set up a project with multiple modules
            registerEvent.postValue(Result.success(1))
        }
    }
}