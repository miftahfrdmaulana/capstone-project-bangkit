package com.dicoding.gardenguard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.gardenguard.data.repository.PlantRepository
import com.dicoding.gardenguard.data.repository.UserRepository
import com.dicoding.gardenguard.data.di.Injection
import com.dicoding.gardenguard.ui.home.HomeViewModel
import com.dicoding.gardenguard.ui.diagnose.DiagnoseViewModel
import com.dicoding.gardenguard.ui.diagnose.UploadImageViewModel
import com.dicoding.gardenguard.ui.disease.DiseaseDetailViewModel
import com.dicoding.gardenguard.ui.profile.ProfileViewModel
import com.dicoding.gardenguard.ui.signin.LoginViewModel
import com.dicoding.gardenguard.ui.signup.SignupViewModel
import com.dicoding.gardenguard.ui.welcome.WelcomeViewModel

class ViewModelFactory(
    private val userRepository: UserRepository, private val plantRepository: PlantRepository
) : ViewModelProvider.NewInstanceFactory() {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(SignupViewModel::class.java) -> {
                SignupViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(WelcomeViewModel::class.java) -> {
                WelcomeViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userRepository) as T
            }

            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(plantRepository) as T
            }

            modelClass.isAssignableFrom(DiagnoseViewModel::class.java) -> {
                DiagnoseViewModel(plantRepository) as T
            }

            modelClass.isAssignableFrom(UploadImageViewModel::class.java) -> {
                UploadImageViewModel(plantRepository) as T
            }

            modelClass.isAssignableFrom(DiseaseDetailViewModel::class.java) -> {
                DiseaseDetailViewModel(plantRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(
                        Injection.provideUserRepository(context),
                        Injection.providePlantRepository(context)
                    )
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

}