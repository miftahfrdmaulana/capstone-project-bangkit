package com.dicoding.gardenguard.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.gardenguard.data.Result
import com.dicoding.gardenguard.data.repository.UserRepository
import com.dicoding.gardenguard.data.response.ProfileResponse
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserRepository) : ViewModel() {

  fun getProfile(): LiveData<Result<ProfileResponse>> = repository.getProfile()

    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }
}