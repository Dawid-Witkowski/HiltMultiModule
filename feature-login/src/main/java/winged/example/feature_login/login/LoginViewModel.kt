package winged.example.feature_login.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import winged.example.core_data.db.entity.LoginCredentials
import winged.example.core_data.db.repository.UserRepository
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    val loginEvent = MutableLiveData<Result<Int>>()
    fun logIn(cred: LoginCredentials) {
        viewModelScope.launch {
            if(repository.logIn(cred) == 1) {
                loginEvent.postValue(Result.success(1))
            } else {
                loginEvent.postValue(Result.failure(Throwable("No account found")))
            }
        }
    }
}