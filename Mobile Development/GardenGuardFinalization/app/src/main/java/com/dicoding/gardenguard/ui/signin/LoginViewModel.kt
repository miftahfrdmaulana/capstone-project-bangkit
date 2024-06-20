package com.dicoding.gardenguard.ui.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.gardenguard.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(username: String, password: String) =
        repository.postLogin(username, password)

    fun saveTokenSession(token: String) {
        viewModelScope.launch {
            repository.saveSession(token)
        }
    }

}