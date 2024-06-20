package com.dicoding.gardenguard.ui.signup

import androidx.lifecycle.ViewModel
import com.dicoding.gardenguard.data.repository.UserRepository

class SignupViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(username: String, email: String, password: String) =
        repository.postRegister(username, email, password)

}