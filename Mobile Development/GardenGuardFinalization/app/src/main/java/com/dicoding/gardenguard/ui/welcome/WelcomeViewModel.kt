package com.dicoding.gardenguard.ui.welcome

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.gardenguard.data.repository.UserRepository

class WelcomeViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getSession(): LiveData<String> {
        return userRepository.getSession().asLiveData()
    }
}