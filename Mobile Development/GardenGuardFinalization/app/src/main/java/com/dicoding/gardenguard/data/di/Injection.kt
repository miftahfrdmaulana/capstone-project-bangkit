package com.dicoding.gardenguard.data.di

import android.content.Context
import com.dicoding.gardenguard.data.repository.PlantRepository
import com.dicoding.gardenguard.data.repository.UserRepository
import com.dicoding.gardenguard.data.preference.UserPreference
import com.dicoding.gardenguard.data.preference.dataStore
import com.dicoding.gardenguard.data.retrofit.ApiConfig
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val token = runBlocking { preference.getToken().first()}
        val apiService = ApiConfig.getApiService(token)
        return UserRepository.getInstance(apiService, preference)
    }

    fun providePlantRepository(context: Context): PlantRepository {
        val preference = UserPreference.getInstance(context.dataStore)
        val token = runBlocking { preference.getToken().first()}
        val apiService = ApiConfig.getApiService(token)
        return PlantRepository.getInstance(preference, apiService)
    }
}