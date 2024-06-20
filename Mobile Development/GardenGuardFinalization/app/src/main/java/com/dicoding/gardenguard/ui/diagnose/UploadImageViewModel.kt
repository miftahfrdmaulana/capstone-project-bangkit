package com.dicoding.gardenguard.ui.diagnose

import androidx.lifecycle.ViewModel
import com.dicoding.gardenguard.data.repository.PlantRepository
import java.io.File

class UploadImageViewModel(private val plantRepository: PlantRepository) : ViewModel() {

    fun diagnoseDisease(file: File, plantName: String) = plantRepository.diagnoseDisease(file, plantName)

}