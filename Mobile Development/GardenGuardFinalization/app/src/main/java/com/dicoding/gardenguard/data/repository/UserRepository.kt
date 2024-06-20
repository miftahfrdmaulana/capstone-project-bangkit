package com.dicoding.gardenguard.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dicoding.gardenguard.data.response.LoginResponse
import com.dicoding.gardenguard.data.response.RegisterResponse
import com.dicoding.gardenguard.data.preference.UserPreference
import com.dicoding.gardenguard.data.response.ProfileResponse
import com.dicoding.gardenguard.data.retrofit.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService,
) {

    suspend fun saveSession(token: String) {
        userPreference.saveToken(token)
    }

    fun getSession(): Flow<String> {
        return userPreference.getToken()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    fun postRegister(
        username: String,
        email: String,
        password: String
    ): LiveData<com.dicoding.gardenguard.data.Result<RegisterResponse>> = liveData {
        emit(com.dicoding.gardenguard.data.Result.Loading)
        try {
            val response = apiService.signup(email, username, password)
            emit(com.dicoding.gardenguard.data.Result.Success(response))
        } catch (e: Exception) {
            emit(com.dicoding.gardenguard.data.Result.Error(e.message.toString()))
        }
    }

    fun postLogin(
        username: String,
        password: String
    ): LiveData<com.dicoding.gardenguard.data.Result<LoginResponse>> = liveData {
        emit(com.dicoding.gardenguard.data.Result.Loading)
        try {
            val response = apiService.signin(username, password)
            emit(com.dicoding.gardenguard.data.Result.Success(response))
        } catch (e: HttpException) {
            emit(com.dicoding.gardenguard.data.Result.Error(e.message.toString()))
        }
    }

    fun getProfile(): LiveData<com.dicoding.gardenguard.data.Result<ProfileResponse>> = liveData {
        emit(com.dicoding.gardenguard.data.Result.Loading)
        try {
            val token = runBlocking { userPreference.getToken().first()}
            val finalToken = "Bearer $token"
            val response = apiService.getProfile(finalToken)
            emit(com.dicoding.gardenguard.data.Result.Success(response))
        } catch (e: HttpException) {
            emit(com.dicoding.gardenguard.data.Result.Error(e.message.toString()))
        }
    }




    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}